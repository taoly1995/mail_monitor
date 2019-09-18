package com.taoly.monitor.enums;

import lombok.Getter;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/30 12:13
 * @ Description：
 */
@Getter
public enum LogTypeEnums {
    OK(0, "正常"),
    WRONG(1, "发生异常"),
    MANUAL(2, "手动干预")

    ;
    private Integer code;

    private String msg;

    LogTypeEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
