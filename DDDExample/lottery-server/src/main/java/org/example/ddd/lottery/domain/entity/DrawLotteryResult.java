package org.example.ddd.lottery.domain.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

/**
 * 抽奖结果
 * @author create 2022/8/30 by rao
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Component
public class DrawLotteryResult {
}
