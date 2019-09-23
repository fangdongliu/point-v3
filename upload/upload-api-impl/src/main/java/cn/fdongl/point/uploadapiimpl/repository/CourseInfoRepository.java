package cn.fdongl.point.uploadapiimpl.repository;

import cn.fdongl.point.common.entity.CourseInfo;
import cn.fdongl.point.common.entity.IndexPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseInfoRepository extends JpaRepository<CourseInfo,Long> {

    int deleteAllByGrade(long grade);
    int deleteAllByCreateFrom(long createFrom);

    CourseInfo findFirstByGradeBeforeAndNumberOrderByGrade(long grade,String number);

}
