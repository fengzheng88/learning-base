package com.jarry.design.facade;

/**
 * 门面模式测试类
 */
public class FacadeTest {
    public static void main(String[] args) {
        System.out.println("病人来看病");
        Receptionist receptionist = new Receptionist();
        System.out.println("接待病人");
        receptionist.accept();
        System.out.println("病人看病完成");
    }
}
