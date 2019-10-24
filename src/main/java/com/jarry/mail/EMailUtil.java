package com.jarry.mail;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Properties;

/**
 * 发送邮件的工具类
 */
public class EMailUtil {

    //发件人邮箱地址
    static String address = "1@qq.com";
    //发件人邮箱密码
    static String password = "onjasjklvovxbghb";
    //邮件服务器主机名
    static String host = "smtp.qq.com";
    //收件人邮箱地址
    static String receive = "1@qq.com";

    public static void main(String[] args) {

        sendMessage("测试邮件", "您好:<br>谢谢", receive);
    }

    /**
     * 发送消息
     * @param subject 主题
     * @param text 正文
     * @param receiveAddress 收件人
     */
    public static void sendMessage(String subject, String text, String receiveAddress) {
        Session session = getSession();

        //内容信息-文件
        MimeBodyPart mbp = new MimeBodyPart();
        DataHandler dataHandler = new DataHandler(new FileDataSource("C:\\Users\\Administrator\\Desktop\\ms1.jpg"));
        try {
            mbp.setDataHandler(dataHandler);
            mbp.setFileName(dataHandler.getName());
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        //内容信息-正文
        BodyPart contentPart = new MimeBodyPart();
        try {
            contentPart.setContent(text, "text/html;charset=utf8");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        MimeMultipart multipart = new MimeMultipart();
        try {
            multipart.addBodyPart(mbp);
            multipart.addBodyPart(contentPart);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        Message message = getMimeMessage(session, subject, text, receiveAddress, multipart);
        try {
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static Message getMimeMessage(Session session, String subject, String text, String receiveAddress, Multipart multipart) {
        MimeMessage message = new MimeMessage(session);
        try {
            //发件人
            message.setFrom(new InternetAddress(address));
            //收件人
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(receiveAddress));
            //抄送人
            message.addRecipient(Message.RecipientType.CC, new InternetAddress(receiveAddress));
            //密送人
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress(receiveAddress));
            //主题
            message.setSubject(subject);
            if (null != multipart) {
                //设置附件/正文
                message.setContent(multipart);
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    /**
     * 创建会话连接
     * @return
     */
    public static Session getSession() {
        return Session.getDefaultInstance(getProperties(), new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(address, password);
            }
        });
    }

    /**
     * 设置连接属性
     * @return
     */
    private static Properties getProperties() {
        Properties properties = System.getProperties();
        //设置邮件服务器主机地址
        properties.setProperty("mail.smtp.host", host);
        //设置邮件服务器端口，默认25
        properties.setProperty("mail.smtp.port", "465");
        //需要验证用户名密码
        properties.put("mail.smtp.auth", "true");
        try {
            //开启SSL加密
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.ssl.socketFactory", sf);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
