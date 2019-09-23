package cn.fdongl.point.uploadapiimpl.repository;

import cn.fdongl.point.common.entity.Course;
import cn.fdongl.point.common.entity.CourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {

    int deleteAllByCreateFrom(long createFrom);

    int deleteAllBySemesterAndSubSemester(long semester,long subSemester);

    Course findByNumberAndSemester(String number,long semester);

    List<Course> findAllByGrade(long grade);

    List<Course> findAllBySemesterBetween(long semesterA,long semesterB);

}
