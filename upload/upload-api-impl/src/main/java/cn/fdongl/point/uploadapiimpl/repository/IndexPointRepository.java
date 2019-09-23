package cn.fdongl.point.uploadapiimpl.repository;

import cn.fdongl.point.common.entity.File;
import cn.fdongl.point.common.entity.IndexPoint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IndexPointRepository extends JpaRepository<IndexPoint,Long> {

    int deleteAllByGrade(long grade);
    int deleteAllByCreateFrom(long createFrom);


}
