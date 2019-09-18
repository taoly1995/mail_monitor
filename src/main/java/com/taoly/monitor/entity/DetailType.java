package com.taoly.monitor.entity;

import com.taoly.monitor.enums.DetailStyle;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/9/2 8:30
 * @ Description：邮件具体信息的类型
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class DetailType implements Serializable{

    private static final long serialVersionUID = 420444672873082508L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 类型 */
    private String detailType;

    /** 利好，警示，提示 */
    private DetailStyle style;

    /** 对应的label */
    private String label;

    /** 对应的方法名 */
    private String methodName;
}
