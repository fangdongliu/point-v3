package cn.fdongl.point.uploadapiimpl.service;

import cn.fdongl.point.uploadapi.service.TeacherCourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TeacherCourseServiceImpl implements TeacherCourseService {
    @Override
    public int upload(MultipartFile file, int year) {
        return 0;
    }

    @Override
    public int deleteByFile(long fileId) {
        return 0;
    }

    @Override
    public Page page(Pageable pageable) {
        return null;
    }
}
