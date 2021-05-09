package com.nb.crm.workbench.service;

import com.nb.crm.vo.PaginationVo;
import com.nb.crm.workbench.domain.Activity;
import com.nb.crm.workbench.domain.ActivityRemark;

import java.util.List;
import java.util.Map;

public interface IActivityService {
    boolean save(Activity a);

    PaginationVo<Activity> pageList(Map<String, Object> map);

    boolean delete(String[] ids);

    Map<String, Object> activityUpdate(String id);

    boolean update(Activity a);


    Activity detail(String id);

    List<ActivityRemark> getRemakeListByAid(String activityId);

    Boolean deleteRemark(String id);

    boolean saveRemark(ActivityRemark ar);

    Boolean updateRemark(ActivityRemark ar);
}
