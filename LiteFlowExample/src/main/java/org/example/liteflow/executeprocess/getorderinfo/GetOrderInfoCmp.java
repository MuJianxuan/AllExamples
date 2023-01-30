package org.example.liteflow.executeprocess.getorderinfo;

import com.yomahub.liteflow.core.NodeComponent;
import org.example.liteflow.model.context.GetOrderInfoContext;
import org.example.liteflow.model.param.OrderInfoParam;
import org.example.liteflow.model.vo.OrderInfoVo;
import org.example.liteflow.model.vo.OrderVo;
import org.springframework.stereotype.Component;

/**
 * @author Rao
 * @Date 2023/01/16
 **/
@Component("getOrderInfoCmp")
public class GetOrderInfoCmp extends NodeComponent {

    @Override
    public void process() throws Exception {
        GetOrderInfoContext getOrderInfoContext = (GetOrderInfoContext) this.getRequestData();
        OrderInfoParam orderInfoParam = getOrderInfoContext.getOrderInfoParam();
        Long orderId = orderInfoParam.getId();

        Thread.sleep(100);

        OrderVo orderVo = new OrderVo();
        orderVo.setId( orderId);
        orderVo.setCreateBy("Rao");
        getOrderInfoContext.setOrderVo(orderVo);

        OrderInfoVo orderInfoVo1 = (OrderInfoVo) this.getFirstContextBean();
        OrderInfoVo orderInfoVo2 = this.getSlot().getContextBean(OrderInfoVo.class);
        System.out.println(orderInfoVo1 == orderInfoVo2);


        orderInfoVo1.setId( orderId);
        orderInfoVo1.setCreateBy("Rao");

        // 异常会中断后续节点的执行 且外部调用无法捕获异常
//        throw new RuntimeException("xxx");
    }

}
