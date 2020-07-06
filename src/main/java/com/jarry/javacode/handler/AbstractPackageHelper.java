package com.jarry.javacode.handler;

/**
 * 抽象包装助手对象
 * <p>
 * 不支持泛型(或者支持泛型，但未找到解决方案)
 * <p>
 * public abstract class AbstractPackageHelper<T, S> {
 *      public abstract T createT(T target, S source);
 * }
 * <p>
 * 经过研究字节码文件，这边支持泛型，但是对于JVM来说都会将类型抹除，所以最好的定义就如该类，
 * 如果确实需要使用泛型，那么在使用javassist生成字节码的时候需要生成2个方法，例如
 * 1. public User createT(User u, TUser u2){
 *      doSomeThing();
 *    }
 * 2. public Object createT(Object u, Object u2){
 *      User uu = (User)u;
 *      TUser uu2 = (TUser)u2;
 *      return this.createT(uu, uu2);
 *    }
 */
public abstract class AbstractPackageHelper {
    /**
     * 将source对象中的属性值赋值到target对象中
     * 通过注解形式获取属性对应关系
     *
     * @param target 目标对象实例
     * @param source 来源对象实例
     * @return
     */
    public abstract Object createT(Object target, Object source);
}

