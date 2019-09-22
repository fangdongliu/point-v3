package cn.fdongl.point.uploadapi.service;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.uploadapi.exception.DataRepeatException;
import org.springframework.web.multipart.MultipartFile;

public interface StudentService extends UploadService{
    /**
     * 上传学生选课文件
     * @param file 学生选课文件
     * @param allowCover 对于重复的数据是否覆盖 / 否则报错
     * @return 成功插入的学生选课记录条数
     * @throws DataRepeatException
     */
    int upload(MultipartFile file, boolean allowCover, JwtUser jwtUser)throws DataRepeatException;
}
