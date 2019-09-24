package cn.fdongl.point.computeapiimpl.vo;

import lombok.Data;

@Data
public class Evaluation {

    String number;
    Long semester;
    Long val;
    Long childIndex;
    Long parentIndex;
    Long count = 0L;
}
