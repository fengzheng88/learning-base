package com.jarry.jaxb;



import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;

/**
 * @Descripe 任何字段都使用@XmlAnyElement注释(lax = true)
 *  这意味着对于该字段，如果元素通过@XmlRootElement或@XmlElementDecl与类关联，
 *  则将使用相应类的实例填充该字段，否则该元素将被设置为org.w3c.dom的实例
 * @param <T>
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Res<T> {

    private String header;

    /**
     * 必须设置，否则无法设置，值为null
     *
     */
    @XmlAnyElement(lax = true)
    private T value;


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Res{" +
                "header='" + header + '\'' +
                ", value=" + value +
                '}';
    }
}