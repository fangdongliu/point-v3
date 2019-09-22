package cn.fdongl.point.common.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@lombok.experimental.Accessors(chain = true)
public class Result {

    Object data;

    int code;

    String msg;

    public static Result of(ErrorCode code){
        return new Result()
                .setCode(code.getCode())
                .setMsg(code.getMsg());
    }

    public static Result of(ErrorCode code,Object data){
        return new Result()
                .setData(data)
                .setCode(code.getCode())
                .setMsg(code.getMsg());
    }

    public Result addDetail(String detail){
        msg = msg + " | " + detail;
        return this;
    }

}
