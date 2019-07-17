package com.jarry.proxy.cglib;

/**
 * @Author Jarry
 * @Date 2019/7/16 16:50
 * @Description
 */
public class CglibTest {
    public static void main(String[] args) {
        WangErXiao proxyWangErXiao = (WangErXiao)new PeopleHandler().getInstance(new WangErXiao());

        proxyWangErXiao.findLove("小芳");
    }
}
