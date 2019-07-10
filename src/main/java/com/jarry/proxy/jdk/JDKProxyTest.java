package com.jarry.proxy.jdk;

/**
 * @Author Jarry
 * @Date 2019/7/9 14:23
 * @Description 动态代理测试类
 */
public class JDKProxyTest {
    public static void main(String[] args) {
        //被代理对象
        Xiaoming xiaoming = new Xiaoming();
        //代理对象总管
        MeiPo<Person> meiPo = new MeiPo<Person>();
        //被代理对象
        Person proxyPerson = meiPo.getInstance(xiaoming);
        //执行被代理方法
        proxyPerson.findLove();
    }
}
