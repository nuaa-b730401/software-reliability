package org.nuaa.b730401.softwarereliability.service.impl;

import org.nuaa.b730401.softwarereliability.entity.ProjectEntity;
import org.nuaa.b730401.softwarereliability.entity.Response;
import org.nuaa.b730401.softwarereliability.repository.IProjectRepository;
import org.nuaa.b730401.softwarereliability.service.IProjectService;
import org.nuaa.b730401.softwarereliability.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 18:58
 */
@Service
public class ProjectServiceImpl implements IProjectService {
    private final IProjectRepository projectRepository;

    public ProjectServiceImpl(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Response createProject(ProjectEntity project) {
        projectRepository.save(project);
        return new Response(Response.SUCCESS_CODE, "创建项目成功");
    }

    @Override
    public Response getProjectByUserId(Long id) {
        return new Response<ProjectEntity>(
                Response.SUCCESS_CODE,
                "获取数据成功",
                projectRepository.findProjectEntitiesByUserId(id)
        );
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Response updateProjectInfo(ProjectEntity project) {
        projectRepository.updateProjectInfo(project.getName(), project.getDescription(), project.getId());
        return new Response(Response.SUCCESS_CODE, "更新数据成功");
    }

    @Override
    public Response updateProjectData(MultipartFile file, Long id) throws IOException {
        FileUtil.saveFile(file, FileUtil.DATA_PATH + "/", id + ".data");
        projectRepository.updateProjectData("/" + id + ".data", id);
        return new Response(Response.SUCCESS_CODE, "更新文件成功");
    }

    @Override
    public Response deleteProject(Long id) {
        projectRepository.deleteById(id);
        return new Response(Response.SUCCESS_CODE, "删除数据成功");
    }
}
