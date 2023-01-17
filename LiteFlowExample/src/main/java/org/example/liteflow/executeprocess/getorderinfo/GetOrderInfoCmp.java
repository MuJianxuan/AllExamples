package org.example.liteflow.executeprocess.getorderinfo;

import com.yomahub.liteflow.core.NodeComponent;
import org.example.liteflow.model.param.OrderInfoParam;
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
        OrderInfoParam orderInfoParam = (OrderInfoParam) this.getRequestData();
        Long orderId = orderInfoParam.getId();

        Thread.sleep(100);

        OrderVo orderVo = new OrderVo();
        orderVo.setId( orderId);
        orderVo.setCreateBy("Rao");

//        this.set
    }

}
