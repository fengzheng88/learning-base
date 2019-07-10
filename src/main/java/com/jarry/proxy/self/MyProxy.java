package com.jarry.proxy.self;

import java.io.File;

/**
 * @Author Jarry
 * @Date 2019/7/10 17:37
 * @Description 自定义代理对象
 */
public class MyProxy {

    public static <T> T newInstance(ClassLoader classLoader, Class[] clazz, MyInvocationHandler h) {

        //1.生成java文件
        File file = new File(classLoader.getResource("").getPath()+".java");

        //2.编译生成的java文件
        //3.加载进内存









        return null;
    }
}
