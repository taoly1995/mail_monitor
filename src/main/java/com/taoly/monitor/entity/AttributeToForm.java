package com.taoly.monitor.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/9/4 16:43
 * @ Description：表格类型的配置
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AttributeToForm implements Serializable {

    private static final long serialVersionUID = -9129740705806769047L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateTime;

    /** 类型名称*/
    private String typeName;

    /** 表头名称 */
    private String headerName;

    /** 相应表头处在的排序 */
    private Integer orderNum;

    /** 对应相关的get方法 */
    private String getMethod;

    /** 对应相关的set方法 */
    private String setMethod;
}
