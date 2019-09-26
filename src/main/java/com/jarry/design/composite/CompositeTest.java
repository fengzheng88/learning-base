package com.jarry.design.composite;

/**
 * 组合模式测试类
 */
public class CompositeTest {
    public static void main(String[] args) {
        Univercity shenzhen = new Univercity("深圳大学", "深大");

        College computeCollege = new College("计算机学院","计算机");
        College languageCollege = new College("语言学院","语言");

        computeCollege.addOrgan(new Department("计算机科学与技术系", "科学与技术"));
        computeCollege.addOrgan(new Department("信息工程系", "信息工程"));


        languageCollege.addOrgan(new Department("汉语系", "汉语系"));
        languageCollege.addOrgan(new Department("英语系", "英语系"));

        shenzhen.addOrgan(computeCollege);
        shenzhen.addOrgan(languageCollege);

        //深大下所有的学院和系
        shenzhen.print();


    }
}
