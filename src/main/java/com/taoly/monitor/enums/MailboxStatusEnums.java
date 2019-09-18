package com.taoly.monitor.enums;

import lombok.Getter;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/28 8:32
 * @ Description：
 */
@Getter
public enum MailboxStatusEnums {

    ENABLE(0,"正在使用的邮箱"),
    DISABLE(1,"当前未使用的邮箱")
    ;
    private Integer code;

    private String msg;

    MailboxStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
