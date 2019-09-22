package cn.fdongl.point.computeapi.controller;

import cn.fdongl.point.common.util.BaseController;
import cn.fdongl.point.computeapi.exception.NoDataException;
import cn.fdongl.point.computeapi.service.ComputeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("compute")
public class ComputeController extends BaseController<ComputeService>{

    @GetMapping("grade/{grade}")
    public void computeByGrade(@PathVariable("grade") int grade) throws NoDataException {
        service.computeByGrade(grade);
    }

    @GetMapping("year/{range}")
    public void computeByYear(@PathVariable("range")String range) throws NoDataException {
        int start;
        int end;
        if(range.matches("\\d{4}-\\d{4}")){
            start = Integer.valueOf(range.substring(0,4));
            end = Integer.valueOf(range.substring(5,9));
        } else {
            throw new NoDataException("年份格式应为yyyy-yyyy",null);
        }
        service.computeByYear(start,end);
    }
}
