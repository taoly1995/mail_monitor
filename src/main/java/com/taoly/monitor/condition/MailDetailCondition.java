package com.taoly.monitor.condition;

import com.taoly.monitor.entity.MailHeader;
import lombok.Data;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/9/16 11:18
 * @ Description：
 */
@Data
public class MailDetailCondition {

    private MailHeader mailHeader;

    private String company;

    private String detailType;

    private String style;

}
