package org.example.ddd.lottery.infrastructure.po;

import lombok.Data;

import java.io.Serializable;

/**
 * 简单的用户信息
 * @author create 2022/8/22 by rao
 */
@Data
public class UserPO implements Serializable {

    private Long id;

    /**
     * 用户名称s
     */
    private String name;

    /**
     * 用户城市
     */
    private String city;

    /**
     * 用户组
     */
    private String userGroup;

}
