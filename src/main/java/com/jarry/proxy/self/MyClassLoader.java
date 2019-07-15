package com.jarry.proxy.self;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author Jarry
 * @Date 2019/7/10 17:39
 * @Description 自定义类加载器-将class文件加载进JVM
 */
public class MyClassLoader extends ClassLoader {

    File fileDir;

    public MyClassLoader(String path) {
        fileDir = new File(path);
    }

    /**
     * 查找自定生成的java文件
     * @param name
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        String className = MyProxy.class.getPackage().getName() + "." + name;
        System.out.println(className);

        if(fileDir != null){
            File newFile = new File(fileDir, name +".class");

            System.out.println(newFile.getPath());
            if(newFile.exists()){
                FileInputStream fis = null;

                try{
                    fis = new FileInputStream(newFile);

                    int len = 0;
                    byte[] b = new byte[1024];
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    while((len = fis.read(b)) != -1){
                        baos.write(b, 0, len);
                    }
                    return defineClass(className, baos.toByteArray(), 0, baos.size());

                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    newFile.delete();
                }
            }
        }





        return super.findClass(name);
    }
}
