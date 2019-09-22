package cn.fdongl.point.uploadapi.service;

import cn.fdongl.point.auth.vo.JwtUser;
import org.springframework.web.multipart.MultipartFile;

public interface TeacherCourseService extends UploadService{
    /**
     * 上传老师授课文件，会根据年份发生覆盖行为
     * @param file 老师授课文件
     * @param year 年份
     * @return 插入数据条数
     */
    int upload(MultipartFile file, int year, JwtUser jwtUser);
}
