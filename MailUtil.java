package com.indctr.util;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * JavaMail发送邮件:前提是邮箱里帐号设置要开启POP3/SMTP协议
 */
public class MailUtil {
    public final static String HOST_QQ = "smtp.qq.com";
    public final static String HOST_SINA= "smtp.sina.com";

    /**
     *
     * @param host
     *            服务器地址,sina,QQ等
     * @param userName
     *            发送的邮箱账户，如xxxx@sina.com
     * @param passWord
     *            发送的邮箱密码
     * @param toAddr
     *            目标地址，如yyy@sina.com
     * @param title
     *            邮件标题
     * @param msg
     *            邮件内容
     * @throws Exception
     */
    public static void sendMail(String host, String userName, String passWord, String toAddr, String title, String msg)
            throws Exception {
        Properties prop = new Properties();
        // 开启debug调试，以便在控制台查看
        prop.setProperty("mail.debug", "true");
        // 设置邮件服务器主机名
        prop.setProperty("mail.host", host);
        // 发送服务器需要身份验证
        prop.setProperty("mail.smtp.auth", "true");
        // 发送邮件协议名称
        prop.setProperty("mail.transport.protocol", "smtp");

        // 开启SSL加密，否则会失败
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.socketFactory", sf);

        // 创建session
        Session session = Session.getInstance(prop);
        // 通过session得到transport对象
        Transport ts = session.getTransport();
        // 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
        ts.connect(host, userName, passWord);
        // 创建邮件
        MimeMessage message = new MimeMessage(session);
        // 指明邮件的发件人
        message.setFrom(new InternetAddress(userName));
        // 指明邮件的收件人
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toAddr));
        // 邮件的标题
        message.setSubject(title);
        // 邮件的文本内容
        message.setContent(msg, "text/html;charset=UTF-8");
        // 发送邮件
        ts.sendMessage(message, message.getAllRecipients());
        ts.close();
    }


}
