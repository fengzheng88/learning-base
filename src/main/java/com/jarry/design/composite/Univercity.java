package com.jarry.design.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构之大学
 */
public class Univercity extends AbstractOrganization {

    //大学具有管理机构之学院
    List<AbstractOrganization> colleges = new ArrayList<>();

    //继承父类的属性，并设值
    Univercity(String organName, String description) {
        super(organName, description);
    }

    @Override
    protected void addOrgan(AbstractOrganization abstractOrganization) {
        colleges.add(abstractOrganization);
    }

    @Override
    protected void removeOrgan(AbstractOrganization abstractOrganization) {
        colleges.remove(abstractOrganization);
    }

    @Override
    public void print() {
        System.out.println("===================" + this.getOrganName()+"下的学院===================");
        for(AbstractOrganization college : colleges){
            college.print();
        }
    }
}
