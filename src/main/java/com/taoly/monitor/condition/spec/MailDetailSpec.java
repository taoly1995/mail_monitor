package com.taoly.monitor.condition.spec;

import com.taoly.monitor.condition.MailDetailCondition;
import com.taoly.monitor.entity.MailDetail;
import com.taoly.monitor.repository.support.ImoocSpecification;
import com.taoly.monitor.repository.support.QueryWraper;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/9/16 15:20
 * @ Description：
 */
public class MailDetailSpec extends ImoocSpecification<MailDetail, MailDetailCondition>{

    public MailDetailSpec(MailDetailCondition condition) {
        super(condition);
    }

    @Override
    protected void addCondition(QueryWraper<MailDetail> queryWraper) {
        addEqualsCondition(queryWraper, "mailHeader");
        addLikeCondition(queryWraper, "company");
        addEqualsCondition(queryWraper, "detailType");
        addEqualsCondition(queryWraper, "style");
    }
}
