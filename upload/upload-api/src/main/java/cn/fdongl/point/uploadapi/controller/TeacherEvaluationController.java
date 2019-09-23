package cn.fdongl.point.uploadapi.controller;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.exception.WorkbookCastException;
import cn.fdongl.point.common.util.BaseController;
import cn.fdongl.point.common.util.ErrorCode;
import cn.fdongl.point.common.util.Result;
import cn.fdongl.point.uploadapi.exception.DataRepeatException;
import cn.fdongl.point.uploadapi.service.StudentEvalutionService;
import cn.fdongl.point.uploadapi.service.TeacherEvaluationService;
import cn.fdongl.point.uploadapi.vo.StudentEvaluation;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("teacher/evaluation")
public class TeacherEvaluationController extends BaseController<TeacherEvaluationService>{

    @PostMapping("evaluate")
    public Object evaluate(
            @RequestParam MultipartFile file,long classId, JwtUser jwtUser) throws Exception {
        return Result.of(ErrorCode.SUCCESS,service.evaluate(file,classId,jwtUser));
    }

    @GetMapping("page")
    public Object page(@RequestParam int pageIndex,
                     @RequestParam int pageSize,JwtUser jwtUser) {
        return Result.of(ErrorCode.SUCCESS,
                service.page(PageRequest.of(pageIndex - 1,pageSize),jwtUser));
    }
}
