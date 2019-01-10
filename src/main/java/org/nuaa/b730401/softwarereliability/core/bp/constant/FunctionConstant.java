package org.nuaa.b730401.softwarereliability.core.bp.constant;

import org.nuaa.b730401.softwarereliability.core.bp.function.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2018/12/11 23:04
 */
public class FunctionConstant {
    /**
     * 损失函数
     */
    public static final Map<String, LossFunction> LOSS_FUNCTION_MAP = new HashMap<String, LossFunction>(){{
        put("normal", new NormalLossFunction());
    }};

    /**
     * 激活函数
     */
    public static final Map<String, ActivationFunction> ACTIVATION_FUNCTION = new HashMap<String, ActivationFunction>(){{
        put("useless", new UselessActivationFunction());
        put("relu", new ReluFunction());
        put("sigmoid", new SigmoidFunction());
    }};
}
