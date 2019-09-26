package com.jarry.design.decorator;

/**
 * 具体服饰类-鞋子
 */
public class ShoesFinery extends Finery {
    ShoesFinery(People people) {
        super(people);
    }

    @Override
    public void dressUp() {
        people.dressUp();
        System.out.println("穿上鞋子");
    }
}
