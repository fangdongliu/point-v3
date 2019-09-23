package cn.fdongl.point.uploadapiimpl.repository;

import cn.fdongl.point.common.entity.MapCourseIndex;
import cn.fdongl.point.common.entity.MapStudentCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MapStudentCourseRepository extends JpaRepository<MapStudentCourse,Long> {

    int deleteAllByCreateFrom(long createFrom);

    Page<MapStudentCourse>findAllByWorkId(String workId, Pageable pageable);

    MapStudentCourse findFirstByCourseNumberAndWorkIdAndCourseSemester(String courseNumber,String workId,long semester);

}
