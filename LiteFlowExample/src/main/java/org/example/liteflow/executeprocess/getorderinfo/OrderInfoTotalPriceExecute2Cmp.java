package org.example.liteflow.executeprocess.getorderinfo;

import com.yomahub.liteflow.core.NodeComponent;
import org.example.liteflow.model.context.GetOrderInfoContext;
import org.example.liteflow.model.vo.OrderInfoVo;
import org.example.liteflow.model.vo.OrderVo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 *  会不会有并行下多线程问题
 *
 * @author Rao
 * @Date 2023/01/29
 **/
@Component("orderInfoTotalPriceExecute2Cmp")
public class OrderInfoTotalPriceExecute2Cmp extends NodeComponent {
    @Override
    public void process() throws Exception {
        System.out.println("orderInfoTotalPriceExecute2Cmp >> "+Thread.currentThread().getId());

        OrderInfoVo orderInfoVo1 = (OrderInfoVo) this.getFirstContextBean();
        orderInfoVo1.setTotalPrice(new BigDecimal("22"));

        // 在加锁的 情况下是可以读取到 另外一个线程执行的数据
        Thread.sleep(10);

        System.out.println("orderInfoTotalPriceExecute2Cmp >> "+orderInfoVo1.getTotalPrice());

    }
}
