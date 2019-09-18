package com.taoly.monitor.service.impl;

import com.taoly.monitor.constant.MessageConstants;
import com.taoly.monitor.entity.MailHeader;
import com.taoly.monitor.entity.MailLog;
import com.taoly.monitor.entity.Mailbox;
import com.taoly.monitor.enums.LogTypeEnums;
import com.taoly.monitor.repository.MailHeaderRepository;
import com.taoly.monitor.repository.MailLogRepository;
import com.taoly.monitor.service.MailHeaderService;
import com.taoly.monitor.utils.MailAnalysisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/29 11:31
 * @ Description：
 */
@Service
public class MailHeaderServiceImpl implements MailHeaderService {

    @Autowired
    private MailHeaderRepository mailHeaderRepository;
    @Autowired
    private MailLogRepository mailLogRepository;

    @Override
    public MailHeader buildMailHeader(Message message, Mailbox mailbox) {
        MimeMessage mailData = (MimeMessage) message;
        MailHeader mailHeader = new MailHeader();
        try {
            mailHeader.setSubject(MailAnalysisUtil.getSubject(mailData));
            mailHeader.setSender(MailAnalysisUtil.getFrom(mailData));
            mailHeader.setSentDate(MailAnalysisUtil.getSentDate(mailData,null));
            mailHeader.setSize(message.getSize()* 1024);
            mailHeader.setIsContainerAttachment(MailAnalysisUtil.isContainAttachment(mailData));
            mailHeader.setMessageNum(message.getMessageNumber());
            mailHeader.setMailbox(mailbox);
        } catch(Exception e) {
            mailLogRepository.save(new MailLog(LogTypeEnums.WRONG.getCode(), "【邮件头解析】"+e.getMessage()));
        }
        return mailHeader;
    }

    @Override
    public Boolean checkMail(MailHeader mailHeader) {
        // 检查邮件名
        Boolean checkMailSubject = mailHeader.getSubject().contains(MessageConstants.MAIL_SUBJECT);
        // 检查发送方
        Boolean checkMailSender = mailHeader.getSender().contains(MessageConstants.SENDER);
        // 检查该邮件是否已经存在
        Boolean checkExist = Boolean.TRUE;
        if(mailHeaderRepository.findBySubjectAndAndSentDateAndSize(mailHeader.getSubject(), mailHeader.getSentDate(), mailHeader.getSize()) != null) {
            checkExist = Boolean.FALSE;
        }
        // 三个判断都通过才可以保存邮件内容
        return checkMailSubject && checkExist && checkMailSender;
    }

    @Override
    public MailHeader save(MailHeader mailHeader) {
        return mailHeaderRepository.save(mailHeader);
    }

    @Override
    public List<MailHeader> findAll() {
        return mailHeaderRepository.findAll();
    }
}
