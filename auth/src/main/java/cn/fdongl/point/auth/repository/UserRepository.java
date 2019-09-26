package cn.fdongl.point.auth.repository;

import cn.fdongl.point.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findFirstByUsername(String username);

    Page<User> findByUsernameLikeOrRealNameLike(String username, String realName, Pageable pageable);

    @Query(value = "delete from sys_user where create_from = :createFrom",nativeQuery = true)
    int deleteAllByCreateFrom(@Param("createFrom") long createFrom);

//    @Query(value = "delete from `user` where username in (:usernames)",nativeQuery = true)
//    @Modifying
    int deleteAllByUsernameIn(@Param("usernames")Iterable<String>usernames);

}
