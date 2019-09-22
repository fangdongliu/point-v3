package cn.fdongl.point.computeapi.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(NoDataException.class)
    public Object exception(NoDataException ex){
        return "没有对应数据; " + ex.getMessage();
    }

}
