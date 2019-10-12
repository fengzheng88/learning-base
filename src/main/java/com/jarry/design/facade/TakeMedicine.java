package com.jarry.design.facade;

/**
 * 4.取药类
 */
public class TakeMedicine implements Hospital {
    @Override
    public void seeAdoctor() {
        System.out.println("取药ing");
    }
}
