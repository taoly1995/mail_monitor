package com.taoly.monitor.service.impl;

import com.taoly.monitor.entity.DetailType;
import com.taoly.monitor.repository.DetailTypeRepository;
import com.taoly.monitor.service.DetailTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ Author     ：taoly.
 * @ Date       ：Created in 2019/9/2 9:19
 * @ Description：
 */
@Service
public class DetailTypeServiceImpl implements DetailTypeService {

    @Autowired
    private DetailTypeRepository detailTypeRepository;

    @Override
    public List<DetailType> findAll() {
        return detailTypeRepository.findAll();
    }
}
