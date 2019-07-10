package com.jarry.proxy.self;

import java.lang.reflect.Method;

/**
 * @Author Jarry
 * @Date 2019/7/10 14:04
 * @Description 自定义调用处理类
 */
public interface MyInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;
}
