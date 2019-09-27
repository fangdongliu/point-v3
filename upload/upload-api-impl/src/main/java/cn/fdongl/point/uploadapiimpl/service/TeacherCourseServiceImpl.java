package cn.fdongl.point.uploadapiimpl.service;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.entity.Course;
import cn.fdongl.point.common.entity.File;
import cn.fdongl.point.common.entity.MapTeacherCourse;
import cn.fdongl.point.common.exception.WorkbookCastException;
import cn.fdongl.point.common.util.PageResult;
import cn.fdongl.point.common.util.SheetHelper;
import cn.fdongl.point.common.util.Workbooks;
import cn.fdongl.point.uploadapi.service.FileService;
import cn.fdongl.point.uploadapi.service.TeacherCourseService;
import cn.fdongl.point.uploadapiimpl.repository.MapTeacherCourseRepository;
import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class TeacherCourseServiceImpl implements TeacherCourseService {

    @Autowired
    MapTeacherCourseRepository mapTeacherCourseRepository;

    @Autowired
    FileService fileService;

    private final Pattern pattern = Pattern.compile("\\d{4}-\\d{4}-\\d");

    @Override
    @Transactional
    public int upload(MultipartFile file, JwtUser jwtUser) throws Exception {
        Workbook workbook = Workbooks.of(file);
        File file1 = fileService.save(file,"teacherCourse");
        SheetHelper sheetHelper = new SheetHelper(workbook.getSheetAt(0),2);
        List<MapTeacherCourse>list = getTeacherCourse(sheetHelper);
        String semester = getSemester(sheetHelper);
        Long ss = Long.valueOf(semester.substring(0,4));
        Long sub = Long.valueOf(semester.substring(10,11));
        mapTeacherCourseRepository.deleteAllBySemesterAndSubSemester(ss,sub);
        for (MapTeacherCourse cours : list) {
            cours
                    .setSemester(ss)
                    .setSubSemester(sub)
                    .setCreateFrom(file1.getId());
        }
        mapTeacherCourseRepository.saveAll(list);
        return list.size();

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

    private List<MapTeacherCourse> getTeacherCourse(SheetHelper sheetHelper) throws IllegalAccessException, ParseException, InstantiationException {
        List<UploadTeacherCourse>couses = sheetHelper.collectLinesForClass(UploadTeacherCourse.class,3);
        return couses.stream().map(UploadTeacherCourse::toMapTeacherCourse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int deleteByFile(long fileId, JwtUser jwtUser) {
        fileService.delete(fileId);
        return mapTeacherCourseRepository.deleteAllByCreateFrom(fileId);
    }

    @Override
    public PageResult page(Pageable pageable, JwtUser jwtUser) {
        return fileService.page(pageable,"teacherCourse",jwtUser);
    }

    @Data
    public static class UploadTeacherCourse{
        public String selectNumber;
        public String area;
        public String courseName;
        public String v;
        public String teacherName;
        public String title;
        public String type,way,cnt,cnt2,week,week2,t,t2,t3,t4,t5;
        public String className;
        public String place;
        public String courseNumber;

        public MapTeacherCourse toMapTeacherCourse(){
            int pos1 = selectNumber.lastIndexOf('-');
            int pos2 = selectNumber.lastIndexOf('-',pos1-1);
            return new MapTeacherCourse()
                    .setCourseNumber(courseNumber)
                    .setCourseName(courseName)
                    .setStatuz(0L)
                    .setWorkId(selectNumber.substring(pos2+1,pos1));

        }

    }

}
