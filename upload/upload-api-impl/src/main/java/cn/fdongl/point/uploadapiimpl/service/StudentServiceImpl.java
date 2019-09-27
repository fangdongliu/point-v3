package cn.fdongl.point.uploadapiimpl.service;

import cn.fdongl.point.auth.repository.UserRepository;
import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.entity.File;
import cn.fdongl.point.common.entity.MapStudentCourse;
import cn.fdongl.point.common.entity.User;
import cn.fdongl.point.common.exception.WorkbookCastException;
import cn.fdongl.point.common.util.PageResult;
import cn.fdongl.point.common.util.SheetHelper;
import cn.fdongl.point.common.util.Workbooks;
import cn.fdongl.point.uploadapi.exception.DataRepeatException;
import cn.fdongl.point.uploadapi.service.FileService;
import cn.fdongl.point.uploadapi.service.StudentService;
import cn.fdongl.point.uploadapiimpl.mapper.BatchUploadMapper;
import cn.fdongl.point.uploadapiimpl.repository.MapStudentCourseRepository;
import cn.fdongl.point.uploadapiimpl.util.BatchExecutor;
import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    FileService fileService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MapStudentCourseRepository mapStudentCourseRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    BatchUploadMapper batchUploadMapper;

    private final Set<String> keySet = new HashSet<>(Arrays.asList("校工选课","拓展英语","专业课"));

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int upload(MultipartFile file, boolean allowCover, JwtUser jwtUser) throws DataRepeatException, WorkbookCastException, IOException, IllegalAccessException, ParseException, InstantiationException {
        File studentFile = fileService.save(file,"student");
        final long fileId = studentFile.getId();
        final String password = passwordEncoder.encode("123456");
        Workbook workbook = Workbooks.of(file);
        SheetHelper sheetHelper = new SheetHelper(workbook.getSheetAt(0),0);
        List<MapStudentCourse>mapStudentCourses = getStudentCourseMap(sheetHelper);
        Collection<User> users = mapStudentCourses.stream().collect(Collectors.toMap(MapStudentCourse::getWorkId, i->{
            User user = new User();
            user
                    .setUsername(i.getWorkId())
                    .setRealName(i.getRealName())
                    .setPassword(password)
                    .setRole("student")
                    .setDepartment("计算机学院")
                    .setCreateFrom(fileId);
            return user;
        },(v1,v2)->v1)).values();
        for (MapStudentCourse mapStudentCours : mapStudentCourses) {
            mapStudentCours.setCreateFrom(fileId);
        }
        if(allowCover){
            userRepository.deleteAllByUsernameIn(users.stream().map(User::getUsername).collect(Collectors.toList()));
            userRepository.flush();
            BatchExecutor.batchExecute(mapStudentCourses,batchUploadMapper::insertStudentCourseIgnore);
        }else{
            BatchExecutor.batchExecute(mapStudentCourses,batchUploadMapper::insertStudentCourse);
        }
        userRepository.saveAll(users);
        return 0;
    }

    private List<MapStudentCourse> getStudentCourseMap(SheetHelper sheetHelper) throws IllegalAccessException, ParseException, InstantiationException {
        List<UploadStudentCourse> lines = sheetHelper.collectLinesForClass(UploadStudentCourse.class, 1);
        return lines.stream().filter(i->!(keySet.contains(i.getType()))).map(UploadStudentCourse::toMapStudentCourse).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByFile(long fileId, JwtUser jwtUser) {
        fileService.delete(fileId);
        int cnt = mapStudentCourseRepository.deleteAllByCreateFrom(fileId);
        cnt += userRepository.deleteAllByCreateFrom(fileId);
        return cnt;
    }

    @Override
    public PageResult page(Pageable pageable, JwtUser jwtUser) {
        return fileService.page(pageable,"student",jwtUser);
    }

    @Data
    public static class UploadStudentCourse{
        public String workId;
        public String realName;
        public String transient1;
        public String semester;
        public String department;
        public String transient2;
        public String classNumber;
        public String courseNumber;
        public String courseName;
        public String transient3;
        public String transient4;
        public String type;
        public MapStudentCourse toMapStudentCourse(){
            MapStudentCourse mapStudentCourse = new MapStudentCourse();
            mapStudentCourse.setWorkId(workId);
            mapStudentCourse.setCourseSemester(Long.valueOf(semester.substring(0,4)));
            mapStudentCourse.setCourseNumber(courseNumber);
            mapStudentCourse.setCourseName(courseName);
            mapStudentCourse.setRealName(realName);
            mapStudentCourse.setSubCourseSemester(Long.valueOf(semester.substring(10)));
            return mapStudentCourse;
        }

    }

}
