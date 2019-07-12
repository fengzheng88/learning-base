package com.jarry.jaxb;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@ToString
@XmlRootElement(name = "body")
@XmlAccessorType(XmlAccessType.FIELD)
public class HelloAck {

    private String key;

    @XmlElement(name = "user")
    private List<User> users;
}