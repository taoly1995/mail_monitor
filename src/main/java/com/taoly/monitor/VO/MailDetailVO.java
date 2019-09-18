package com.taoly.monitor.VO;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/9/12 11:58
 * @ Description：子表具体内容，返回相应VO用
 */
@Data
public class MailDetailVO implements Serializable {

    private static final long serialVersionUID = 2234266998290072521L;

    private Long id;

    private Date createdTime;

    private Date updateTime;

    private String company;

    private String style;

    private String detailType;

    private String detail;

    /** 表格具体展示时，使用对应关系*/
    private Map<String, String> formMap;
}
