package com.jarry.design.decorator;

/**
 * 服饰类
 */
public abstract class Finery implements People {

    protected People people;
    protected Finery(People people){
        this.people = people;
    }

    @Override
    public abstract void dressUp() ;
}
