package com.taoly.monitor.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/28 9:15
 * @ Description：存放邮件的基本信息（邮件主表）
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class MailHeader implements Serializable {

    private static final long serialVersionUID = -6210220563118117640L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateTime;

    /** 邮件主题 */
    private String subject;

    /** 发件人 */
    private String sender;

    /** 发送时间 */
    private String sentDate;

    /** 邮件大小 */
    private Integer size;

    /** 是否包含附件 */
    private Boolean isContainerAttachment;

    /** 当前邮件是邮箱中的第多少封 */
    private Integer messageNum;

    /** 来自哪个邮箱，json中忽略，包含邮箱授权码等敏感信息 */
    @ManyToOne
    @JsonIgnore
    private Mailbox mailbox;

    @Transient
    private List<MailDetail> mailDetailList;
}
