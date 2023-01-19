package org.example.liteflow.model.context;

import lombok.Data;
import org.example.liteflow.model.param.OrderInfoParam;

/**
 * @author Rao
 * @Date 2023/01/16
 **/
@Data
public class GetOrderInfoContext {

    private final OrderInfoParam orderInfoParam;

}
