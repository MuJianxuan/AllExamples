package org.example.liteflow.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Rao
 * @Date 2023/01/16
 **/
@Data
public class OrderVo {

    private Long id;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 创建人
     */
    private String createBy;

}
