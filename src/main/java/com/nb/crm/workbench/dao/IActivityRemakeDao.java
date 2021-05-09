package com.nb.crm.workbench.dao;

import com.nb.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface IActivityRemakeDao {
    int getCountByAids(String[] ids);

    int deleteByAids(String[] ids);

    List<ActivityRemark> getRemakeListByAid(String activityId);

    int deleteById(String id);

    int saveRemark(ActivityRemark ar);

    int updateRemark(ActivityRemark ar);
}
