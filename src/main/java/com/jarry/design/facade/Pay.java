package com.jarry.design.facade;

/**
 * 付钱类
 */
public class Pay implements Hospital {
    @Override
    public void seeAdoctor() {
        System.out.println("付钱ing");
    }
}
