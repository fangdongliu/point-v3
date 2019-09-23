package cn.fdongl.point.uploadapiimpl.service;

import cn.fdongl.point.auth.repository.UserRepository;
import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.entity.File;
import cn.fdongl.point.common.entity.User;
import cn.fdongl.point.common.exception.WorkbookCastException;
import cn.fdongl.point.common.util.Converter;
import cn.fdongl.point.common.util.PageResult;
import cn.fdongl.point.common.util.SheetHelper;
import cn.fdongl.point.common.util.Workbooks;
import cn.fdongl.point.uploadapi.exception.DataRepeatException;
import cn.fdongl.point.uploadapi.service.FileService;
import cn.fdongl.point.uploadapi.service.TeacherService;
import cn.fdongl.point.uploadapiimpl.repository.FileRepository;
import lombok.Data;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FileService fileService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int upload(MultipartFile file, boolean allowCover, JwtUser jwtUser) throws DataRepeatException, WorkbookCastException, IOException, IllegalAccessException, ParseException, InstantiationException {

        File teacherFile = fileService.save(file,"teacher");

        Workbook workbook = Workbooks.of(file);
        List<User> teachers = getTeacherInfo(workbook);
        if (allowCover) {
            userRepository.deleteAllByUsernameIn(teachers.stream().map(User::getUsername).collect(Collectors.toList()));
            userRepository.flush();
        }
        String password = passwordEncoder.encode("123456");
        for (User teacher : teachers) {
            teacher
                    .setPassword(password)
                    .setRole("teacher")
                    .setDepartment("计算机学院")
                    .setCreateFrom(teacherFile.getId());
        }
        userRepository.saveAll(teachers);

        return teachers.size();
    }

    private List<User> getTeacherInfo(Workbook workbook) throws IllegalAccessException, ParseException, InstantiationException {

        SheetHelper sheetHelper = new SheetHelper(workbook.getSheetAt(0),2);
        List<UploadUser>users = sheetHelper.collectLinesForClass(UploadUser.class,3);
        return users.stream().map(UploadUser::toUser).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public int deleteByFile(long fileId, JwtUser jwtUser) {
        fileRepository.deleteById(fileId);
        return userRepository.deleteAllByCreateFrom(fileId);
    }

    @Override
    public PageResult page(Pageable pageable, JwtUser jwtUser) {
        return fileService.page(pageable,"teacher",jwtUser);
    }

    @Data
    public static class UploadUser{
        public String username;
        public String account;
        public String realName;
        public String department;
        User toUser(){
            return Converter.convert(this,User.class);
        }
    }

}
