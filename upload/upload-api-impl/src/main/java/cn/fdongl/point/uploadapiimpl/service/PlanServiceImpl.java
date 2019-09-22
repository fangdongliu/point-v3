package cn.fdongl.point.uploadapiimpl.service;

import cn.fdongl.point.uploadapi.service.PlanService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PlanServiceImpl implements PlanService {
    @Override
    public int upload(MultipartFile plan, MultipartFile matrix, int grade) {
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
