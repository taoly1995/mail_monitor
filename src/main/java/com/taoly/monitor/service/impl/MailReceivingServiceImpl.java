package com.taoly.monitor.service.impl;

import com.taoly.monitor.entity.MailLog;
import com.taoly.monitor.entity.Mailbox;
import com.taoly.monitor.enums.LogTypeEnums;
import com.taoly.monitor.repository.MailLogRepository;
import com.taoly.monitor.service.MailReceivingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/27 13:29
 * @ Description：
 */
@Service
public class MailReceivingServiceImpl implements MailReceivingService {

    @Autowired
    private MailLogRepository mailLogRepository;

    @Override
    public Message[] mailReceiving(Mailbox mailbox, Integer startNum, Integer endNum) {
        Properties props = new Properties();
        // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.transport.protocol", mailbox.getProtocol());
        // 邮箱相关协议的服务器地址
        props.setProperty("mail.smtp.host", mailbox.getHost());
        // 获取连接
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);
        Message[] messages = null;
        try {
            // 获取Store对象
            Store store = session.getStore(mailbox.getProtocol());
            // POP3服务器的登陆认证
            store.connect(mailbox.getHost(), mailbox.getUsername(), mailbox.getPassword());
            // 通过POP3协议获得Store对象调用这个方法时，邮件夹名称只能指定为"INBOX"
            // 获得用户的邮件帐户
            Folder folder = store.getFolder("INBOX");
            // 设置对邮件帐户的访问权限
            folder.open(Folder.READ_WRITE);
            // 得到邮箱帐户中一定数量的邮件（从start到end）
            messages = folder.getMessages(startNum, endNum);
            // 关闭邮件夹对象
//            folder.close(false);
            // 关闭连接对象
//            store.close();
        } catch (Exception e) {
            mailLogRepository.save(new MailLog(LogTypeEnums.WRONG.getCode(), "【获取邮件错误】"+e.getMessage()));
        }
        return messages;
    }

    @Override
    public Integer mailCount(Mailbox mailbox) {
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", mailbox.getProtocol());
        props.setProperty("mail.smtp.host", mailbox.getHost());
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);
        Integer mailCount = 0;
        try {
            Store store = session.getStore(mailbox.getProtocol());
            store.connect(mailbox.getHost(), mailbox.getUsername(), mailbox.getPassword());
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            mailCount = folder.getMessageCount();
            folder.close(false);
            store.close();
        } catch(Exception e) {
            mailLogRepository.save(new MailLog(LogTypeEnums.WRONG.getCode(), "【计算邮件总数】"+e.getMessage()));
        }
        return mailCount;
    }
}
