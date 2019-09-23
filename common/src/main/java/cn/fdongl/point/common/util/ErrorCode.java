package cn.fdongl.point.common.util;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    SUCCESS(0,"success"),
    FAIL(1,"fail"),
    DATA_REPEAT(2,"fail:数据重复"),
    FILE_NOT_FOUND(3,"fail:文件未找到"),
    WORKBOOK_CAST(4,"fail:无法解析为excel文件")
    ;

    private int code;
    private String msg;

    public static ErrorCode getEnum (int code) {
        for (ErrorCode value : ErrorCode.values()) {
            if(value.code == code){
                return value;
            }
        }
        return null;
    }


}
