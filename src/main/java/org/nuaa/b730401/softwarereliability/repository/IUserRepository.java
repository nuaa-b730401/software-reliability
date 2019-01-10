package org.nuaa.b730401.softwarereliability.repository;

import org.nuaa.b730401.softwarereliability.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 16:44
 */
public interface IUserRepository extends JpaRepository<UserEntity, Long>{
    /**
     * 根据用户名获取用户
     * @param name
     * @return
     */
    Optional<UserEntity> findUserEntityByName(String name);

    @Modifying
    @Query(value = "update user set name = ?1, phone = ?2, email = ?3, sex = ?4 where id = ?5", nativeQuery = true)
    void updateUserInfo(String name, String phone, String email, Integer sex, Long id);

    @Modifying
    @Query(value = "update user set password = ?1 where id = ?2", nativeQuery = true)
    void updatePassword(String password, Long id);

    @Modifying
    @Query(value = "update user set login_count = login_count +  1 where id = ?1", nativeQuery = true)
    void updateLoginCount(Long id);
}
