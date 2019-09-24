package cn.fdongl.point.computeapiimpl.vo;

import lombok.Data;

@Data
public class Evaluation2 {

    String number;
    Long semester;
    Double val;
    Long childIndex;
    Long parentIndex;
    Long count = 0L;

}
