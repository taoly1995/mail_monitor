package com.taoly.monitor.service.impl;

import com.taoly.monitor.VO.MailDetailVO;
import com.taoly.monitor.cache.TypeCache;
import com.taoly.monitor.constant.DetailLabelConstants;
import com.taoly.monitor.entity.*;
import com.taoly.monitor.enums.DetailStyle;
import com.taoly.monitor.repository.AttributeToFormRepository;
import com.taoly.monitor.repository.DetailTypeRepository;
import com.taoly.monitor.repository.MailDetailRepository;
import com.taoly.monitor.repository.MailHeaderRepository;
import com.taoly.monitor.service.*;
import com.taoly.monitor.utils.MailAnalysisUtil;
import com.taoly.monitor.utils.MailDetailBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.mail.Message;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MailLogServiceImplTest {

    private static final Long MAILBOX_ID = new Long(1);

    @Autowired
    private MailLogService mailLogService;
    @Autowired
    private MailboxService mailboxService;
    @Autowired
    private MailReceivingService mailReceivingService;
    @Autowired
    private MailHeaderRepository mailHeaderRepository;
    @Autowired
    private MailHeaderService mailHeaderService;
    @Autowired
    private MailDetailService mailDetailService;
    @Autowired
    private DetailTypeRepository detailTypeRepository;
    @Autowired
    private AttributeToFormRepository attributeToFormRepository;
    @Autowired
    private MailDetailRepository mailDetailRepository;

    @Test
    public void save() throws Exception {
        Mailbox mailbox = mailboxService.findById(new Long(1));
        Message[] messages = mailReceivingService.mailReceiving(mailbox, 1,6);
        MailAnalysisUtil.parseMessage(messages);
    }

    @Test
    public void getLastMailCount() throws Exception {
//        Assert.assertEquals(new Integer(2), mailLogService.getLastMailCount());
        Mailbox mailBox = mailboxService.findById(MAILBOX_ID);
        Integer currentMailCount = mailReceivingService.mailCount(mailBox);
        Integer handleCountThisTime = currentMailCount - mailLogService.getLastMailCount();
        System.out.println(handleCountThisTime);
    }

    @Test
    public void testLog() throws Exception {
        Mailbox mailbox = mailboxService.findById(new Long(1));
        Message[] messages = mailReceivingService.mailReceiving(mailbox,7,7);
        mailDetailService.saveByMessage(messages[0],new MailHeader());
    }

    @Test
    public void testXiabiao() throws Exception {
        Mailbox mailbox = mailboxService.findById(new Long(1));
        Message[] messages = mailReceivingService.mailReceiving(mailbox,6,6);
        MailHeader mailHeader = mailHeaderService.buildMailHeader(messages[0], mailbox);
        if(mailHeaderService.checkMail(mailHeader)) {
            mailHeaderService.save(mailHeader);
        } else {
            System.out.println("不通过");
        }
    }

    @Test
    public void testFanShe() throws Exception {
        MailHeader mailHeader = mailHeaderRepository.findById(new Long(12)).get();
        Mailbox mailbox = mailboxService.findById(new Long(1));
        Message[] messages = mailReceivingService.mailReceiving(mailbox,8,8);
        mailDetailService.saveByMessage(messages[0], mailHeader);
    }

    @Test
    public void testBiaoge() throws Exception {
        String a = "<div style=\"position:relative;padding-right:1px;padding-bottom:18px\"><div style=\"position:absolute;left:19px;top:14px;z-index:2;display:block;width:7px;height:7px;background-color:#e0e0e0;border-radius:50%;margin-left:0px;margin-top:-3px\"></div><div style=\"position:absolute;top:14px;left:19px;z-index:1;height:100%;width:1px;background:#fff\"></div><div style=\"position:relative;z-index:2;height:28px;margin:0 1px 0 31px;padding-left:8px\"><span style=\"display:inline-block;vertical-align:top;line-height:28px;margin-right:10px\">专利信息</span><span style=\"vertical-align: top;margin-top: 4px;display:inline-block;font-size:12px;padding:1px 4px;border-radius:2px;line-height:16px;border:1px solid #34cc33;color:#34cc33\">利好信息</span></div><div><div style=\"position:relative;padding-left:39px;padding-top:1px;padding-bottom:3px\"><div style=\"margin:9px 0 3px\"><table style=\"width:100%;max-width:100%;font-size:14px;text-align:center;color:#333;border-spacing:0;border-collapse:collapse;table-layout:fixed\"><thead style=\"background-color:#f0f7fc\"><tr><th style=\"box-sizing:border-box;padding:8px 0;border:1px solid #e4eef6;font-weight:400;vertical-align:middle;height:40px;width:100px\">申请公布日</th><th style=\"box-sizing:border-box;padding:8px 0;border:1px solid #e4eef6;font-weight:400;vertical-align:middle;height:40px\" width=\"251\">专利名称</th><th style=\"box-sizing:border-box;padding:8px 0;border:1px solid #e4eef6;font-weight:400;vertical-align:middle;height:40px\" width=\"160\">申请号</th><th style=\"box-sizing:border-box;padding:8px 0;border:1px solid #e4eef6;font-weight:400;vertical-align:middle;height:40px\" width=\"130\">申请公布号</th><th style=\"box-sizing:border-box;padding:8px 0;border:1px solid #e4eef6;font-weight:400;vertical-align:middle;height:40px\" width=\"\">专利类型</th></tr></thead><tbody><tr><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff\"><span>2019-08-20</span></td><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff;text-align:left\"><span>一种永磁电机定、转子流转专用厚片吸塑托盘</span></td><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff\"><span>CN201821916103.2</span></td><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff\"><span>CN209275112U</span></td><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff\"><span>-</span></td></tr><tr><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff\"><span>2019-08-20</span></td><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff;text-align:left\"><span>一种电机安装连接块</span></td><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff\"><span>CN201821916098.5</span></td><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff\"><span>CN209274337U</span></td><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff\"><span>-</span></td></tr><tr><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff\"><span>2019-08-20</span></td><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff;text-align:left\"><span>一种激光打标固定工装</span></td><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff\"><span>CN201821916129.7</span></td><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff\"><span>CN209272751U</span></td><td style=\"box-sizing:border-box;font-size:14px;padding:8px;word-break:break-all;border:1px solid #eaf4ff\"><span>-</span></td></tr></tbody></table></div></div></div></div>";
        Document doc = Jsoup.parse(a);
        Elements elements = doc.select("span[style=\"vertical-align: top;margin-top: 4px;display:inline-block;font-size:12px;padding:1px 4px;border-radius:2px;line-height:16px;border:1px solid #34cc33;color:#34cc33\"]");
        if(elements.size()== 0) {
            System.out.println("这不是表格类信息");
        } else {
            System.out.println("这是表格类信息");
        }
    }

    @Test
    public void testXinzeng() throws Exception {
        MailDetail mailDetail = mailDetailRepository.findById(new Long(1246)).get();
        MailDetailVO mailDetailVO = mailDetailService.converter(mailDetail);
        System.out.println(mailDetailVO);
    }

}