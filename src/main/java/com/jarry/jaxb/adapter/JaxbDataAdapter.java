package com.jarry.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author Jarry
 * @Date 2019/7/22 16:58
 * @Description 日期格式处理
 */
public class JaxbDataAdapter extends XmlAdapter<String, Date> {
    static final String STANDARM_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public Date unmarshal(String v) throws Exception {
        if (v == null) {
            return null;
        }

        DateFormat format = new SimpleDateFormat(STANDARM_DATE_FORMAT);
        return format.parse(v);
    }

    @Override
    public String marshal(Date v) throws Exception {
        DateFormat format = new SimpleDateFormat(STANDARM_DATE_FORMAT);
        return format.format(v);
    }
}
