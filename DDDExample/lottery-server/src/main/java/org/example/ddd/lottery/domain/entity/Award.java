package org.example.ddd.lottery.domain.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 奖品
 * @author create 2022/8/30 by rao
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Component
public class Award {

    private Serializable id;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 被抽中的概率
     */
    private BigDecimal beDrawLotteryProbability;

}
