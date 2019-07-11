package com.jarry.proxy.self;

import com.jarry.proxy.jdk.Person;
import com.jarry.proxy.jdk.Xiaoming;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

/**
 * @Author Jarry
 * @Date 2019/7/10 17:37
 * @Description 自定义代理对象
 */
public class MyProxy {

    public static void main(String[] args) {
        newInstance(new MyClassLoader(), Xiaoming.class.getInterfaces(), null);
    }

    static final String ENTER = "\r\n";



    public static <T> T newInstance(ClassLoader classLoader, Class[] clazz, MyInvocationHandler h) {

        String className = "$Proxy0";
        //根据接口生成代理类
        StringBuffer sb = new StringBuffer();
        sb.append("package ").append(classLoader.getClass().getName()).append(";").append(ENTER);
        sb.append(ENTER);
        sb.append("public class " + className).append(" implements ");
        for(int i=0; i<clazz.length; i++ ){
            //获取实现的接口
            sb.append(clazz[i].getName());
            if(i != clazz.length -1){
                sb.append(",");
            }
        }
        sb.append(" {").append(ENTER);

        sb.append("private MyInvocationHandler h;").append(ENTER);



        for(Class clz : clazz){
            //通过反射生成方法
            Method[] methodArr = clz.getMethods();
            for(Method method : methodArr){
                //获取方法的修饰权限
                String modifier = Modifier.toString(method.getModifiers());
                sb.append(modifier).append(" ");
                //获取方法的返回类型
                sb.append(method.getReturnType()).append(" ");
                //获取方法名
                sb.append(method.getName()).append("(");
                //设置参数类型以及参数名
                Parameter[] parameters = method.getParameters();
                for(int i=0; i< parameters.length; i++){
                    sb.append(parameters[i].getType().getName()).append(" ").append(parameters[i].getName());
                    if(i != parameters.length -1){
                        sb.append(",");
                    }
                }

                sb.append(") {").append(ENTER);
            }
            //方法体内容

            //

        }


        sb.append("}");
        System.out.println(sb);



//        //1.生成java文件
//        String url = classLoader.getResource("").getPath() +"$Proxy0.java";
//        File file = new File(url);
//
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(file);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        //2.编译生成的java文件
        //3.加载进内存









        return null;
    }
}
