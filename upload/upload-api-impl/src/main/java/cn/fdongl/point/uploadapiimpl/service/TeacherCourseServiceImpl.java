package cn.fdongl.point.uploadapiimpl.service;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.uploadapi.service.TeacherCourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TeacherCourseServiceImpl implements TeacherCourseService {

    @Override
    public int upload(MultipartFile file, int year, JwtUser jwtUser) {
        return 0;
    }

    @Override
    public int deleteByFile(long fileId, JwtUser jwtUser) {
        return 0;
    }

    @Override
    public Page page(Pageable pageable, JwtUser jwtUser) {
        return null;
    }
}
