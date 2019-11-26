package com.ayf.springboot_datasources_switch.mapper;

import com.ayf.springboot_datasources_switch.entity.CkRollback;

import java.util.List;

public interface CkRollbackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CkRollback record);

    int insertSelective(CkRollback record);

    CkRollback selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CkRollback record);

    int updateByPrimaryKeyWithBLOBs(CkRollback record);

    int updateByPrimaryKey(CkRollback record);

    List<CkRollback> selectAll();
}