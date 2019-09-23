package cn.fdongl.point.uploadapiimpl.service;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.entity.Course;
import cn.fdongl.point.common.entity.File;
import cn.fdongl.point.common.exception.WorkbookCastException;
import cn.fdongl.point.common.util.PageResult;
import cn.fdongl.point.common.util.SheetHelper;
import cn.fdongl.point.common.util.Workbooks;
import cn.fdongl.point.uploadapi.service.CourseService;
import cn.fdongl.point.uploadapi.service.FileService;
import cn.fdongl.point.uploadapiimpl.repository.CourseRepository;
import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    FileService fileService;

    @Autowired
    CourseRepository courseRepository;

    private final Pattern pattern = Pattern.compile("\\d{4}-\\d{4}-\\d");


    @Override
    @Transactional
    public int upload(MultipartFile file, JwtUser jwtUser) throws Exception {
        Workbook workbook = Workbooks.of(file);
        File file1 = fileService.save(file,"course");
        SheetHelper sheetHelper = new SheetHelper(workbook.getSheetAt(0),2);
        String semester = getSemester(sheetHelper);
        List<Course>courses = getCourses(sheetHelper);
        Long ss = Long.valueOf(semester.substring(0,4));
        Long sub = Long.valueOf(semester.substring(10,11));
        courseRepository.deleteAllBySemesterAndSubSemester(ss,sub);
        for (Course cours : courses) {
            cours
                    .setSemester(ss)
                    .setSubSemester(sub)
                    .setCreateFrom(file1.getId());
        }
        courseRepository.saveAll(courses);
        return courses.size();

    }

    private List<Course> getCourses(SheetHelper sheetHelper) throws IllegalAccessException, ParseException, InstantiationException {
        List<UploadCourse>couses = sheetHelper.collectLinesForClass(UploadCourse.class,3);
        return couses.stream().map(UploadCourse::toCourse).collect(Collectors.toList());
    }

    private String getSemester(SheetHelper sheetHelper) throws Exception {
        String head = String.valueOf(sheetHelper.filterNull(sheetHelper.collectLines(0,1).get(0)).get(0));
        Matcher matcher = pattern.matcher(head);
        Long semester = null;
        if(matcher.find()){
           return matcher.group(0);
        } else {
            throw new Exception("未指定学期");
        }
    }

    @Override
    @Transactional
    public int deleteByFile(long fileId, JwtUser jwtUser) {
        return courseRepository.deleteAllByCreateFrom(fileId);
    }

    @Override
    public PageResult page(Pageable pageable, JwtUser jwtUser) {
        return fileService.page(pageable,"course",jwtUser);
    }

    @Data
    public static class UploadCourse{
        public String courseNumber;
        public String courseName;
        public String transient1;
        public String transient2;
        public String transient3;
        public String type;
        public String transient5;
        public String transient6;
        public String department;
        public String courseRoute;

        public Course toCourse(){
            return new Course()
                    .setGrade(Long.valueOf(courseRoute.substring(0,4)))
                    .setNumber(courseNumber)
                    .setName(courseName)
                    .setType(type);

        }

    }

}
