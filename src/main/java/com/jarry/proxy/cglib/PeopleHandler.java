package com.jarry.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @Author Jarry
 * @Date 2019/7/16 16:32
 * @Description 处理类-通过代理者继承被代理者，获取到被代理者的属性和方法
 */
public class PeopleHandler implements MethodInterceptor {

    //获取代理对象
    public Object getInstance(Object obj) {
        Enhancer enhancer = new Enhancer();
        //设置父类是谁
        enhancer.setSuperclass(obj.getClass());
        //设置方法回调，代理者调用PeopleHandler中的intercept方法
        enhancer.setCallback(this);
        //创建代理对象
        return enhancer.create();
    }

    /**
     *
     * @param target 代理对象
     * @param method 被代理方法
     * @param args 参数
     * @param methodProxy 代理方法
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object target, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {


        System.out.println(method);
        System.out.println(methodProxy);

        System.out.println("CGLIB代理对象处理开始");
        methodProxy.invokeSuper(target, args);
        System.out.println("CGLIB代理对象处理完成");
        return null;
    }
}
