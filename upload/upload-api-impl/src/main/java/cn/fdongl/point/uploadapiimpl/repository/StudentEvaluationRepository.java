package cn.fdongl.point.uploadapiimpl.repository;

import cn.fdongl.point.common.entity.MapStudentCourse;
import cn.fdongl.point.common.entity.MapStudentEvaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentEvaluationRepository extends JpaRepository<MapStudentEvaluation,Long> {

}
