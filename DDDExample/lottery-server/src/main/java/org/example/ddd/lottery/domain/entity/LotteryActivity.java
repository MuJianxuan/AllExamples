package org.example.ddd.lottery.domain.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

/**
 * 抽奖活动
 * @author create 2022/8/30 by rao
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Component
public class LotteryActivity {

    private Serializable id;

    /**
     * 用户值信息
     */
    private Serializable userId;

    /**
     * 奖品值信息
     */
    private Award award;



}
