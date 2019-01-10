package org.nuaa.b730401.softwarereliability.service;

import org.nuaa.b730401.softwarereliability.core.exception.NetworkException;
import org.nuaa.b730401.softwarereliability.entity.Response;
import org.nuaa.b730401.softwarereliability.entity.form.BpPropertiesForm;
import org.nuaa.b730401.softwarereliability.entity.form.SVRPropertiesForm;

import java.io.IOException;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 21:09
 */
public interface IModelService {
    /**
     * J-M模型
     * @param projectId
     * @return
     */
    Response jmEvaluate(Long projectId) throws IOException;

    /**
     * G-O模型
     * @param projectId
     * @return
     */
    Response goEvaluate(Long projectId) throws IOException;

    /**
     * bp神经网络
     * @param projectId
     * @param form
     * @return
     */
    Response bpEvaluate(Long projectId, BpPropertiesForm form) throws NetworkException, IOException;

    /**
     * svr
     * @param projectId
     * @param form
     * @return
     */
    Response svrEvaluate(Long projectId, SVRPropertiesForm form);

    /**
     * 模型评估
     * @param projectId
     * @return
     */
    Response modelEvaluate(Long projectId);
}
