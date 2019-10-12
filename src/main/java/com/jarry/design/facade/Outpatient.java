package com.jarry.design.facade;

/**
 * 2.门诊类
 */
public class Outpatient implements Hospital {
    @Override
    public void seeAdoctor() {
        System.out.println("门诊ing");
    }
}
