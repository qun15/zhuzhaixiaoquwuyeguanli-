package com.nb.crm.settings.dao;


import com.nb.crm.settings.domain.User;

import java.util.List;
import java.util.Map;

public interface IUserDao {

    User login(Map<String,Object> map);

    List<User> getUserList();

    int save(User user);

    List<User> getUserListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int deleteUser(String[] ids);

    User getUserById(String id);

    int update(User user);

    User getXiangXi(String id);

    int updatePwd(Map<String, Object> map);
}
