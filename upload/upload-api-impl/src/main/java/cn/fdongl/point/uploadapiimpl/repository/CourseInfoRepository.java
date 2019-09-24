package cn.fdongl.point.uploadapiimpl.repository;

import cn.fdongl.point.common.entity.CourseInfo;
import cn.fdongl.point.common.entity.IndexPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseInfoRepository extends JpaRepository<CourseInfo,Long> {

    int deleteAllByGrade(long grade);
    int deleteAllByCreateFrom(long createFrom);

    CourseInfo findFirstByGradeBeforeAndNumberOrderByGrade(long grade,String number);

    @Query(value = "select * from course_info where grade = (select max(grade) from course_info where grade <= :grade)",nativeQuery = true)
    List<CourseInfo> getMatrixCourse(@Param("grade")long grade);

}
