package com.taoly.monitor.repository;

import com.taoly.monitor.entity.MailHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/28 9:26
 * @ Description：
 */
@Repository
public interface MailHeaderRepository extends JpaRepository<MailHeader, Long> {

    // 通过主题，发送日期，邮件大小来查找邮件
    MailHeader findBySubjectAndAndSentDateAndSize(String subject, String sentDate, Integer size);
}
