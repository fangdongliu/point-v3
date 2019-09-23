package cn.fdongl.point.uploadapiimpl.repository;

import cn.fdongl.point.common.entity.MapStudentEvaluation;
import cn.fdongl.point.common.entity.MapTeacherEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TeacherEvaluationRepository extends JpaRepository<MapTeacherEvaluation,Long> {

}
