package com.jarry.design.composite;

/**
 * 机构之院系
 */
public class Department extends AbstractOrganization {


    //继承父类的属性，并设值
    Department(String organName, String description) {
        super(organName, description);
    }


    @Override
    public void print() {
        System.out.println("            " + this.getOrganName());
    }
}
