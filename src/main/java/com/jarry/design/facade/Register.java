package com.jarry.design.facade;

/**
 * 1.挂号类
 */
public class Register implements Hospital {
    @Override
    public void seeAdoctor() {
        System.out.println("看病得先挂号,挂号ing");
    }
}
