package com.jarry.jaxb;



import com.jarry.jaxb.adapter.JaxbCdataAdapter;
import com.jarry.jaxb.adapter.JaxbDataAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;

/**
 * @Author Jarry
 * @Date 2019/7/12 18:01
 * @Description
 */

@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    private String name;

    private int age;

    /**
     * 通过适配器修改日期格式
     */
    @XmlJavaTypeAdapter(value= JaxbDataAdapter.class)
    private Date date;

    /**
     * 当传输的url中包含转义字符的时候，需要做特殊处理
     * 1.添加<!CDATA[[http://www.baidu.com?a=1&b=2]]>
     * 2.marshaller需设置特殊字符不要转义
     */
    @XmlJavaTypeAdapter(value = JaxbCdataAdapter.class)
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", date=" + date +
                ", url='" + url + '\'' +
                '}';
    }
}
