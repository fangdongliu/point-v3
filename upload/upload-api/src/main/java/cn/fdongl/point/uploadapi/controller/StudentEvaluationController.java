package cn.fdongl.point.uploadapi.controller;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.exception.WorkbookCastException;
import cn.fdongl.point.common.util.BaseController;
import cn.fdongl.point.common.util.ErrorCode;
import cn.fdongl.point.common.util.Result;
import cn.fdongl.point.uploadapi.exception.DataRepeatException;
import cn.fdongl.point.uploadapi.service.StudentEvalutionService;
import cn.fdongl.point.uploadapi.service.StudentService;
import cn.fdongl.point.uploadapi.vo.StudentEvaluation;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("student/evaluation")
public class StudentEvaluationController extends BaseController<StudentEvalutionService>{

    @PostMapping("evaluate")
    public Object evaluate(
            @RequestBody StudentEvaluation studentEvaluation, JwtUser jwtUser) throws DataRepeatException, WorkbookCastException {
        return Result.of(ErrorCode.SUCCESS,service.evaluate(studentEvaluation,jwtUser));
    }

    @GetMapping("points/{id}")
    public Object points(@PathVariable("id") long id,JwtUser jwtUser) throws Exception {
        return Result.of(ErrorCode.SUCCESS,service.points(id,jwtUser));
    }

    @GetMapping("page")
    public Object page(@RequestParam int pageIndex,
                     @RequestParam int pageSize,JwtUser jwtUser) {
        return Result.of(ErrorCode.SUCCESS,
                service.page(PageRequest.of(pageIndex - 1,pageSize),jwtUser));
    }
}
