package org.nuaa.b730401.softwarereliability.controller;

import org.nuaa.b730401.softwarereliability.entity.ProjectEntity;
import org.nuaa.b730401.softwarereliability.entity.Response;
import org.nuaa.b730401.softwarereliability.service.IProjectService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 20:59
 */
@RestController
@CrossOrigin
@RequestMapping("/project")
public class ProjectController {
    private final IProjectService projectService;

    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/user/{id}")
    public Response getProjectList(@PathVariable(name = "id") Long id) {
        return projectService.getProjectByUserId(id);
    }

    @PostMapping
    public Response createProject(ProjectEntity project) {
        return projectService.createProject(project);
    }

    @PutMapping
    public Response updateProjectInfo(ProjectEntity project) {
        return projectService.updateProjectInfo(project);
    }

    @PutMapping("/data")
    public Response updateProjectData(MultipartFile file, Long id) throws IOException {
        return projectService.updateProjectData(file, id);
    }

    @DeleteMapping("/{id}")
    public Response deleteProject(@PathVariable(name =  "id") Long id) {
        return projectService.deleteProject(id);
    }
}
