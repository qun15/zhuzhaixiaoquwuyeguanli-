package com.nb.crm.settings.service;

import com.nb.crm.exception.LoginException;
import com.nb.crm.settings.domain.Check;
import com.nb.crm.settings.domain.User;
import com.nb.crm.vo.PaginationVo;

import java.util.List;
import java.util.Map;

public interface ICheckService {

    Boolean save(Check check);

    PaginationVo<Check> pageList(Map<String, Object> map);

    Check getCheckListById(String id);

    Boolean update(Check check);

    boolean deleteCheck(String[] ids);
}
