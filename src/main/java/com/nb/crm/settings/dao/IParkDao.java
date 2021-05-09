package com.nb.crm.settings.dao;

import com.nb.crm.settings.domain.Park;

import java.util.List;
import java.util.Map;

public interface IParkDao {

    int getTotalByCondition(Map<String, Object> map);

    List<Park> getUserListByCondition(Map<String, Object> map);

    Park getParkListById(String id);

    int update(Park park);

    int deletePark(String[] ids);

    int save(Park park);

    List<Park> getUserList();
}
