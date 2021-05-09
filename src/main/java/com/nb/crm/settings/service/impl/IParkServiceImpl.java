package com.nb.crm.settings.service.impl;

import com.nb.crm.settings.dao.IParkDao;
import com.nb.crm.settings.domain.Park;
import com.nb.crm.settings.domain.User;
import com.nb.crm.settings.service.IParkService;
import com.nb.crm.utils.SqlSessionUtil;
import com.nb.crm.vo.PaginationVo;

import java.util.List;
import java.util.Map;

public class IParkServiceImpl implements IParkService {
    private IParkDao iParkDao= SqlSessionUtil.getSqlSession().getMapper(IParkDao.class);
    @Override
    public PaginationVo<Park> pageList(Map<String, Object> map) {
        int total=iParkDao.getTotalByCondition(map);
        List<Park> dataList=iParkDao.getUserListByCondition(map);
        PaginationVo<Park> vo=new PaginationVo<Park>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public Park getParkListById(String id) {
        Park park=iParkDao.getParkListById(id);
        return park;
    }

    @Override
    public Boolean update(Park park) {
        boolean flag=false;
        int count= iParkDao.update(park);
        if(count!=1){
            flag=true;
        }
        return flag;
    }

    @Override
    public boolean deletePark(String[] ids) {
        boolean flag=true;
        int count=iParkDao.deletePark(ids);
        if(count==0){
            flag=false;
        }
        return flag;
    }

    @Override
    public Boolean save(Park park) {
        boolean flag=false;
        int count= iParkDao.save(park);
        if(count!=1){
            flag=true;
        }
        return flag;
    }

    @Override
    public List<Park> getUserList() {
        List<Park> uList=iParkDao.getUserList();
        return uList;
    }
}
