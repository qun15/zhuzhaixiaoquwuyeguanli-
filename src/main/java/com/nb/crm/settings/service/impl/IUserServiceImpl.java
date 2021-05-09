package com.nb.crm.settings.service.impl;

import com.nb.crm.exception.LoginException;
import com.nb.crm.settings.dao.IUserDao;
import com.nb.crm.settings.domain.User;
import com.nb.crm.settings.service.IUserService;
import com.nb.crm.utils.DateTimeUtil;
import com.nb.crm.utils.SqlSessionUtil;
import com.nb.crm.vo.PaginationVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IUserServiceImpl implements IUserService {
    private IUserDao userDao = SqlSessionUtil.getSqlSession().getMapper(IUserDao.class);

    @Override
    public User login(String loginAct, String loginPwd) throws LoginException {
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user=userDao.login(map);
        if(user==null){
            throw new LoginException("账号密码错误");
        }
        String expireTime=user.getExpireTime();
        String currentTime= DateTimeUtil.getSysTime();
        if(expireTime.compareTo(currentTime)<0){
            throw new LoginException("账号已经失效");
        }

        return user;

    }

    @Override
    public List<User> getUserList() {
        List<User> uList=userDao.getUserList();
        return uList;
    }

    @Override
    public Boolean save(User user) {
        boolean flag=false;
       int count= userDao.save(user);
       if(count!=1){
           flag=true;
       }
        return flag;
    }

    @Override
    public PaginationVo<User> pageList(Map<String,Object> map) {
      int total=userDao.getTotalByCondition(map);
      List<User> dataList=userDao.getUserListByCondition(map);
        PaginationVo<User> vo=new PaginationVo<User>();
      vo.setTotal(total);
      vo.setDataList(dataList);
      return vo;
    }

    @Override
    public boolean deleteUser(String[] ids) {
        boolean flag=true;
        int count=userDao.deleteUser(ids);
        if(count==0){
            flag=false;
        }
        return flag;
    }

    @Override
    public User getUserListById(String id) {
        User user=userDao.getUserById(id);
        return user;
    }

    @Override
    public Boolean update(User user) {
        boolean flag=false;
        int count= userDao.update(user);
        if(count!=1){
            flag=true;
        }
        return flag;
    }

    @Override
    public User detail(String id) {
       User user= userDao.getXiangXi(id);
       return user;
    }

    @Override
    public Boolean updatePwd(Map<String, Object> map) {
       Boolean flag=false;
       int count= userDao.updatePwd(map);
       if(count!=0){
           flag=true;
       }
       return flag;
    }
}
