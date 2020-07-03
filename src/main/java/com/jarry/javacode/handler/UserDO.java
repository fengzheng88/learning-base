package com.jarry.javacode.handler;

import lombok.Data;

/**
 * 测试来源对象
 */
@Data
public class UserDO {
    @MyColumn(name = "name")
    private String nameD;

    @MyColumn(name = "age", target = MyColumn.TransferTo.INTEGER)
    private String ageD;

    @MyColumn(name = "clazzName")
    private String clazzNameD;

    @MyColumn(name = "money", target = MyColumn.TransferTo.BIGDECIMAL)
    private String moneyD;
}
