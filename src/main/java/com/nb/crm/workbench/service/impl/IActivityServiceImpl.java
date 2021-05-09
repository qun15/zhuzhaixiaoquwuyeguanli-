package com.nb.crm.workbench.service.impl;

import com.nb.crm.settings.dao.IUserDao;
import com.nb.crm.settings.domain.User;
import com.nb.crm.utils.SqlSessionUtil;
import com.nb.crm.vo.PaginationVo;
import com.nb.crm.workbench.dao.IActivityDao;
import com.nb.crm.workbench.dao.IActivityRemakeDao;
import com.nb.crm.workbench.domain.Activity;
import com.nb.crm.workbench.domain.ActivityRemark;
import com.nb.crm.workbench.service.IActivityService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IActivityServiceImpl implements IActivityService {
    private IActivityDao activityDao= SqlSessionUtil.getSqlSession().getMapper(IActivityDao.class);
    private IActivityRemakeDao activityRemakeDao= SqlSessionUtil.getSqlSession().getMapper(IActivityRemakeDao.class);
    private IUserDao userDao= SqlSessionUtil.getSqlSession().getMapper(IUserDao.class);

    @Override
    public boolean save(Activity a) {
        boolean flag=true;
        int count=activityDao.save(a);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public PaginationVo<Activity> pageList(Map<String, Object> map) {
        int total=activityDao.getTotalByCondition(map);
        List<Activity> dataList= activityDao.getActivityListByCondition(map);
        PaginationVo<Activity> vo=new PaginationVo<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public boolean delete(String[] ids) {
        boolean flag=true;
        int count1=activityRemakeDao.getCountByAids(ids);
        int count2=activityRemakeDao.deleteByAids(ids);
        if(count1!=count2){
            flag=false;
        }
        int count3=activityDao.delete(ids);
        if(count3!=ids.length){
            flag=false;
        }
        return flag;
    }



    @Override
    public Map<String, Object> activityUpdate(String id) {
        Activity a=activityDao.getByid(id);
        List<User> uList=userDao.getUserList();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("uList",uList);
        map.put("a",a);
        return map;
    }

    @Override
    public boolean update(Activity a) {
        boolean flag=true;
        int count=activityDao.update(a);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Activity detail(String id) {
        Activity a=activityDao.detail(id);
        return a;
    }

    @Override
    public List<ActivityRemark> getRemakeListByAid(String activityId) {
        List<ActivityRemark> list=activityRemakeDao.getRemakeListByAid(activityId);
        return list;
    }

    @Override
    public Boolean deleteRemark(String id) {
        boolean flag=true;
        int count=activityRemakeDao.deleteById(id);
        if(count!=1){
            flag=false;
        }
         return flag;
    }

    @Override
    public boolean saveRemark(ActivityRemark ar) {
        boolean flag=true;
        int count=activityRemakeDao.saveRemark(ar);
        if(count!=1){
            flag=false;
        }
        return flag;
    }

    @Override
    public Boolean updateRemark(ActivityRemark ar) {
        boolean flag=true;
        int count=activityRemakeDao.updateRemark(ar);
        if(count!=1){
            flag=false;
        }
        return flag;
    }
}
