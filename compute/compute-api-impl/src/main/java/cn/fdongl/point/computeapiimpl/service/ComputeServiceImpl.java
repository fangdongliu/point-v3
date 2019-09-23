package cn.fdongl.point.computeapiimpl.service;

import cn.fdongl.point.common.entity.Course;
import cn.fdongl.point.computeapi.exception.NoDataException;
import cn.fdongl.point.computeapi.service.ComputeService;
import cn.fdongl.point.uploadapiimpl.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class ComputeServiceImpl implements ComputeService {

    @Autowired
    CourseRepository courseRepository;

    @Override
    @Transactional
    public void computeByGrade(int grade, HttpServletResponse response) throws NoDataException {
        List<Course> list = courseRepository.findAllByGrade(grade);
        buildResult(list,response);
    }

    private void buildResult(List<Course> list, HttpServletResponse response) {

    }

    @Override
    @Transactional
    public void computeByYear(int yearStart, int yearEnd,HttpServletResponse response) throws NoDataException {
        List<Course> list = courseRepository.findAllBySemesterBetween(yearEnd,yearEnd);
        buildResult(list,response);
    }


}
