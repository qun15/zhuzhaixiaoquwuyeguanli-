package com.nb.crm.settings.service;

import com.nb.crm.exception.LoginException;
import com.nb.crm.settings.domain.User;
import com.nb.crm.vo.PaginationVo;

import java.util.List;
import java.util.Map;

public interface IUserService {
    User login(String loginAct, String loginPwd) throws LoginException;

    List<User> getUserList();

    Boolean save(User user);

    PaginationVo<User> pageList(Map<String,Object> map);

    boolean deleteUser(String[] ids);

    User getUserListById(String id);

    Boolean update(User user);

    User detail(String id);

    Boolean updatePwd(Map<String, Object> map);
}
