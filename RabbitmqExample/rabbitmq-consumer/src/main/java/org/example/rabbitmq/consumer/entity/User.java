package org.example.rabbitmq.consumer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * desc: 用户信息对象
 *
 *    测试一下对象接收的问题， 是否存在 序列化的问题
 *
 * @author create 2022/3/25 by rao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 5142181883988504673L;
    private String name;

}
