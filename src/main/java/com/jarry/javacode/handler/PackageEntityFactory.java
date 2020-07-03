package com.jarry.javacode.handler;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

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
        sb.append("public Object createT(Object target, Object source){ \n");
        //由于不支持泛型，只能在方法里面对对象进行强转
        sb.append(target.getName()).append(" t = (").append(target.getName()).append(")target; \n");
        sb.append(source.getName()).append(" s = (").append(source.getName()).append(")source; \n");

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

        //将拼接的字符串转成方法
        //参数1：方法字符串  参数2：实例类对象
        CtMethod cm = CtMethod.make(sb.toString(), implCtClass);
        //添加方法
        implCtClass.addMethod(cm);

        //获取实例字节码文件
        Class<?> helperClass = implCtClass.toClass();
        //生成实例对象
        helper = (AbstractPackageHelper) helperClass.newInstance();

        HELPER_MAP.putIfAbsent(target, helper);

        return helper;
    }
}
