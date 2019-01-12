package org.nuaa.b730401.softwarereliability.service;

import org.nuaa.b730401.softwarereliability.entity.ProjectEntity;
import org.nuaa.b730401.softwarereliability.entity.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 16:45
 */
public interface IProjectService {
    /**
     * 创建项目
     * @param project
     * @return
     */
    Response createProject(ProjectEntity project);

    /**
     * 根据用户id获取项目列表
     * @param id
     * @param page
     * @param limit
     * @return
     */
    Response getProjectByUserId(Long id, int page, int limit);

    /**
     * 更新工程信息
     * @param project
     * @return
     */
    Response updateProjectInfo(ProjectEntity project);

    /**
     * 更新工程失效数据
     * @param file
     * @param id
     * @return
     */
    Response updateProjectData(MultipartFile file, Long id) throws IOException;

    /**
     * 删除工程
     * @param id
     * @return
     */
    Response deleteProject(Long id);
}
