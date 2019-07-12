package com.jarry.proxy.self;

import com.jarry.proxy.jdk.Xiaoming;

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

        newInstance(new MyClassLoader(), Xiaoming.class.getInterfaces(), new MeipoHandler());
    }

    static final String ENTER = "\r\n";


    public static <T> T newInstance(ClassLoader classLoader, Class[] clazz, MyInvocationHandler h) {

        generatorSrc(classLoader, clazz, h);

        //1.生成源码
        //2.将源码输出到.java文件中

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

        //3.编译源代码，并生成.class文件
        //4.将.class文件动态加载到JVM中
        //5.返回被代理对象


        return null;
    }

    /**
     * 1.生成源码
     *
     * @param classLoader
     * @param clazz
     * @return
     */
    private static String generatorSrc(ClassLoader classLoader, Class<?>[] clazz, MyInvocationHandler h) {
        String className = "$Proxy0";
        //根据接口生成代理Java类
        StringBuffer sb = new StringBuffer();
        sb.append(classLoader.getClass().getPackage()).append(";").append(ENTER);
        sb.append(ENTER);
        sb.append("public class " + className).append(" implements ");
        for (int i = 0; i < clazz.length; i++) {
            //获取实现的接口
            sb.append(clazz[i].getName());
            if (i != clazz.length - 1) {
                sb.append(",");
            }
        }
        sb.append(" {").append(ENTER);

        sb.append("private ").append(h.getClass().getName()).append(" h;").append(ENTER);

        //创建构造方法
        sb.append("public ").append(className).append("(").append(h.getClass().getName()).append(" h").append(")").append(" {").append(ENTER);
        sb.append("this.h = h").append(ENTER);
        sb.append("}").append(ENTER);

        for (Class clz : clazz) {
            //通过反射生成方法
            Method[] methodArr = clz.getMethods();
            for (Method method : methodArr) {

                System.out.println(method.toString());

                //获取方法的修饰权限
                String modifier = Modifier.toString(method.getModifiers()^ Modifier.ABSTRACT);
                sb.append(modifier).append(" ");
                //获取方法的返回类型
                sb.append(method.getReturnType()).append(" ");
                //获取方法名
                sb.append(method.getName()).append("(");
                //设置参数类型以及参数名
                Parameter[] parameters = method.getParameters();

                String args = "";
                for (int i = 0; i < parameters.length; i++) {
                    sb.append(parameters[i].getType().getName()).append(" ").append(parameters[i].getName());
                    args += parameters[i].getName();
                    if (i != parameters.length - 1) {
                        sb.append(",");
                        args += ",";
                    }
                }

                sb.append(") {").append(ENTER);
                //方法体内容
                //Method method = MyProxy.class.getMethod()
                sb.append("Method method = ").append(clz.getName());
                sb.append("this.h.invoke(this, method, new Object[]{");
                //参数内容
                sb.append(args);
                sb.append("});")

                        .append(ENTER);
                //
                sb.append("}").append(ENTER);

            }


        }


        sb.append("}");
        System.out.println(sb);


        return sb.toString();
    }
}
