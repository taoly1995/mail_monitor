package com.taoly.monitor.constant;


/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/27 8:24
 * @ Description：存放解析html用的标签
 */
public class LabelConstants {

    /** 邮件有用的全部地方 */
    public static final String OVER_ALL_LABEL = "div[style=\"border:1px solid #E4EEF6;border-bottom:none;background:#FFFFFF;margin-top:24px;overflow:hidden\"]";

    /** 获取公司和当前公司发生事情的类型 */
    public static final String COMPANY_AND_ITEM_LABEL = "div[style=\"position:relative;background-color:#fff;border-bottom:1px " +
            "solid #f3f3f3;box-shadow:0 6px 10px 0 rgba(0, 0, 0, .02), 0 2px 3px 0 rgba(0, 0, 0, .06)\"]";

    /** 从companyAndItemLabel取公司名称 */
    public static final String COMPANY_NAME_LABEL = "div[style=\"padding-left:8px;font-size:18px;color:#333;line-height:28px;" +
            "font-weight:700;margin-bottom:1px;padding-right:56px\"]";

    /** 从itemsLabel取得所有单条item */
    public static final String ITEM_LABEL = "span[style=\"padding-right:8px\"]";

    /** 邮件中各个公司发生的具体内容 */
    public static final String DATAS = "div[style=\"padding:0 32px 0 16px;border-bottom:1px solid #f3f3f3\"]";

    /** 大类下具体信息 */
    public static final String SUBJECT_LABEL = "div[style=\"position:relative;padding-right:1px;padding-bottom:18px\"]";

    public static final String COUNT_LABEL = "span[style=\"padding:0 4px;color:#ff3b30\"]";

    public static final String LABEL1 = "div[style=\"font-weight:bold;font-size:24px;color:#333333;line-height:36px\"]";

    // 一句话类型使用的
    public static final String LABEL_NOT_FOR_FORM = "div[style=\"position:relative;font-size:14px;line-height:22px\"]";

    /** styles */
    // 提示
    public static final String TIP_INFO = "span[style=\"vertical-align: top;margin-top: 4px;display:inline-block;font-size:12px;padding:1px 4px;border-radius:2px;line-height:16px;border:1px solid #c66ef2;color:#c66ef2\"]";
    // 警示
    public static final String  WARNING_INFO = "span[style=\"vertical-align: top;margin-top: 4px;display:inline-block;font-size:12px;padding:1px 4px;border-radius:2px;line-height:16px;border:1px solid #ff7d18;color:#ff7d18\"]";
    // 利好
    public static final String GOOD_INFO = "span[style=\"vertical-align: top;margin-top: 4px;display:inline-block;font-size:12px;padding:1px 4px;border-radius:2px;line-height:16px;border:1px solid #34cc33;color:#34cc33\"]";
}


