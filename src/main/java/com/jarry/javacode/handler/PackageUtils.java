package com.jarry.javacode.handler;


/**
 * 包装工具类
 */
public class PackageUtils {

    public static void doPackage(Object target, Object source){
        if(target == null || source == null){
            throw new RuntimeException("请求参数都不能为空");
        }

        try {
            AbstractPackageHelper packageHelp = PackageEntityFactory.createPackageHelp(target.getClass(), source.getClass());
            packageHelp.createT(target, source);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UserDO userDO = new UserDO();
        userDO.setNameD("张三");
        userDO.setAgeD("11");
        userDO.setClazzNameD("一般");
        userDO.setMoneyD("99.99");

        UserVO vo = new UserVO();

        doPackage(vo, userDO);
        System.out.println(vo);
    }
}
