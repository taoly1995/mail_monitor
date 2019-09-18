package com.taoly.monitor.service;

import com.taoly.monitor.entity.Mailbox;

import java.util.List;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/27 13:43
 * @ Description：邮箱相关
 */
public interface MailboxService {

    Mailbox findById(Long id);

    /** 取所有正在使用的邮箱 */
    List<Mailbox> findEnableMailboxes();

    Mailbox save(Mailbox mailbox);
}
