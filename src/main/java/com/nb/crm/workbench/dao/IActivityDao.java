package com.nb.crm.workbench.dao;

import com.nb.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface IActivityDao {
    int save(Activity a);

    List<Activity> getActivityListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int delete(String[] ids);

    Activity getByid(String id);

    int update(Activity a);

    Activity detail(String id);
}
