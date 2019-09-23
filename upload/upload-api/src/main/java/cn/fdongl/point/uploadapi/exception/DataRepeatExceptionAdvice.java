package cn.fdongl.point.uploadapi.exception;

import cn.fdongl.point.common.util.ErrorCode;
import cn.fdongl.point.common.util.Result;
import cn.fdongl.point.uploadapi.exception.DataRepeatException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;

@ControllerAdvice
public class DataRepeatExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(DataRepeatException.class)
    public Object exception(DataRepeatException ex){
        return Result.of(ErrorCode.DATA_REPEAT).addDetail(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(FileNotFoundException.class)
    public Object fileNotfound(FileNotFoundException ex){
        return Result.of(ErrorCode.FILE_NOT_FOUND);
    }

}
