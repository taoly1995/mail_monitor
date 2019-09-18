package com.taoly.monitor.enums;

import com.taoly.monitor.constant.MessageConstants;
import lombok.Getter;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/9/2 8:38
 * @ Description：
 */
@Getter
public enum  DetailStyle {

    GOOD_INFO(MessageConstants.GOOD_INFO),
    WARNING_INFO(MessageConstants.WARNING_INFO),
    TIP_INFO(MessageConstants.TIP_INFO)
    ;

    private String style;

    DetailStyle(String style) {
        this.style = style;
    }
}
