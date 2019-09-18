package com.taoly.monitor.controller;

import com.taoly.monitor.VO.MailDetailVO;
import com.taoly.monitor.VO.ResultVO;
import com.taoly.monitor.entity.MailDetail;
import com.taoly.monitor.entity.MailHeader;
import com.taoly.monitor.entity.MailLog;
import com.taoly.monitor.entity.Mailbox;
import com.taoly.monitor.enums.LogTypeEnums;
import com.taoly.monitor.service.*;
import com.taoly.monitor.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Message;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/28 9:47
 * @ Description：提供主要邮件存储服务及一些处理接口
 */
@RestController
@Component
public class MailController {

    // 目前单邮箱
    public static final Long MAILBOX_ID = new Long(1);
    
    public static final Integer NO_NEED_HANDLE_NUMBER = new Integer(0);
    @Autowired
    private MailboxService mailboxService;
    @Autowired
    private MailReceivingService mailReceivingService;
    @Autowired
    private MailLogService mailLogService;
    @Autowired
    private MailHeaderService mailHeaderService;
    @Autowired
    private MailDetailService mailDetailService;

    /**
     * 主服务，定时查询、存储天眼查邮件
     * 每天10点30执行
     * @return
     */
    @GetMapping("/task")
    @Scheduled(cron = "0 30 10 ? * *")
    public ResultVO saveMailScheduled() {
        List<MailHeader> mailHeaderList = new ArrayList<>();
        /** 1.初始化一些Count，判断要查询多少封（本次查询邮件量和上次查询的差值*/
        Mailbox mailBox = mailboxService.findById(MAILBOX_ID);
        // 上次处理时，邮箱中的邮件总数
        Integer lastTimeMailCount = mailLogService.getLastMailCount();
        // 当前邮箱邮件总数
        Integer currentMailCount = mailReceivingService.mailCount(mailBox);
        // 本次操作处理的总数
        Integer handleCountThisTime = null;
        // 如果没有新增邮件，则不需要处理（保存日志）
        if(currentMailCount.equals(lastTimeMailCount)) {
            MailLog mailLog = mailLogService.recordNoHandleThisTime(currentMailCount);
            return ResultVOUtil.success(mailHeaderList);
        }
        /** 2.获取相应数量邮件，从（上次总量+1）封 到 （本次总量）封*/
        Message[] messages = mailReceivingService.mailReceiving(mailBox, lastTimeMailCount + 1, currentMailCount);
        handleCountThisTime = messages.length;
        /** 3.循环邮件，过滤不需要的邮件 且通过邮件头中的内容进行验重*/
        // 注意：数组里下标范围从0开始，而邮箱中范围从1开始
        for(int i = 0; i < handleCountThisTime; i++) {
            // 当前邮件获取邮件头
            MailHeader mailHeader = mailHeaderService.buildMailHeader(messages[i], mailBox);
            // 使用邮件头进行判断,TRUE:保存邮件头，保存邮件具体内容，有效count + 1
            if(mailHeaderService.checkMail(mailHeader)) {
                // 保存邮件头
                MailHeader savedMailHead = mailHeaderService.save(mailHeader);
                /** 存储邮件内容 */
                List<MailDetail> mailDetailList = mailDetailService.saveByMessage(messages[i], savedMailHead);
                savedMailHead.setMailDetailList(mailDetailList);
                mailHeaderList.add(savedMailHead);
            }
        }
        // 成功，当前邮箱总量，这次处理了多少封，多少封天眼查有效邮件
        mailLogService.save(new MailLog(LogTypeEnums.OK.getCode(), currentMailCount, handleCountThisTime, mailHeaderList.size()));
        return ResultVOUtil.success(mailHeaderList);
    }


    @GetMapping("/findAll")
    public ResultVO findAll() {
        List<MailDetail> mailDetails = mailDetailService.findAll();
        List<MailDetailVO> mailDetailVOS = new ArrayList<>();
        for(MailDetail mailDetail: mailDetails) {
            try {
                MailDetailVO mailDetailVO = mailDetailService.converter(mailDetail);
                mailDetailVOS.add(mailDetailVO);
            } catch (Exception e) {
                e.printStackTrace();
                return ResultVOUtil.error(300, e.getMessage());
            }
        }
        return ResultVOUtil.success(mailDetailVOS);
    }

    @GetMapping("/findAllType")
    public ResultVO findAllType() {
        return null;
    }
}
