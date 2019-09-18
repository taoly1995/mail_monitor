package com.taoly.monitor.entity;

import com.taoly.monitor.constant.MessageConstants;
import com.taoly.monitor.enums.LogTypeEnums;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static com.taoly.monitor.controller.MailController.NO_NEED_HANDLE_NUMBER;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/28 10:22
 * @ Description：日志表
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MailLog implements Serializable {

    private static final long serialVersionUID = -1069740483908139071L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updateTime;
    /** 日志类型 */
    private Integer logType;
    /** 邮箱中的总邮件数 */
    private Integer allMailNum;
    /** 今天一共处理了多少封 */
    private Integer dailyAllMailNum;
    /** 今天有多少封有效 */
    private Integer dailyEffectiveMailNum;

    private String msg;

    public MailLog() {
    }

    public MailLog(Integer logType, Integer allMailNum, Integer dailyAllMailNum, Integer dailyEffectiveMailNum) {
        this.logType = logType;
        this.allMailNum = allMailNum;
        this.dailyAllMailNum = dailyAllMailNum;
        this.dailyEffectiveMailNum = dailyEffectiveMailNum;
    }

    public MailLog(Integer logType, Integer allMailNum, Integer dailyAllMailNum, Integer dailyEffectiveMailNum, String msg) {
        this.logType = logType;
        this.allMailNum = allMailNum;
        this.dailyAllMailNum = dailyAllMailNum;
        this.dailyEffectiveMailNum = dailyEffectiveMailNum;
        this.msg = msg;
    }

    public MailLog(Integer logType, Integer allMailNum, String msg) {
        this.logType = logType;
        this.allMailNum = allMailNum;
        this.msg = msg;
    }

    public MailLog(Integer logType, String msg) {
        this.logType = logType;
        this.msg = msg;
    }

}
