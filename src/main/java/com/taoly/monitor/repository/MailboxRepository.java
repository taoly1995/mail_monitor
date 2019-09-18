package com.taoly.monitor.repository;

import com.taoly.monitor.entity.Mailbox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/27 13:42
 * @ Description：
 */
@Repository
public interface MailboxRepository extends JpaRepository<Mailbox, Long>{

    List<Mailbox> findByStatus(Integer status);
}
