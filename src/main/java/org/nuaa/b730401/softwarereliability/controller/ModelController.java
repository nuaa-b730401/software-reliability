package org.nuaa.b730401.softwarereliability.controller;

import org.nuaa.b730401.softwarereliability.core.exception.NetworkException;
import org.nuaa.b730401.softwarereliability.entity.Response;
import org.nuaa.b730401.softwarereliability.entity.form.BpPropertiesForm;
import org.nuaa.b730401.softwarereliability.entity.form.SVRPropertiesForm;
import org.nuaa.b730401.softwarereliability.service.IModelService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/11 20:57
 */
@RestController
@CrossOrigin
@RequestMapping("/model")
public class ModelController {
    private final IModelService modelService;

    public ModelController(IModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/jm/{id}")
    public Response jm(@PathVariable(name = "id") Long projectId) throws IOException {
        return modelService.jmEvaluate(projectId);
    }

    @GetMapping("/go/{id}")
    public Response go(@PathVariable(name = "id") Long projectId) throws IOException {
        return modelService.goEvaluate(projectId);
    }

    @GetMapping("/bp/{id}")
    public Response bp(@PathVariable(name = "id") Long projectId, BpPropertiesForm form) throws IOException, NetworkException {
        return modelService.bpEvaluate(projectId, form);
    }

    @GetMapping("/svr/{id}")
    public Response svr(@PathVariable(name = "id") Long projectId, SVRPropertiesForm form) {
        return modelService.svrEvaluate(projectId, form);
    }

    @GetMapping("/evaluate/{id}")
    public Response evaluate(@PathVariable(name = "id") Long projectId) {
        return modelService.modelEvaluate(projectId);
    }
}
