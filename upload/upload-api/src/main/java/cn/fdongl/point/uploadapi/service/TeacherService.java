package cn.fdongl.point.uploadapi.service;


import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.uploadapi.exception.DataRepeatException;
import org.springframework.web.multipart.MultipartFile;

public interface TeacherService extends UploadService{
    /**
     * 上传老师名单文件
     * @param file 老师名单文件
     * @param allowCover 是否允许覆盖重复数据 / 否则抛出异常
     * @return 成功插入的数据条数
     * @throws DataRepeatException
     */
    int upload(MultipartFile file, boolean allowCover, JwtUser jwtUser)throws DataRepeatException;
}
