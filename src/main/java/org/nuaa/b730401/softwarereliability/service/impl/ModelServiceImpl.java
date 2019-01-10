package org.nuaa.b730401.softwarereliability.service.impl;

import org.nuaa.b730401.softwarereliability.core.GoelOkumotoModel;
import org.nuaa.b730401.softwarereliability.core.JelinskiMorandaModel;
import org.nuaa.b730401.softwarereliability.core.bean.GoelOkumotoBean;
import org.nuaa.b730401.softwarereliability.core.bean.JmModelDataBean;
import org.nuaa.b730401.softwarereliability.core.bp.Layer;
import org.nuaa.b730401.softwarereliability.core.bp.Network;
import org.nuaa.b730401.softwarereliability.core.bp.constant.FunctionConstant;
import org.nuaa.b730401.softwarereliability.core.bp.matrix.Matrix;
import org.nuaa.b730401.softwarereliability.core.exception.MatrixSizeException;
import org.nuaa.b730401.softwarereliability.core.exception.NetworkException;
import org.nuaa.b730401.softwarereliability.entity.DatasetBean;
import org.nuaa.b730401.softwarereliability.entity.Response;
import org.nuaa.b730401.softwarereliability.entity.form.BpPropertiesForm;
import org.nuaa.b730401.softwarereliability.entity.form.LayerForm;
import org.nuaa.b730401.softwarereliability.entity.form.SVRPropertiesForm;
import org.nuaa.b730401.softwarereliability.repository.IProjectRepository;
import org.nuaa.b730401.softwarereliability.service.IModelService;
import org.nuaa.b730401.softwarereliability.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/1/10 23:23
 */
@Service
public class ModelServiceImpl implements IModelService {

    private final IProjectRepository projectRepository;

    public ModelServiceImpl(IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public Response jmEvaluate(Long projectId) throws IOException {
        List<DatasetBean> datasetBeans = FileUtil.readData(FileUtil.DATA_PATH + "/" + projectId + ".data");
        JmModelDataBean jm = JelinskiMorandaModel.execute(
                datasetBeans.stream().map(e -> e.getY()).collect(Collectors.toList())
        );
        return new Response<JmModelDataBean>(
                Response.SUCCESS_CODE, "J-M success", jm
        );
    }

    @Override
    public Response goEvaluate(Long projectId) throws IOException {
        List<DatasetBean> datasetBeans = FileUtil.readData(FileUtil.DATA_PATH + "/" + projectId + ".data");
        GoelOkumotoBean go = GoelOkumotoModel.execute(
                datasetBeans.stream().map(e -> e.getY()).collect(Collectors.toList())
        );
        return new Response<GoelOkumotoBean>(
                Response.SUCCESS_CODE, "G-O success", go
        );
    }

    @Override
    public Response bpEvaluate(Long projectId, BpPropertiesForm form) throws NetworkException, IOException {
        Network network = new Network()
                .setLearningRate(form.getLearningRate())
                .setLossThreshold(form.getLimit())
                .setMaxGeneration(form.getCount())
                .setLoss(FunctionConstant.LOSS_FUNCTION_MAP.get(form.getLoss()));

        for (LayerForm layer : form.getLayers()) {
            network.addLayer(
                    new Layer("layer", layer.getCol(), layer.getRow())
                    .setActivation(FunctionConstant.ACTIVATION_FUNCTION.get(layer.getActivation()))
            );
        }

        List<DatasetBean> datasetBeans = FileUtil.readData(FileUtil.DATA_PATH + "/" + projectId + ".data");
        List<DatasetBean> train = datasetBeans.subList(0, (int) (datasetBeans.size() * 0.7));
        List<DatasetBean> test = datasetBeans.subList((int) (datasetBeans.size() * 0.7), datasetBeans.size());
        Matrix input = new Matrix(1, 1);
        Matrix output = new Matrix(1, 1);
        Random random = new Random();
        IntStream.range(0, form.getGeneration()).forEach(i -> {
            DatasetBean target = train.get(random.nextInt(train.size()));
            input.set(0, 0, target.getX());
            output.set(0, 0, target.getY());
            try {
                network.execute(input, output);
            } catch (MatrixSizeException e) {
                e.printStackTrace();
            }
        });

        // test
        test.forEach(e -> {
            input.set(0, 0, e.getX());
            try {
                double y = network.execute(input).get(0, 0);
            } catch (MatrixSizeException e1) {
                e1.printStackTrace();
            }
        });

        datasetBeans.forEach(e -> {
            input.set(0, 0, e.getX());
            try {
                e.setPredict(network.execute(input).get(0, 0));;
            } catch (MatrixSizeException e1) {
                e1.printStackTrace();
            }
        });

        return new Response<DatasetBean>(
                Response.SUCCESS_CODE,
                "bp train success",
                datasetBeans
        );
    }

    @Override
    public Response svrEvaluate(Long projectId, SVRPropertiesForm form) {
        return null;
    }

    @Override
    public Response modelEvaluate(Long projectId) {
        return null;
    }
}
