package com.jarry.proxy.self;

/**
 * @Author Jarry
 * @Date 2019/7/10 17:39
 * @Description 自定义类加载器-将class文件加载进JVM
 */
public class MyClassLoader extends ClassLoader {

    /**
     * 查找自定生成的java文件
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {






        return super.findClass(name);
    }
}
