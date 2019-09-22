package cn.fdongl.point.uploadapi.controller;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.util.BaseController;
import cn.fdongl.point.common.util.ErrorCode;
import cn.fdongl.point.common.util.Result;
import cn.fdongl.point.uploadapi.exception.DataRepeatException;
import cn.fdongl.point.uploadapi.service.StudentService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("teacher")
public class StudentController extends BaseController<StudentService>{

    @PostMapping("upload")
    public Object upload(
            @RequestParam MultipartFile file, boolean allowCover, JwtUser jwtUser) throws DataRepeatException {
        return Result.of(ErrorCode.SUCCESS,service.upload(file,allowCover,jwtUser));
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
