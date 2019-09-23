package cn.fdongl.point.uploadapiimpl.repository;

import cn.fdongl.point.common.entity.MapStudentCourse;
import cn.fdongl.point.common.entity.MapTeacherCourse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MapTeacherCourseRepository extends JpaRepository<MapTeacherCourse,Long> {

    int deleteAllByCreateFrom(long createFrom);

    int deleteAllBySemesterAndSubSemester(long semester,long subSemester);

    Page<MapTeacherCourse> findAllByWorkId(String workId, Pageable pageable);

}
