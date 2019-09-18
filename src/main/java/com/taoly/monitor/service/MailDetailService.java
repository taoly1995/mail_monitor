package com.taoly.monitor.service;

import com.taoly.monitor.VO.MailDetailVO;
import com.taoly.monitor.entity.MailDetail;
import com.taoly.monitor.entity.MailHeader;

import javax.mail.Message;
import java.util.List;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/29 14:19
 * @ Description：
 */
public interface MailDetailService {

    List<MailDetail> saveByMessage(Message message, MailHeader savedMailHead);

    List<MailDetail> findByMailHeader(MailHeader mailHeader);

    List<MailDetail> findAll();

    /**
     * 将MailDetail转换成前端使用的MailDetailVO
     * @param mailDetail
     * @return
     */
    MailDetailVO converter(MailDetail mailDetail) throws Exception;

}
