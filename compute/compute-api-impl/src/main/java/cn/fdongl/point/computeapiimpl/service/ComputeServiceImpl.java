package cn.fdongl.point.computeapiimpl.service;

import cn.fdongl.point.computeapi.exception.NoDataException;
import cn.fdongl.point.computeapi.service.ComputeService;
import org.springframework.stereotype.Service;

@Service
public class ComputeServiceImpl implements ComputeService {
    @Override
    public void computeByGrade(int grade) throws NoDataException {

    }

    @Override
    public void computeByYear(int yearStart, int yearEnd) throws NoDataException {

    }
}
