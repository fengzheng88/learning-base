package com.jarry.design.facade;

/**
 * 接待人员类-充当门面
 * 定义好步骤，让病人一键看病
 */
public class Receptionist {

    private Register register;

    private Outpatient outpatient;

    private Pay pay;

    private TakeMedicine takeMedicine;

    Receptionist(){
        register = new Register();
        outpatient = new Outpatient();
        pay = new Pay();
        takeMedicine = new TakeMedicine();
    }

    /**
     * 接待人员进行接待
     * 面向病人,病人无需关心看病的步骤,接待人员已经处理好看病的步骤
     */
    public void accept(){
        register.seeAdoctor();
        outpatient.seeAdoctor();
        pay.seeAdoctor();
        takeMedicine.seeAdoctor();
    }
}
