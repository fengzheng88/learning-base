package com.jarry.design.decorator;

import com.jarry.design.decorator.Finery;
import com.jarry.design.decorator.People;

/**
 * 具体服饰类-衣服
 */
public class ClothesFinery extends Finery {
    ClothesFinery(People people) {
        super(people);
    }

    @Override
    public void dressUp() {
        people.dressUp();
        System.out.println("穿上衣服");
    }
}
