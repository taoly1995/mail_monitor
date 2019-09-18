package com.taoly.monitor.service.impl;

import com.taoly.monitor.VO.MailDetailVO;
import com.taoly.monitor.constant.DetailLabelConstants;
import com.taoly.monitor.constant.LabelConstants;
import com.taoly.monitor.entity.*;
import com.taoly.monitor.enums.DetailStyle;
import com.taoly.monitor.enums.LogTypeEnums;
import com.taoly.monitor.repository.AttributeToFormRepository;
import com.taoly.monitor.repository.DetailTypeRepository;
import com.taoly.monitor.repository.MailDetailRepository;
import com.taoly.monitor.repository.MailLogRepository;
import com.taoly.monitor.service.MailDetailService;
import com.taoly.monitor.utils.MailAnalysisUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/8/29 14:19
 * @ Description：
 */
@Service
public class MailDetailServiceImpl implements MailDetailService {

    @Autowired
    private MailLogRepository mailLogRepository;
    @Autowired
    private MailDetailRepository mailDetailRepository;
    @Autowired
    private DetailTypeRepository detailTypeRepository;
    @Autowired
    private AttributeToFormRepository attributeToFormRepository;

    @Override
    public List<MailDetail> saveByMessage(Message message, MailHeader savedMailHead) {
        List<MailDetail> mailDetailList = new ArrayList<>();
        StringBuffer content = new StringBuffer(30);
        try {
            MailAnalysisUtil.getMailTextContent(message, content);
        } catch (Exception e) {
            mailLogRepository.save(new MailLog(LogTypeEnums.WRONG.getCode(), "【邮件内容解析#1】"+e.getMessage()));
        }
        Document doc = Jsoup.parse(content.toString());
        // 获取到想要的片段，在overAllLabel下存放了公司及提示信息
        Elements elements = doc.select(LabelConstants.OVER_ALL_LABEL);
        // 得到所有公司及其下属的类型标签
        Elements companiesAndItems = elements.select(LabelConstants.COMPANY_AND_ITEM_LABEL);
        // 得到和标签平级的，所有公司发生的具体内容
        Elements datas =elements.select(LabelConstants.DATAS);
        Integer companyCount = companiesAndItems.size();
        // 循环公司
        for(int i = 0; i < companyCount; i++) {
            // 当前公司的名称，标签
            Element thisComapnyItems = companiesAndItems.get(i);
            // 当前公司发生的具体内容
            Element thisCompanyDatas = datas.get(i);
            Elements items = thisComapnyItems.select(LabelConstants.ITEM_LABEL);
            // 公司名称
            String companyName = thisComapnyItems.select(LabelConstants.COMPANY_NAME_LABEL).text();
            // 循环公司下面的大类：这里我们不要第[0]位的item，初始化从x=1开始，因为[1]必存在，所有不用担心空指针
            for(int x = 1; x < items.size(); x++) {
                // 这里能得到，例如：招投标（2）的信息,下一步要取到（String）招投标、（Integer）2
                String item = items.get(x).text();
                String detailType = item.substring(0, item.indexOf("（"));
                Integer nums = new Integer(item.substring(item.indexOf("（") + 1, item.indexOf("）")));
                // 循环处理各分类中的单条
                for(int y = 0; y < nums; y++) {
                    MailDetail mailDetail = getMailDetail(detailType, x - 1, y , thisCompanyDatas);
                    // 类型没有加入，可能为空
                    if(mailDetail != null) {
                        // MailDetail加入savedMailHead，companyName
                        mailDetail.setMailHeader(savedMailHead);
                        mailDetail.setCompany(companyName);
                        MailDetail savedMailDetail = mailDetailRepository.save(mailDetail);
                        mailDetailList.add(savedMailDetail);
                    }
                }
            }
        }
        return mailDetailList;
    }



    /**
     * 1.注意这里的两个order，第一个order序号，确定这个当前公司下面第几个方面的标签（比如是招投标大类，还是专利信息大类）
     *                     第二个order序号，确定在这个大类下，他是第几个
     *                      这样才能定位到具体的内容
     * 2.这里用了反射，所以要求所有工具类都必须参数一样
     * @param detailType
     * @param order
     * @param orderItem
     * @param thisCompanyDatas
     * @return
     */
    public synchronized MailDetail getMailDetail(String detailType, Integer order, Integer orderItem, Element thisCompanyDatas) {
        DetailType type = detailTypeRepository.findByDetailType(detailType);
        MailDetail mailDetail = new MailDetail();
        if (type == null) {
            // 1. 找出这个type是否是表格类的，如果不是，detail_type表加入一行即可，如果是，detail_type表和attribute_to_form表都要加
            // 取相应大类的下面，tr的数量，为0就不是表格类
            Integer isForm = thisCompanyDatas.select(LabelConstants.SUBJECT_LABEL).get(order)
                    .select(DetailLabelConstants.TR).size();
            DetailType addDetailType = new DetailType();
            if(isForm == 0) {
                addDetailType.setDetailType(detailType);
                addDetailType.setLabel(LabelConstants.LABEL_NOT_FOR_FORM);
                addDetailType.setMethodName("getDetail");
                addDetailType.setStyle(getStyle(thisCompanyDatas.select(LabelConstants.SUBJECT_LABEL).get(order)));
            } else {
                // 加入表格类的detailtype
                addDetailType.setDetailType(detailType);
                addDetailType.setLabel("form");
                addDetailType.setMethodName("getFormData");
                addDetailType.setStyle(getStyle(thisCompanyDatas.select(LabelConstants.SUBJECT_LABEL).get(order)));
                // 循环存新加入attribute to form
                Elements heads = thisCompanyDatas.select(LabelConstants.SUBJECT_LABEL).get(order)
                        .select(DetailLabelConstants.TR).get(0).children();
                for(int headOrder = 0; headOrder < heads.size(); headOrder++) {
                    AttributeToForm attributeToForm = new AttributeToForm();
                    attributeToForm.setHeaderName(heads.get(headOrder).text());
                    attributeToForm.setOrderNum(headOrder + 1);
                    attributeToForm.setTypeName(detailType);
                    attributeToForm.setGetMethod("getHeader" + (headOrder + 1));
                    attributeToForm.setSetMethod("setHeader" + (headOrder + 1));
                    attributeToFormRepository.save(attributeToForm);
                }
            }
            // 保存新加入的detailType，并赋给type
            type = detailTypeRepository.save(addDetailType);
        }
        try {
            MailDetailServiceImpl mailDetailServiceImpl = new MailDetailServiceImpl();
            // 反射
            String methodName = type.getMethodName();
            Method method = mailDetailServiceImpl.getClass().getMethod(methodName,
                    Class.forName("java.lang.Integer"), Class.forName("java.lang.Integer"),
                    Class.forName("org.jsoup.nodes.Element"),Class.forName("com.taoly.monitor.entity.DetailType"));
            mailDetail = (MailDetail) method.invoke(mailDetailServiceImpl, order, orderItem, thisCompanyDatas, type);
        } catch (Exception e) {
            mailLogRepository.save(new MailLog(LogTypeEnums.WRONG.getCode(), "【反射解析错误】"+e.getMessage()));
        }

        return mailDetail;
    }

    /** 单行数据类型，只获取detail：动产抵押、招投标、股权变更 */
    public MailDetail getDetail(Integer order, Integer orderItem, Element thisCompanyDatas, DetailType type) throws Exception {
        MailDetail mailDetail = getBasicDetail(type);
        // 获取单条数据（第order个大类的，第orderItem行）
        String detail = thisCompanyDatas.select(LabelConstants.SUBJECT_LABEL).get(order)
                .select(type.getLabel()).get(orderItem).text();
        mailDetail.setDetail(detail);
        return mailDetail;
    }

    /** 表格类：专利信息,经营范围变更,专利信息 */
    public MailDetail getFormData(Integer order, Integer orderItem, Element thisCompanyDatas, DetailType type) throws Exception {
        MailDetail mailDetail = getBasicDetail(type);
        // 获取表头信息,第1个tr里面的children里面有相关信息
        Elements heads = thisCompanyDatas.select(LabelConstants.SUBJECT_LABEL).get(order)
                .select(DetailLabelConstants.TR).get(0).children();
        // 这里取表体信息,第2个tr里的children
        Elements datas = thisCompanyDatas.select(LabelConstants.SUBJECT_LABEL).get(order)
                .select(DetailLabelConstants.TBODY).select(DetailLabelConstants.TR).get(orderItem)
                .children();
        // 塞相应的数据到表中的具体某列
        for(int headOrder = 0; headOrder < heads.size(); headOrder++) {
            // 反射的方法名
            String methodName = "setHeader"+(headOrder + 1);
            Method methodForSet = mailDetail.getClass().getMethod(methodName, Class.forName("java.lang.String"));
            methodForSet.invoke(mailDetail, datas.get(headOrder).text());

        }
        return mailDetail;
    }

    /** 所有工具方法都用的一个方法，通过DetailTypeEnums获得一个基础MailDetail */
    public MailDetail getBasicDetail(DetailType type) {
        MailDetail mailDetail = new MailDetail();
        mailDetail.setDetailType(type.getDetailType());
        mailDetail.setStyle(type.getStyle().getStyle());
        return mailDetail;
    }

    /** 获取当前detailType是利好，警示还是提示 */
    DetailStyle getStyle(Element element) {
        Integer isTip = element.select(LabelConstants.TIP_INFO).size();
        if(isTip != 0) {
            return DetailStyle.TIP_INFO;
        }
        Integer isGoodInfo = element.select(LabelConstants.GOOD_INFO).size();
        if(isGoodInfo != 0) {
            return DetailStyle.GOOD_INFO;
        }
        Integer isWarnInfo = element.select(LabelConstants.WARNING_INFO).size();
        if(isWarnInfo != 0) {
            return DetailStyle.WARNING_INFO;
        }
        mailLogRepository.save(new MailLog(LogTypeEnums.WRONG.getCode(), "【Style解析错误】新增的DetailType的style可能需要手动调整"));
        return DetailStyle.TIP_INFO;
    }



    @Override
    public List<MailDetail> findByMailHeader(MailHeader mailHeader) {
        return mailDetailRepository.findByMailHeader(mailHeader);
    }

    @Override
    public List<MailDetail> findAll() {
        return mailDetailRepository.findAll();
    }

    @Override
    public MailDetailVO converter(MailDetail mailDetail) throws Exception {
        MailDetailVO mailDetailVO = new MailDetailVO();
        BeanUtils.copyProperties(mailDetail, mailDetailVO);
        // 处理表格类的：
        if(mailDetail.getDetail() == null) {
            List<AttributeToForm> list = attributeToFormRepository.findByTypeName(mailDetail.getDetailType());
            Map<String, String> formMap = new HashMap<>();
            for(AttributeToForm attributeToForm: list) {
                String methodName = attributeToForm.getGetMethod();
                Method method = mailDetail.getClass().getMethod(methodName);
                String data = (String) method.invoke(mailDetail);
                formMap.put(attributeToForm.getHeaderName(), data);
            }
            mailDetailVO.setFormMap(formMap);
        }
        return mailDetailVO;
    }
}

