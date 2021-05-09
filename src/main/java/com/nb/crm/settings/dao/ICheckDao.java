package com.nb.crm.settings.dao;

import com.nb.crm.settings.domain.Check;

import java.util.List;
import java.util.Map;

public interface ICheckDao {
    int save(Check check);

    int getTotalByCondition(Map<String, Object> map);

    List<Check> getCheckListByCondition(Map<String, Object> map);

    Check getCheckListById(String id);

    int update(Check check);

    int deleteCheck(String[] ids);
}
