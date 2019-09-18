package com.taoly.monitor.service;

import com.taoly.monitor.entity.MailLog;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/28 12:59
 * @ Description：
 */
public interface MailLogService {

    MailLog save(MailLog mailLog);

    /**
     * 获取上一次log记录中的邮件总数量
     * @return
     */
    Integer getLastMailCount();

    /**
     * 记录log：邮箱没有新增邮件，无任何操作
     * @param currentMailCount
     * @return
     */
    MailLog recordNoHandleThisTime(Integer currentMailCount);
}
