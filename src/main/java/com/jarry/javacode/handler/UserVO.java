package com.jarry.javacode.handler;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 测试目标对象
 */
@Data
public class UserVO {
    private String name;

    private Integer age;

    private String clazzName;

    private BigDecimal money;
}
