package cn.fdongl.point.uploadapiimpl.repository;

import cn.fdongl.point.common.entity.MapCourseIndex;
import cn.fdongl.point.common.entity.MapStudentCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MapStudentCourseRepository extends JpaRepository<MapStudentCourse,Long> {

    @Query(value = "delete from map_student_course where create_from = :createFrom",nativeQuery = true)
    int deleteAllByCreateFrom(@Param("createFrom") long createFrom);

    Page<MapStudentCourse>findAllByWorkId(String workId, Pageable pageable);

    MapStudentCourse findFirstByCourseNumberAndWorkIdAndCourseSemester(String courseNumber,String workId,long semester);

}
