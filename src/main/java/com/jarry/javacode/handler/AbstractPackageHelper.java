package com.jarry.javacode.handler;

/**
 * 抽象包装助手对象
 * <p>
 * 不支持泛型(或者支持泛型，但未找到解决方案)
 * <p>
 * public abstract class AbstractPackageHelper<T, S> {
 * public abstract T createT(T target, S source);
 * }
 */
public abstract class AbstractPackageHelper {
    /**
     * 将source对象中的属性值赋值到target对象中
     * 通过注解形式获取属性对应关系
     * @param target 目标对象实例
     * @param source 来源对象实例
     * @return
     */
    public abstract Object createT(Object target, Object source);
}

