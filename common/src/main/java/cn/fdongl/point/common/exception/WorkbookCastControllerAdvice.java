package cn.fdongl.point.common.exception;

import cn.fdongl.point.common.util.ErrorCode;
import cn.fdongl.point.common.util.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class WorkbookCastControllerAdvice {

    @ExceptionHandler(WorkbookCastException.class)
    @ResponseBody
    public Object ex(WorkbookCastException ex){
        return Result.of(ErrorCode.WORKBOOK_CAST);
    }

}
