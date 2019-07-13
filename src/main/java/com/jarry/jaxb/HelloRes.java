package com.jarry.jaxb;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * @XmlSeeAlso 指定泛型所使用的类型
 */

@XmlRootElement(name="response")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({HelloAck.class})
public class HelloRes extends Res<HelloAck> {
}