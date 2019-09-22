package cn.fdongl.point.uploadapi.service;

import org.springframework.web.multipart.MultipartFile;

public interface CourseService extends UploadService{
    /**
     * 上传某年开课信息，会导致同年文件的覆盖
     * @param file 课程文件
     * @param year 年份
     * @return 成功插入的条数
     */
    int upload(MultipartFile file,int year);
}
