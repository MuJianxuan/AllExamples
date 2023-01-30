package org.example.liteflow.executeprocess.getorderinfo;

import com.yomahub.liteflow.core.NodeComponent;
import org.example.liteflow.model.context.GetOrderInfoContext;
import org.example.liteflow.model.vo.OrderInfoVo;
import org.example.liteflow.model.vo.OrderVo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author Rao
 * @Date 2023/01/16
 **/
@Component("orderInfoTotalPriceExecuteCmp")
public class OrderInfoTotalPriceExecuteCmp extends NodeComponent {

    /**
     * 并行计算
     * @throws Exception
     */
    @Override
    public void process() throws Exception {
        System.out.println("orderInfoTotalPriceExecuteCmp >> "+Thread.currentThread().getId());

        OrderInfoVo orderInfoVo1 = (OrderInfoVo) this.getFirstContextBean();
        orderInfoVo1.setTotalPrice(new BigDecimal("11"));

        System.out.println("orderInfoTotalPriceExecuteCmp >> "+orderInfoVo1.getTotalPrice());
    }

//    @Override
//    public void process() throws Exception {
//
//        // 前序执行组件错误则无法执行
//        System.out.println("orderInfoTotalPriceExecuteCmp被执行了");
//
//        GetOrderInfoContext requestData = (GetOrderInfoContext) this.getRequestData();
//        OrderVo orderVo = requestData.getOrderVo();
//
//        orderVo.setTotalPrice(new BigDecimal("12.22"));
//
//        OrderInfoVo orderInfoVo1 = (OrderInfoVo) this.getFirstContextBean();
//        OrderInfoVo orderInfoVo2 = this.getSlot().getContextBean(OrderInfoVo.class);
//        System.out.println("orderInfoVo1:"+orderInfoVo1 );
//        System.out.println("orderInfoVo2:"+orderInfoVo2 );
//
//    }
}
