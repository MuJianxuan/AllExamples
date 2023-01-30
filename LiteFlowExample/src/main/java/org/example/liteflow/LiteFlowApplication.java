package org.example.liteflow;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.example.liteflow.model.context.GetOrderInfoContext;
import org.example.liteflow.model.param.OrderInfoParam;
import org.example.liteflow.model.vo.OrderInfoVo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

/**
 * @author Rao
 * @Date 2023/01/16
 **/
@SpringBootApplication
public class LiteFlowApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(LiteFlowApplication.class, args);
        Optional.of(applicationContext.getBean(FlowExecutor.class)).ifPresent(flowExecutor -> {

//            demo1(flowExecutor);

//            demo2(flowExecutor);

            // 并行计算下 数据的问题
            demo3(flowExecutor);

        });

    }

    private static void demo3(FlowExecutor flowExecutor) {
        LiteflowResponse execute2Resp = flowExecutor.execute2Resp("parallelComputing", null, OrderInfoVo.class);
        if(execute2Resp.isSuccess()){
            OrderInfoVo orderInfoVo1 = execute2Resp.getSlot().getContextBean(OrderInfoVo.class);
            System.out.println("LiteFlowApplication:orderInfoVo1：" + orderInfoVo1);
        }
    }

    private static void demo2(FlowExecutor flowExecutor) {

        // 使用自定义参数传递上下文
        OrderInfoParam orderInfoParam = new OrderInfoParam();
        orderInfoParam.setId(1L);

        GetOrderInfoContext getOrderInfoContext = new GetOrderInfoContext(orderInfoParam);

        LiteflowResponse execute2Resp = flowExecutor.execute2Resp("getOrderInfo", getOrderInfoContext, OrderInfoVo.class);

        // 异常获取
        if (!execute2Resp.isSuccess()) {
            System.out.println("异常Code：" + execute2Resp.getCode());
            System.out.println("异常Cause：" + execute2Resp.getCause());
            System.out.println("异常Msg:" + execute2Resp.getMessage());

            return;
        }

        GetOrderInfoContext result = (GetOrderInfoContext) execute2Resp.getSlot().getRequestData();
        System.out.println("请求参数：" + result);

        // 可证实对象是同一个
        OrderInfoVo orderInfoVo1 = execute2Resp.getSlot().getContextBean(OrderInfoVo.class);
        System.out.println("LiteFlowApplication:orderInfoVo1：" + orderInfoVo1);

        OrderInfoVo orderInfoVo2 = (OrderInfoVo) execute2Resp.getFirstContextBean();
        System.out.println("LiteFlowApplication:orderInfoVo2：" + orderInfoVo2);

    }

    private static void demo1(FlowExecutor flowExecutor) {
        LiteflowResponse liteflowResponse = flowExecutor.execute2Resp("chain1", "arg");
    }
}
