package com.jarry.proxy.self;

import com.jarry.proxy.jdk.Xiaoming;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * @Author Jarry
 * @Date 2019/7/10 17:37
 * @Description 自定义代理对象
 */
public class MyProxy {

    public static void main(String[] args) {

        newInstance(new MyClassLoader(MyProxy.class.getResource("").getPath()), Xiaoming.class.getInterfaces(), new MeipoHandler());
    }

    static final String ENTER = "\r\n";


    public static <T> T newInstance(MyClassLoader classLoader, Class[] clazz, MyInvocationHandler h) {

        //1.生成源码
        String javaSrc = generatorSrc(clazz, h);


        //2.将源码输出到.java文件中
        String url = MyProxy.class.getResource("").getPath() + "$Proxy0.java";
        //System.out.println(url);
        File file = new File(url);

        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos));
            bw.write(javaSrc, 0, javaSrc.length());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //3.编译源代码，并生成.class文件
        //3.1 获取java编译器
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //3.2 获取标准java文件管理器
        StandardJavaFileManager manager = compiler.getStandardFileManager(null, Locale.CHINESE, Charset.forName("UTF-8"));
        //3.3 获取java文件对象
        Iterable<? extends JavaFileObject> javaFileObjects = manager.getJavaFileObjects(file);
        //3.4 创建编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, null, null, javaFileObjects);
        task.call();
        try {
            manager.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            file.delete();
        }

        //4.将.class文件动态加载到JVM中
        //在classLoader.findClass("")中实现
        //5.返回被代理对象
        try {
            Class proxyObject = classLoader.findClass("$Proxy0");

            Constructor constructor = proxyObject.getConstructor(h.getClass());
            T o = (T) constructor.newInstance(h);

            return o;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return null;
    }

    /**
     * 1.生成源码
     *
     * @param
     * @param clazz
     * @return
     */
    private static String generatorSrc(Class<?>[] clazz, MyInvocationHandler h) {
        String className = "$Proxy0";

        //根据接口生成代理Java类
        StringBuffer sb = new StringBuffer();
        sb.append(MyProxy.class.getPackage()).append(";").append(ENTER);
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
        sb.append("this.h = h;").append(ENTER);
        sb.append("}").append(ENTER);

        for (Class clz : clazz) {
            //通过反射生成方法
            Method[] methodArr = clz.getMethods();
            for (Method method : methodArr) {

                //获取方法的修饰权限
                String modifier = Modifier.toString(method.getModifiers() ^ Modifier.ABSTRACT);
                sb.append(modifier).append(" ");
                //获取方法的返回类型
                sb.append(method.getReturnType()).append(" ");
                //获取方法名
                sb.append(method.getName()).append("(");
                //设置参数类型以及参数名
                Parameter[] parameters = method.getParameters();

                //参数值
                String argsValue = "";
                //参数类型
                String argsType = "";
                for (int i = 0; i < parameters.length; i++) {
                    sb.append(parameters[i].getType().getName()).append(" ").append(parameters[i].getName());
                    argsValue += parameters[i].getName();
                    argsType += parameters[i].getType().getName() + ".class";
                    if (i != parameters.length - 1) {
                        sb.append(",");
                        argsValue += ",";
                        argsType += ",";
                    }
                }

                sb.append(") {").append(ENTER);
                //方法体内容
                sb.append("try {");
                sb.append(Method.class.getName()).append(" method = ").append(clz.getName()).append(".class").append(".getMethod(").append("\"").append(method.getName())
                        .append("\",new Class[]{").append(argsType).append("});").append(ENTER);
                sb.append("this.h.invoke(this, method, new Object[]{");
                //参数内容
                sb.append(argsValue);
                sb.append("});").append(ENTER);
                sb.append("}catch(Throwable e) {e.printStackTrace();}");
                //
                sb.append("}").append(ENTER);

            }


        }


        sb.append("}");
        //System.out.println(sb);

        return sb.toString();
    }
}
