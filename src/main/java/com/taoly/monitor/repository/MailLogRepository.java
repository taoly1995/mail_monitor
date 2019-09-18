package com.taoly.monitor.repository;

import com.taoly.monitor.entity.MailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/28 11:09
 * @ Description：
 */
@Repository
public interface MailLogRepository extends JpaRepository<MailLog, Long> {

    /**
     * 获取上一次log记录中的邮件总数量
     * @return
     */
    @Query(value = "select all_mail_num from mail_log where log_type=0 order by created_time desc limit 1", nativeQuery = true)
    Integer getLastMailCount();
}
