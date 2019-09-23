package cn.fdongl.point.uploadapiimpl.repository;

import cn.fdongl.point.common.entity.CourseInfo;
import cn.fdongl.point.common.entity.MapCourseIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MapCourseIndexRepository extends JpaRepository<MapCourseIndex,Long> {

    int deleteAllByGrade(long grade);

    int deleteAllByCreateFrom(long createFrom);

    List<MapCourseIndex> findAllByCourseId(Long courseId);

}
