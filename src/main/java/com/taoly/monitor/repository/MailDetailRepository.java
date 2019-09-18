package com.taoly.monitor.repository;

import com.taoly.monitor.entity.MailDetail;
import com.taoly.monitor.entity.MailHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/29 14:25
 * @ Description：
 */
@Repository
public interface MailDetailRepository extends JpaRepository<MailDetail, Long> {

    List<MailDetail> findByMailHeader(MailHeader mailHeader);
}
