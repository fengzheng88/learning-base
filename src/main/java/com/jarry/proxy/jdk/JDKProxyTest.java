package com.jarry.proxy.jdk;

import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author Jarry
 * @Date 2019/7/9 14:23
 * @Description 动态代理测试类
 */
public class JDKProxyTest {
    public static void main(String[] args) throws IOException {
        //被代理对象
        Xiaoming xiaoming = new Xiaoming();
        //代理对象总管
        MeiPo<Person> meiPo = new MeiPo<Person>();
        //被代理对象
        Person proxyPerson = meiPo.getInstance(xiaoming);
        //执行被代理方法
        proxyPerson.findLove();

        //获取字节码内容
       /*
        byte[] $Proxy0s = ProxyGenerator.generateProxyClass("$Proxy0", new Class[]{Person.class});
        FileOutputStream fos = new FileOutputStream(new File("D:/$Proxy.class"));
        fos.write($Proxy0s);
        fos.close();
        */

    }
}
