package cn.fdongl.point.uploadapi.service;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.exception.WorkbookCastException;
import cn.fdongl.point.common.util.PageResult;
import cn.fdongl.point.uploadapi.exception.DataRepeatException;
import cn.fdongl.point.uploadapi.vo.StudentEvaluation;
import com.sun.org.apache.xpath.internal.operations.Mult;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface TeacherEvaluationService{
    PageResult page(Pageable pageable, JwtUser jwtUser);

    int evaluate(MultipartFile multipartFile, long id, JwtUser jwtUser) throws Exception;
}
