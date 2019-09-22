package cn.fdongl.point.uploadapi.controller;

import cn.fdongl.point.common.util.BaseController;
import cn.fdongl.point.common.util.ErrorCode;
import cn.fdongl.point.common.util.Result;
import cn.fdongl.point.uploadapi.service.PlanService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("teacher")
public class PlanController extends BaseController<PlanService>{

    @PostMapping("upload")
    public Object upload(
            @RequestParam MultipartFile plan,
            @RequestParam MultipartFile matrix, int grade) {
        return Result.of(ErrorCode.SUCCESS,service.upload(plan, matrix, grade));
    }

    @PostMapping("delete")
    public Object deleteByFile(long fileId) {
        return Result.of(ErrorCode.SUCCESS,service.deleteByFile(fileId));
    }

    @GetMapping("page")
    public Object page(@RequestParam int pageIndex,
                     @RequestParam int pageSize) {
        return Result.of(ErrorCode.SUCCESS,
                service.page(PageRequest.of(pageIndex - 1,pageSize)));
    }
}
