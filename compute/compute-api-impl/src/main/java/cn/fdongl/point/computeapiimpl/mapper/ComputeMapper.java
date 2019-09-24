package cn.fdongl.point.computeapiimpl.mapper;

import cn.fdongl.point.computeapiimpl.vo.Evaluation;
import cn.fdongl.point.computeapiimpl.vo.Evaluation2;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ComputeMapper {

    @Select("select A.number,A.semester,B.val,B.child_index as childIndex,B.parent_index as parentIndex from(select * from course where grade = #{param1})A join map_teacher_evaluation B on " +
            "A.number = B.course_number and A.semester = B.semester ")
    List<Evaluation2> getTeacherEvaluationByGrade(int grade);

    @Select("select A.number,A.semester,B.val,B.child_index as childIndex,B.parent_index as parentIndex from(select * from course where grade = #{param1})A join map_teacher_evaluation B on " +
            "A.number = B.course_number and A.semester = B.semester ")
    List<Evaluation> getStudentEvaluationByGrade(int grade);

    @Select("select A.number,A.semester,B.val,B.child_index as childIndex,B.parent_index as parentIndex from(select * from course where semester between #{param1} and #{param2})A join map_teacher_evaluation B on " +
            "A.number = B.course_number and A.semester = B.semester ")
    List<Evaluation2> getTeacherEvaluationByYear(int left,int right);

    @Select("select A.number,A.semester,B.val,B.child_index as childIndex,B.parent_index as parentIndex from(select * from course where semester between #{param1} and #{param2})A join map_teacher_evaluation B on " +
            "A.number = B.course_number and A.semester = B.semester ")
    List<Evaluation> getStudentEvaluationByYear(int left,int right);

}
