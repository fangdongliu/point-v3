package cn.fdongl.point.uploadapiimpl.service;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.uploadapi.exception.DataRepeatException;
import cn.fdongl.point.uploadapi.service.TeacherService;
import cn.fdongl.point.uploadapiimpl.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    FileRepository fileRepository;

    @Override
    public int upload(MultipartFile file, boolean allowCover, JwtUser jwtUser) throws DataRepeatException {
         return 0;
    }

    @Override
    public int deleteByFile(long fileId, JwtUser jwtUser) {
        return 0;
    }

    @Override
    public Page page(Pageable pageable, JwtUser jwtUser) {
        return fileRepository.findByCreateByAndType(jwtUser.getId(),"teacher",pageable);
    }
}
