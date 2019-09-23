package cn.fdongl.point.uploadapi.vo;

import lombok.Data;

import java.util.List;

@Data
public class StudentEvaluation {

    private String courseNumber;

    private long courseSemester;

    private List<Evaluation>evaluations;

    @Data
    public static class Evaluation{
        private Long indexId;
        private Long evaluationValue;
    }

}
