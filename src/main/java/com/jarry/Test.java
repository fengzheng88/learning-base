package com.jarry;

/**
 * @Author Jarry
 * @Date 2019/7/16 15:10
 * @Description
 */
public class Test {

    class User {
        String name;
        int age;

        User(String name, int age){
            this.name = name;
            this.age = age;
        }
    }

    void allocation(int age){
        new User("zz" + age, age);
    }

    public static void main(String[] args) {
        Test t = new Test();
        long start = System.currentTimeMillis();
        for(int i =0; i<10000000; i++){
            t.allocation(i);
        }
        long time = System.currentTimeMillis() - start;
        System.out.println(time);
    }

}
