package com.nb.crm.settings.service;

import com.nb.crm.settings.domain.Park;
import com.nb.crm.vo.PaginationVo;

import java.util.List;
import java.util.Map;

public interface IParkService {
    PaginationVo<Park> pageList(Map<String, Object> map);

    Park getParkListById(String id);

    Boolean update(Park park);

    boolean deletePark(String[] ids);

    Boolean save(Park park);

    List<Park> getUserList();
}
