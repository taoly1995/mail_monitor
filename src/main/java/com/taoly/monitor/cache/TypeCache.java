package com.taoly.monitor.cache;

import com.taoly.monitor.entity.DetailType;
import com.taoly.monitor.service.DetailTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/9/2 9:17
 * @ Description：把类型,表头对应方法等内容放在cache里
 */
@Component
public class TypeCache {

    // 存放type，对应的service中方法
    public static Map<String, DetailType> detailMap = new HashMap<String, DetailType>();

    // 存放表头类型，对应的set方法
    public static Map<String, String> headMap = new HashMap<String, String>();

    @Autowired
    private DetailTypeService detailTypeService;

    @PostConstruct
    public void init() {
        System.out.println("run...");
        /** 暂时不用在cache里存配置(新增的进不来) */
//        List<DetailType> detailTypes = detailTypeService.findAll();
//        for(DetailType detailType: detailTypes) {
//            detailMap.put(detailType.getDetailType(), detailType);
//        }
    }

    @PreDestroy
    public void destroy() {
        System.out.println("系统运行结束");
    }
}
