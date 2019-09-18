package com.taoly.monitor.controller;

import com.taoly.monitor.VO.ResultVO;
import com.taoly.monitor.entity.MailHeader;
import com.taoly.monitor.entity.MailLog;
import com.taoly.monitor.entity.Mailbox;
import com.taoly.monitor.repository.MailLogRepository;
import com.taoly.monitor.service.MailLogService;
import com.taoly.monitor.service.MailboxService;
import com.taoly.monitor.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/27 11:23
 * @ Description：
 */
@RestController
//@Component
public class TestController {


    @Autowired
    private MailboxService mailboxService;
    @Autowired
    private MailLogService mailLogService;

    @GetMapping("/test")
    public Mailbox test() {
        return mailboxService.findById(new Long(1));
    }

    @GetMapping("/test1")
    public List<Mailbox> test1() {
        mailLogService.save(new MailLog());
        return mailboxService.findEnableMailboxes();
    }

    @GetMapping("/test3")
//    @Scheduled(cron = "0/15 * * * * ?")
    public ResultVO test2() {
        Map<String, Object> map = new HashMap<>();
        map.put("测试属性1", mailboxService.findById(new Long(1)));
        MailHeader header = new MailHeader();
        header.setSender("taolingyang");
        header.setSubject("kaihui");
        header.setSentDate("2019年9月4日16:19:33");
        map.put("测试属性2", header);
        map.put("测试属性3", "XXXXXXX");
        map.put("测试属性4", "SSSSSS");
        return ResultVOUtil.success(map);
    }
 }
