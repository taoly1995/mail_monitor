package com.taoly.monitor.repository;

import com.taoly.monitor.entity.DetailType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/9/2 9:00
 * @ Description：
 */
@Repository
public interface DetailTypeRepository extends JpaRepository<DetailType, Long> {

    /**
     * 根据type的名称取
     * @param detailType
     * @return
     */
    DetailType findByDetailType(String detailType);
}
