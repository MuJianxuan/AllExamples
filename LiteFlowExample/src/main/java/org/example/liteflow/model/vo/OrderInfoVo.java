package org.example.liteflow.model.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Rao
 * @Date 2023/01/29
 **/
@Data
public class OrderInfoVo {


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
