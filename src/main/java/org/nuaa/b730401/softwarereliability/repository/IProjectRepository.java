package org.nuaa.b730401.softwarereliability.repository;

import org.nuaa.b730401.softwarereliability.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 16:44
 */
public interface IProjectRepository extends JpaRepository<ProjectEntity, Long> {
    /**
     * 通过用户id获取项目数据
     * @param userId
     * @param limit
     * @param offset
     * @return
     */
    @Query(value = "select * from project where user_id = ?1 limit ?2 offset ?3", nativeQuery = true)
    List<ProjectEntity> findProjectEntitiesByUserId(Long userId, int limit, int offset);

    /**
     * 更新项目信息
     * @param name
     * @param description
     * @param id
     */
    @Modifying
    @Query(value = "update project set name = ?1, description = ?2 where id = ?3", nativeQuery = true)
    void updateProjectInfo(String name, String description, Long id);

    /**
     * 更新项目数据
     * @param dataPath
     * @param id
     */
    @Modifying
    @Query(value = "update project set data_path = ?1 where id = ?2", nativeQuery = true)
    void updateProjectData(String dataPath, Long id);
}
