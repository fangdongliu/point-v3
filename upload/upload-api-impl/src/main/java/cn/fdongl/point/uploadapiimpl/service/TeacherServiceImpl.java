package cn.fdongl.point.uploadapiimpl.service;

import cn.fdongl.point.uploadapi.exception.DataRepeatException;
import cn.fdongl.point.uploadapi.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Override
    public int upload(MultipartFile file, boolean allowCover) throws DataRepeatException {
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
