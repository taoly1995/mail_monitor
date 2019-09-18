package com.taoly.monitor.service.impl;

import com.taoly.monitor.entity.Mailbox;
import com.taoly.monitor.enums.MailboxStatusEnums;
import com.taoly.monitor.repository.MailboxRepository;
import com.taoly.monitor.service.MailboxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/27 13:44
 * @ Description：
 */
@Service
public class MailboxServiceImpl implements MailboxService {

    @Autowired
    private MailboxRepository mailboxRepository;

    @Override
    public Mailbox findById(Long id) {
        return mailboxRepository.findById(id).get();
    }

    @Override
    public List<Mailbox> findEnableMailboxes() {
        return mailboxRepository.findByStatus(MailboxStatusEnums.ENABLE.getCode());
    }

    @Override
    public Mailbox save(Mailbox mailbox) {
        return mailboxRepository.save(mailbox);
    }
}
