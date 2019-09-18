package com.taoly.monitor.service;

import com.taoly.monitor.entity.Mailbox;

import javax.mail.Message;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/27 13:29
 * @ Description：邮件接收服务
 */
public interface MailReceivingService {

    /** 接收指定邮箱从x到y封的邮件 */
    Message[] mailReceiving(Mailbox mailbox, Integer startNum, Integer endNum);

    /** 获取当前邮箱中，邮件总数量 */
    Integer mailCount(Mailbox mailbox);
}
