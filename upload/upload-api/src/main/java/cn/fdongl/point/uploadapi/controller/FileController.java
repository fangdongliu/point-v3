package cn.fdongl.point.uploadapi.controller;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.util.BaseController;
import cn.fdongl.point.common.util.PageResult;
import cn.fdongl.point.uploadapi.service.FileService;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("file")
public class FileController extends BaseController<FileService> {

    @GetMapping("page")
    public PageResult page(@RequestParam int pageIndex,
                           @RequestParam int pageSize,
                           String type, JwtUser jwtUser) {
        return service.page(PageRequest.of(pageIndex,pageSize),type,jwtUser);
    }

    @GetMapping("download/{fileId}")
    public void download(@PathVariable("fileId") int fileId,
                         HttpServletResponse response)throws FileNotFoundException {
        service.download(fileId,response);
    }

}
