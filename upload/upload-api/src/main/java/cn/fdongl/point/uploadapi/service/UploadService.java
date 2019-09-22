package cn.fdongl.point.uploadapi.service;

import cn.fdongl.point.auth.vo.JwtUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 无需实现
 */
public interface UploadService {

    /**
     * 根据文件ID删除对应的所有数据
     * 从文件上传的数据会绑定对应的文件ID
     * @param fileId 文件ID
     * @return 删除数据条数
     */
    int deleteByFile(long fileId, JwtUser jwtUser);

    /**
     * 获取文件列表
     * @param pageable 分页
     * @return 对应分页的文件列表
     */
    Page page(Pageable pageable, JwtUser jwtUser);

}
