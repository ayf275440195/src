package com.ayf.springboot_datasources_switch.service;

import com.ayf.springboot_datasources_switch.config.DataSourceContextHolder;
import com.ayf.springboot_datasources_switch.mapper.CkRollbackMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private CkRollbackMapper ckRollbackMapper;

    public Object getRollBack(){
        DataSourceContextHolder.setDataSourceKey("bdatasource");
        return ckRollbackMapper.selectAll();
    }




}
