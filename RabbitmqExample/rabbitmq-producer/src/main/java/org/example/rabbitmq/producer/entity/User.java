package org.example.rabbitmq.producer.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * desc: 用户信息对象
 *
 * @author create 2022/3/25 by rao
 */
@Data
public class User implements Serializable {

    private String name;

}
