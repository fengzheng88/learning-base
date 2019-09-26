package com.jarry.design.composite;

/**
 * 组织机构抽象类
 */
public abstract class AbstractOrganization {

    //组织机构名称
    private String organName;

    //描述
    private String description;

    AbstractOrganization(String organName, String description){
        this.organName = organName;
        this.description = description;
    }

    //组织机构抽象类本身不能进行添加操作
    protected void addOrgan(AbstractOrganization abstractOrganization){
        new UnsupportedOperationException();
        throw new RuntimeException("不支持新增");
    }

    //组织机构抽象类本身不能进行移除操作
    protected void removeOrgan(AbstractOrganization abstractOrganization){
        throw new RuntimeException("不支持移除");
    }

    public String getOrganName() {
        return organName;
    }

    public void setOrganName(String organName) {
        this.organName = organName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public abstract void print();
}
