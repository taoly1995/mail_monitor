package com.taoly.monitor.enums;

import com.taoly.monitor.constant.DetailLabelConstants;
import com.taoly.monitor.constant.MessageConstants;
import lombok.Getter;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/30 12:07
 * @ Description：
 */
@Getter
public enum DetailTypeEnums {

    CHATTEL_MORTGAGE("动产抵押", MessageConstants.WARNING_INFO, DetailLabelConstants.CHATTEL_MORTGAGE, "chattelMortgage" ),
    PATENT_INFO("专利信息", MessageConstants.GOOD_INFO, "表格类", "patentInfo"),
    LEGAL_ACTION("法律诉讼", MessageConstants.WARNING_INFO, "表格类", "legalAction"),
    BIDDING("招投标", MessageConstants.TIP_INFO, DetailLabelConstants.CHATTEL_MORTGAGE, "bidding"),
//    EQUITY_CHANGE("股权变更", MessageConstants.TIP_INFO, DetailLabelConstants.EQUITY_CHANGE, "equityChange"),
    LEGAL_ASSISTANCE("司法协助", MessageConstants.WARNING_INFO, "", ""),
    BUSINESS_SCOPE_CHANGE("经营范围变更", MessageConstants.TIP_INFO, "", ""),
    REGISTRATION_AUTHORITY_CHANGE("登记机关变更", MessageConstants.TIP_INFO, "", ""),
    ;

    private String detailType;

    private String style;

    private String label;

    private String methodName;

    DetailTypeEnums(String detailType, String style, String label, String methodName) {
        this.detailType = detailType;
        this.style = style;
        this.label = label;
        this.methodName = methodName;
    }

}
