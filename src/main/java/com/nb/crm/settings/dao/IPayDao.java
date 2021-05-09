package com.nb.crm.settings.dao;

import com.nb.crm.settings.domain.Check;
import com.nb.crm.settings.domain.Pay;

import java.util.List;
import java.util.Map;

public interface IPayDao {

    List<Pay> getPayListByCondition(Map<String, Object> map);

    int getTotalByCondition(Map<String, Object> map);

    int save(Pay pay);

    Pay getPayListById(String id);

    int update(Pay pay);

    int deletePay(String[] ids);
}
