package com.jarry.jaxb.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * @Author Jarry
 * @Date 2019/7/22 16:58
 * @Description 含有特殊字符处理
 */
public class JaxbCdataAdapter extends XmlAdapter<String, String> {

    /**
     * 使用<![CDATA[]]>表示这里面的特殊字符不需要处理
     * @param arg0
     * @return
     * @throws Exception
     */
    @Override
    public String marshal(String arg0) throws Exception {
        return "<![CDATA[" + arg0 + "]]>";
    }
    @Override
    public String unmarshal(String arg0) throws Exception {
        return arg0;
    }
}