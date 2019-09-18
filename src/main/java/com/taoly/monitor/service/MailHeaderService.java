package com.taoly.monitor.service;

import com.taoly.monitor.entity.MailHeader;
import com.taoly.monitor.entity.Mailbox;

import javax.mail.Message;
import java.util.List;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/29 11:28
 * @ Description：
 */
public interface MailHeaderService {

    /**
     * 邮件头的组装
     * @param message
     * @param mailbox
     * @return
     */
    MailHeader buildMailHeader(Message message, Mailbox mailbox);

    /**
     * 检查当前邮件是否来自天眼查，以及验证当前邮件是否已经存在（通过邮件名，发送时间，邮件大小来判断）
     * @param mailHeader
     * @return  Boolean.TRUE：验证通过，邮件内容进行下一步存储操作
     *          Boolean.FALSE:未通过，不储存
     */
    Boolean checkMail(MailHeader mailHeader);

    MailHeader save(MailHeader mailHeader);

    List<MailHeader> findAll();
}
