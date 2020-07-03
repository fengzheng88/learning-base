package com.jarry.javacode.handler;

import java.lang.annotation.*;
import java.math.BigDecimal;

/**
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.FIELD})
public @interface MyColumn {
    //转换目标字段名
    String name();

    //类型转换目标对象
    TransferTo target() default TransferTo.OBJECT;

    enum TransferTo {
        INTEGER(Integer.class), BIGDECIMAL(BigDecimal.class), OBJECT(Object.class);
        Class clazz;

        TransferTo(Class clazz) {
            this.clazz = clazz;
        }
    }
}
