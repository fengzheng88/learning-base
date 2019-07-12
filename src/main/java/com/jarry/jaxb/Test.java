package com.jarry.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Jarry
 * @Date 2019/7/12 16:28
 * @Description 将xml转成泛型java对象
 */
public class Test {
    public static void main(String[] args) {

        HelloRes res = new HelloRes();
        res.setHeader("213123");
        HelloAck helloAck = new HelloAck();
        helloAck.setKey("099989");
        User user1 = new User();
        user1.setAge(100);
        user1.setName("张三");

        User user2 = new User();
        user2.setAge(200);
        user2.setName("李四");

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        helloAck.setUsers(userList);

        res.setValue(helloAck);


        String s = XMLUtil.convertToXml(res);
        System.out.println(s);

//
//        String xml = "" +
//                "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
//                "<response>\n" +
//                "\t<header>header_val</header>\n" +
//                "\t<body>\n" +
//                "\t\t<key>key_val</key>\n" +
//                "\t</body>\n" +
//                "</response>";
//
//        try {
//            JAXBContext jc = JAXBContext.newInstance(HelloRes.class);
//            Unmarshaller unmar = jc.createUnmarshaller();
//            HelloRes r = (HelloRes) unmar.unmarshal(new StringReader(xml));
//            System.out.println(r);
//        } catch (JAXBException e) {
//            e.printStackTrace();
//        }

    }
}
