package com.taoly.monitor.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/27 13:32
 * @ Description：邮箱参数
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Mailbox implements Serializable {

    private static final long serialVersionUID = -4162785755430737908L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 协议对应服务器地址*/
    private String host;

    /** 协议类型*/
    private String protocol;

    /** 邮箱*/
    private String username;

    /** 授权码*/
    private String password;

    private String remark;

    /** 0 正常，1 停用*/
    private Integer status;
}
