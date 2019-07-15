package com.jarry.proxy.self;

import com.jarry.proxy.jdk.Person;
import com.jarry.proxy.jdk.Xiaoming;

import java.io.IOException;

/**
 * @Author Jarry
 * @Date 2019/7/9 14:23
 * @Description 动态代理测试类
 */
public class SelfProxyTest {
    public static void main(String[] args) throws IOException {
        //被代理对象
        Xiaoming xiaoming = new Xiaoming();
        //代理对象总管
        MeipoHandler<Person> meiPo = new MeipoHandler<Person>();
        //被代理对象
        Person proxyPerson = meiPo.getInstance(xiaoming);
        //执行被代理方法
        proxyPerson.findLove("小美");

    }
}
