package com.jarry.design.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构之学院
 */
public class College extends AbstractOrganization {

    //学院具有管理机构之院系
    List<AbstractOrganization> departments = new ArrayList<>();//组合

    //继承父类的属性，并设值
    College(String organName, String description) {
        super(organName, description);
    }

    @Override
    protected void addOrgan(AbstractOrganization abstractOrganization) {
        departments.add(abstractOrganization);
    }

    @Override
    protected void removeOrgan(AbstractOrganization abstractOrganization) {
        departments.remove(abstractOrganization);
    }

    @Override
    public void print() {
        System.out.println("    ===================" + this.getOrganName()+"下的院系===================");
        for(AbstractOrganization department : departments){
            department.print();
        }
    }
}
