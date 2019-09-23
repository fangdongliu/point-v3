package cn.fdongl.point.uploadapi.controller;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.util.BaseController;
import cn.fdongl.point.common.util.ErrorCode;
import cn.fdongl.point.common.util.Result;
import cn.fdongl.point.uploadapi.service.CourseService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("course")
public class CourseController extends BaseController<CourseService>{

    @PostMapping("upload")
    public Object upload(
            @RequestParam MultipartFile file, int year, JwtUser jwtUser) throws Exception {
        return Result.of(ErrorCode.SUCCESS,service.upload(file,jwtUser));
    }

    @PostMapping("delete")
    public Object deleteByFile(long fileId,JwtUser jwtUser) {
        return Result.of(ErrorCode.SUCCESS,service.deleteByFile(fileId,jwtUser));
    }

    @GetMapping("page")
    public Object page(@RequestParam int pageIndex,
                     @RequestParam int pageSize,JwtUser jwtUser) {
        return Result.of(ErrorCode.SUCCESS,
                service.page(PageRequest.of(pageIndex - 1,pageSize),jwtUser));
    }
}
