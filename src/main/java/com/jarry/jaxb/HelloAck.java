package com.jarry.jaxb;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
public class HelloAck {

    private String key;

    @XmlElement(name = "user")
    private List<User> users;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "HelloAck{" +
                "key='" + key + '\'' +
                ", users=" + users +
                '}';
    }
}