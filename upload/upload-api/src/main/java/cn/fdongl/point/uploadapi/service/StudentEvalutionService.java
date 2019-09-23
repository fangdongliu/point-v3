package cn.fdongl.point.uploadapi.service;

import cn.fdongl.point.auth.vo.JwtUser;
import cn.fdongl.point.common.exception.WorkbookCastException;
import cn.fdongl.point.common.util.PageResult;
import cn.fdongl.point.uploadapi.exception.DataRepeatException;
import cn.fdongl.point.uploadapi.vo.StudentEvaluation;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StudentEvalutionService{

    PageResult page(Pageable pageable, JwtUser jwtUser);

    int evaluate(StudentEvaluation evaluationList,JwtUser jwtUser);

    List points(long studentCourseId,JwtUser jwtUser) throws Exception;

}
