package com.taoly.monitor.service.impl;

import com.taoly.monitor.constant.MessageConstants;
import com.taoly.monitor.entity.MailLog;
import com.taoly.monitor.enums.LogTypeEnums;
import com.taoly.monitor.repository.MailLogRepository;
import com.taoly.monitor.service.MailLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.taoly.monitor.controller.MailController.NO_NEED_HANDLE_NUMBER;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/28 12:59
 * @ Description：
 */
@Service
public class MailLogServiceImpl implements MailLogService {

    @Autowired
    private MailLogRepository mailLogRepository;

    @Override
    public MailLog save(MailLog mailLog) {
        return mailLogRepository.save(mailLog);
    }

    @Override
    public Integer getLastMailCount() {
        return mailLogRepository.getLastMailCount();
    }

    @Override
    public MailLog recordNoHandleThisTime(Integer currentMailCount) {
        MailLog mailLog = mailLogRepository.save(new MailLog(LogTypeEnums.OK.getCode(), currentMailCount, NO_NEED_HANDLE_NUMBER,
                NO_NEED_HANDLE_NUMBER, MessageConstants.NO_NEED_HANDLE_THIS_TIME));
        return mailLog;
    }
}
