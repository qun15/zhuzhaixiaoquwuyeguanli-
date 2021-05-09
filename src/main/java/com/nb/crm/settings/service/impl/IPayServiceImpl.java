package com.nb.crm.settings.service.impl;

import com.nb.crm.settings.dao.IPayDao;
import com.nb.crm.settings.domain.Check;
import com.nb.crm.settings.domain.Park;
import com.nb.crm.settings.domain.Pay;
import com.nb.crm.settings.service.IPayService;
import com.nb.crm.utils.SqlSessionUtil;
import com.nb.crm.vo.PaginationVo;

import java.util.List;
import java.util.Map;

public class IPayServiceImpl implements IPayService {
    private IPayDao iPayDao= SqlSessionUtil.getSqlSession().getMapper(IPayDao.class);
    @Override
    public PaginationVo<Pay> pageList(Map<String, Object> map) {
        int total=iPayDao.getTotalByCondition(map);
        List<Pay> dataList=iPayDao.getPayListByCondition(map);
        PaginationVo<Pay> vo=new PaginationVo<Pay>();
        vo.setTotal(total);
        vo.setDataList(dataList);
        return vo;
    }

    @Override
    public Boolean save(Pay pay) {
        boolean flag=false;
        int count= iPayDao.save(pay);
        if(count!=1){
            flag=true;
        }
        return flag;
    }

    @Override
    public Pay getPayListById(String id) {
        Pay pay=iPayDao.getPayListById(id);
        return pay;
    }

    @Override
    public Boolean update(Pay pay) {
        boolean flag=false;
        int count= iPayDao.update(pay);
        if(count!=1){
            flag=true;
        }
        return flag;
    }

    @Override
    public boolean deletePay(String[] ids) {
        Boolean flag=false;
        int count=iPayDao.deletePay(ids);
        if(count==1 || count >1){
            flag=true;
        }
        return flag;
    }
}
