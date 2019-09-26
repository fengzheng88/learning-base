package com.jarry.design.decorator;

/**
 * 客户端测试类
 */
public class ClientTest {
    public static void main(String[] args) {
        People jerry = new Jerry();
        People clothesFinery = new ClothesFinery(jerry);
        People shoesFinery = new ShoesFinery(clothesFinery);
        shoesFinery.dressUp();
    }
}
