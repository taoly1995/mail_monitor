package com.taoly.monitor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/29 13:49
 * @ Description：
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class MailDetail implements Serializable {

    private static final long serialVersionUID = -4723387088879694567L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateTime;

    /** 记录邮件头 ,json中忽略，不然会循环嵌套*/
    @ManyToOne
    @JsonIgnore
    private MailHeader mailHeader;

    /** 公司名称 */
    private String company;
    /** 利好，提示，警示 */
    private String style;
    /** 具体内容是哪方面的 */
    private String detailType;

    /** 内容（一句话类型） */
    private String detail;

    /** 以下为表格类存储需要的属性 */

    private String header1;

    private String header2;

    private String header3;

    private String header4;

    private String header5;

    private String header6;

    private String header7;

    private String header8;

    private String header9;

    private String header10;

    private String header11;

    private String header12;
}
