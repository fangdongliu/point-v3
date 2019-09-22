package cn.fdongl.point.uploadapi.service;


import org.springframework.web.multipart.MultipartFile;

public interface PlanService extends UploadService{

    /**
     * 上传 某 一届 的培养方案与实现矩阵，会导致覆盖
     * @param plan 培养方案文件
     * @param matrix 实现矩阵文件
     * @param grade 届 egg: 2016
     * @return 成功插入指标点的数量
     */
    int upload(MultipartFile plan,MultipartFile matrix,int grade);
}
