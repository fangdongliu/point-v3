package cn.fdongl.point.uploadapi.service;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.entity.File;
import cn.fdongl.point.common.util.PageResult;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {
    /**
     * 根据文件类型获取指定分页
     * @param pageable 分页
     * @param type 文件类型
     * @return 分页数据
     */
    PageResult page(Pageable pageable, String type, JwtUser jwtUser);

    /**
     * 下载指定文件
     * @param fileId 文件ID
     * @throws FileNotFoundException 文件未找到
     */
    void download(int fileId, HttpServletResponse response) throws FileNotFoundException;

    /**
     * 保存文件至本地并记录到数据库
     * @param file
     * @return 上传后的数据库数据
     */
    File save(MultipartFile file, String type) throws IOException;

    void delete(long fileId);

    File save(MultipartFile file);
}
