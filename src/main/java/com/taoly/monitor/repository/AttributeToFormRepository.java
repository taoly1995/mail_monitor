package com.taoly.monitor.repository;

import com.taoly.monitor.entity.AttributeToForm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/9/10 15:46
 * @ Description：
 */
@Repository
public interface AttributeToFormRepository extends JpaRepository<AttributeToForm, Long> {

    List<AttributeToForm> findByTypeName(String typeName);
}
