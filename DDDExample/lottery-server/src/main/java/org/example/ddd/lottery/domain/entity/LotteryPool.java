package org.example.ddd.lottery.domain.entity;

import org.example.ddd.lottery.domain.service.Lottery;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 抽奖池
 *
 * @author create 2022/8/30 by rao
 */
@Scope(WebApplicationContext.SCOPE_REQUEST)
@Component
public class LotteryPool implements Lottery {

    private Serializable id;

    /**
     * 奖品列表
     */
    private final List<Award> awardList = new ArrayList<>();

    @Override
    public Award lottery() {
        if(CollectionUtils.isEmpty( awardList )){
            return null;
        }
        // 抽奖
        return awardList.get(0);
    }
}
