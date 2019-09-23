package cn.fdongl.point.uploadapi.controller;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.exception.WorkbookCastException;
import cn.fdongl.point.common.util.BaseController;
import cn.fdongl.point.common.util.ErrorCode;
import cn.fdongl.point.common.util.Result;
import cn.fdongl.point.uploadapi.service.PlanService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("plan")
public class PlanController extends BaseController<PlanService>{

    @PostMapping("upload")
    public Object upload(
            @RequestParam MultipartFile plan,
            @RequestParam MultipartFile matrix, int grade,JwtUser jwtUser) throws WorkbookCastException, IOException {
        return Result.of(ErrorCode.SUCCESS,service.upload(plan, matrix, grade,jwtUser));
    }

    @PostMapping("delete")
    public Object deleteByGrade(long id,JwtUser jwtUser) {
        return Result.of(ErrorCode.SUCCESS,service.deleteByFile(id,jwtUser));
    }

    @GetMapping("page")
    public Object page(@RequestParam int pageIndex,
                       @RequestParam int pageSize, JwtUser jwtUser) {
        return Result.of(ErrorCode.SUCCESS,
                service.page(PageRequest.of(pageIndex - 1,pageSize),jwtUser));
    }
}
