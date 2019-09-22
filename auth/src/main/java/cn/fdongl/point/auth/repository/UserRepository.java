package cn.fdongl.point.auth.repository;

import cn.fdongl.point.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findFirstByUsername(String username);

    Page<User> findByUsernameLikeOrRealNameLike(String username, String realName, Pageable pageable);

}
