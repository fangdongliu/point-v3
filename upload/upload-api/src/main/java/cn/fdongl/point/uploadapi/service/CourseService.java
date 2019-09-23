package cn.fdongl.point.uploadapi.service;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.exception.WorkbookCastException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface CourseService extends UploadService{
    /**
     * 上传某年开课信息，会导致同年文件的覆盖
     * @param file 课程文件
     * @return 成功插入的条数
     */
    int upload(MultipartFile file, JwtUser jwtUser) throws Exception;
}
