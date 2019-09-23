package cn.fdongl.point.uploadapiimpl.repository;

import cn.fdongl.point.common.entity.File;
import cn.fdongl.point.common.entity.Plan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlanRepository extends JpaRepository<Plan,Long> {



}
