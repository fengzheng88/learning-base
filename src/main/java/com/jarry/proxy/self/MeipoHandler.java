package com.jarry.proxy.self;

import com.jarry.proxy.jdk.Person;

import java.lang.reflect.Method;

/**
 * @Author Jarry
 * @Date 2019/7/10 17:33
 * @Description 代理处理类
 */
public class MeipoHandler<T> implements MyInvocationHandler {

    private T target;

    public T getInstance(T target){
        this.target = target;
        return MyProxy.newInstance(new MyClassLoader(), target.getClass().getInterfaces(), this);
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("[自定义]->我是媒婆，我这边有个小伙子要找对象");
        //执行被代理对象方法
        method.invoke(target, args);

        System.out.println("[自定义]->找到合适的姑娘");
        return null;
    }
}
