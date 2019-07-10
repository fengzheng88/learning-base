package com.jarry.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Author Jarry
 * @Date 2019/7/9 14:40
 * @Description 处理代理对象
 */
public class MeiPo<T> implements InvocationHandler {

    private T target;

    /**
     * 获取代理对象
     * @param target
     * @return
     */
    public T getInstance(T target){
        this.target = target;
        return (T)Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
    }

    /**
     * 反射
     * @param proxy 代理对象
     * @param method 代理方法
     * @param args 参数
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("我是媒婆，我这边有个小伙子要找对象");
        //执行被代理对象方法
        method.invoke(target, args);

        System.out.println("找到合适的姑娘");

        return null;
    }
}
