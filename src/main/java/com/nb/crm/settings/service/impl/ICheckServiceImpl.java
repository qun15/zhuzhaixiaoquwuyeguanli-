package com.nb.crm.settings.service.impl;

import com.nb.crm.exception.LoginException;
import com.nb.crm.settings.dao.ICheckDao;
import com.nb.crm.settings.dao.IParkDao;
import com.nb.crm.settings.domain.Check;
import com.nb.crm.settings.domain.Park;
import com.nb.crm.settings.domain.User;
import com.nb.crm.settings.service.ICheckService;
import com.nb.crm.settings.service.IParkService;
import com.nb.crm.utils.SqlSessionUtil;
import com.nb.crm.vo.PaginationVo;

import java.util.List;
import java.util.Map;

public class ICheckServiceImpl implements ICheckService {
    private ICheckDao iCheckDao= SqlSessionUtil.getSqlSession().getMapper(ICheckDao.class);

    @Override
    public Boolean save(Check check) {
        boolean flag=false;
        int count= iCheckDao.save(check);
        if(count!=1){
            flag=true;
        }
        return flag;
    }

    @Override
    public PaginationVo<Check> pageList(Map<String, Object> map) {
        int total=iCheckDao.getTotalByCondition(map);
        List<Check> dataList=iCheckDao.getCheckListByCondition(map);
        PaginationVo<Check> vo=new PaginationVo<Check>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public Check getCheckListById(String id) {
        Check check=iCheckDao.getCheckListById(id);
        return check;
    }

    @Override
    public Boolean update(Check check) {
        boolean flag=false;
        int count= iCheckDao.update(check);
        if(count!=1){
            flag=true;
        }
        return flag;
    }

    @Override
    public boolean deleteCheck(String[] ids) {
        boolean flag=true;
        int count=iCheckDao.deleteCheck(ids);
        if(count==0){
            flag=false;
        }
        return flag;
    }
}
