package com.jarry.javacode.handler;

import javassist.CannotCompileException;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建包装助手工厂
 */
public class PackageEntityFactory {

    private PackageEntityFactory() {
    }

    private static final Map<Class, AbstractPackageHelper> HELPER_MAP = new ConcurrentHashMap<>();

    /**
     * 获取动态生成助手实例对象
     *
     * @param target 目标对象类型
     * @param source 来源对象类型
     * @return
     * @throws Exception
     */
    public static AbstractPackageHelper createPackageHelp(Class target, Class source) throws Exception {
        if (target == null || source == null) {
            throw new RuntimeException("未传入目标对象或者来源对象类型");
        }

        AbstractPackageHelper helper = HELPER_MAP.get(target);

        if (helper != null) {
            return helper;
        }

        /**************************************************************************
         *                     通过javassist动态生成get、set代码                     *
         **************************************************************************/

        //获取类池
        ClassPool pool = ClassPool.getDefault();

        //在web服务器时需要添加classpath路径，否则会报javassist.NotFoundException
        //官网描述：The default ClassPool returned by a static method ClassPool.getDefault() searches the same path that the underlying JVM (Java virtual machine) has. If a program is running on a web application server such as JBoss and Tomcat, the ClassPoolobject may not be able to find user classes since such a web application server uses multiple class loaders as well as the system class loader. In that case, an additional class path must be registered to the ClassPool.
        ClassClassPath classPath = new ClassClassPath(PackageEntityFactory.class);
        pool.insertClassPath(classPath);

        pool.appendSystemPath();

        //导入相关类,这边可以不用写，只要生成的代码都使用相对路径(带包名)即ok(ps:也就是不使用xxx.getSimpleName())
        pool.importPackage(target.getName());
        pool.importPackage(source.getName());

        //获取抽象助手类
        CtClass abstractCtClass = pool.getCtClass(AbstractPackageHelper.class.getName());

        //定义自动生成代码的相对路径，生成在AbstractPackageHelper同级包下
        final String helperImplPath = AbstractPackageHelper.class.getPackage().getName() + "." + target.getSimpleName() + "Helper";
        //创建助手类 1:助手类路径 2：父类(继承)
        CtClass implCtClass = pool.makeClass(helperImplPath, abstractCtClass);

        //定义泛型(目前只会给继承类定义泛型，实现接口暂时不会)  类似 =>  extends AbstractPackageHelper<T, S>
       /* String generic = AbstractPackageHelper.class.getName() + "<" + target.getName() + "," + source.getName() + ">";
        implCtClass.setGenericSignature(new SignatureAttribute.TypeVariable(generic).encode());*/

        //创建默认构造器 1:参数 2：构造器的声明 => public A(){}
        CtConstructor ctConstructor = new CtConstructor(new CtClass[0], implCtClass);
        ctConstructor.setBody("{}");

        /**
         * 写法2 CtConstructor ctConstructor2 = CtNewConstructor.make("public A(){}", implCtClass);
         */
        //往生成的类中添加构造方法
        implCtClass.addConstructor(ctConstructor);

        //创建函数代码字符串，即重写父类的方法,手动拼接
        final StringBuilder sb = new StringBuilder();

        //创建方法一
        CtMethod ctMethod = createMethod1(sb, target, source, implCtClass);

        //创建方法二
        //CtMethod ctMethod = createMethod2(sb, target, source, implCtClass, pool);

        //添加方法
        implCtClass.addMethod(ctMethod);


        //输出字节码文件查看是否合理
        implCtClass.writeFile("E:\\");

        //获取实例字节码文件
        Class<?> helperClass = implCtClass.toClass();
        //生成实例对象
        helper = (AbstractPackageHelper) helperClass.newInstance();

        HELPER_MAP.putIfAbsent(target, helper);

        return helper;
    }


    /**
     * 完全手动拼接方法
     *
     * @param sb
     * @param target
     * @param source
     * @param implCtClass
     * @return
     * @throws NoSuchFieldException
     * @throws CannotCompileException
     */
    public static CtMethod createMethod1(StringBuilder sb, Class target, Class source, CtClass implCtClass) throws NoSuchFieldException, CannotCompileException {
        sb.append("public Object createT(Object target, Object source){ \n");
        sb.append(target.getName()).append(" t = (").append(target.getName()).append(")target; \n");
        sb.append(source.getName()).append(" s = (").append(source.getName()).append(")source; \n");

        commonBody(sb, target, source);
        //参数1：方法字符串  参数2：实例类对象
        CtMethod cm = CtMethod.make(sb.toString(), implCtClass);
        return cm;
    }

    /**
     * 部分手动拼接方法
     *
     * @param sb
     * @param target
     * @param source
     * @param implCtClass
     * @param pool
     * @return
     * @throws NoSuchFieldException
     * @throws CannotCompileException
     * @throws NotFoundException
     */
    public static CtMethod createMethod2(StringBuilder sb, Class target, Class source, CtClass implCtClass, ClassPool pool) throws NoSuchFieldException, CannotCompileException, NotFoundException {
        //从类池中获取已经存在的Object字节码对象
        CtClass returnType = pool.get("java.lang.Object");
        //由于参数都是Object
        CtClass[] parameter = new CtClass[]{returnType, returnType};

        sb.append("{ \n");
        //由于参数名未指定，所以只能用$1、$2等代替参数别名
        sb.append(target.getName()).append(" t = (").append(target.getName()).append(")$1; \n");
        sb.append(source.getName()).append(" s = (").append(source.getName()).append(")$2; \n");

        commonBody(sb, target, source);

        CtMethod ctMethod = CtNewMethod.make(returnType, "createT", parameter, null, sb.toString(), implCtClass);

        return ctMethod;
    }

    /**
     * 公共方法体拼接方法
     *
     * @param sb
     * @param target
     * @param source
     * @throws NoSuchFieldException
     */
    public static void commonBody(StringBuilder sb, Class target, Class source) throws NoSuchFieldException {
        //通过反射获取来源类字段属性，并拼接get/set字符串
        Field[] fArr = source.getDeclaredFields();
        for (Field f : fArr) {
            MyColumn annotation = f.getAnnotation(MyColumn.class);
            if (annotation == null) {
                //无自定义注解则跳过
                continue;
            }
            //获取定义的列名
            String columnName = annotation.name();

            //判断目标对象是否存在该属性
            Field declaredField = target.getDeclaredField(columnName);
            if (declaredField == null) {
                //属性不存在，说明填写错误
                //continue;
                throw new RuntimeException(columnName + "属性名填写错误，请检查");
            }

            //属性类型转换，将来源类型转成目标类型(目前只支持String ->Integer, String -> BigDecimal)
            Class targetClass = annotation.target().clazz;
            //this mean's => t.setXxxx(
            sb.append("t.set").append(columnName.substring(0, 1).toUpperCase()).append(columnName.substring(1)).append("(");
            //this mean's => s.getXxxx()
            String getMethod = "s.get" + f.getName().substring(0, 1).toUpperCase() + f.getName().substring(1) + "()";

            //未定义转换类型，则直接赋值
            if (targetClass == Object.class) {
                sb.append(getMethod);
            } else {
                //this mean's => ( s.getXxx() != null && s.getXxx() != "" ) ?
                sb.append("(").append(getMethod).append(" != null && ").append(getMethod).append(" != \"\") ?");
                if (targetClass == Integer.class) {
                    //this mean's => new Integer(s.getXxx())
                    sb.append(" new ").append(Integer.class.getName()).append(" (").append(getMethod).append(")");
                } else if (targetClass == BigDecimal.class) {
                    sb.append(" new ").append(BigDecimal.class.getName()).append(" (").append(getMethod).append(")");
                }
                sb.append(" : null");

                //整个 => t.setXxx(  (s.getZzz() != null && s.getZzz != "") ? new Integer(s.getZzz()) : null )
            }
            sb.append(");\n");
        }

        sb.append("return t;");
        sb.append(" } ");
    }
}
