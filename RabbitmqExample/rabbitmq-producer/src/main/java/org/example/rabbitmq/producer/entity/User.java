package org.example.rabbitmq.producer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * desc: 用户信息对象
 *
 * @author create 2022/3/25 by rao
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 5142181889988504673L;
    private String name;

}
