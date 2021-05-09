package com.nb.crm.settings.service;


import com.nb.crm.settings.domain.Check;
import com.nb.crm.settings.domain.Pay;

import com.nb.crm.vo.PaginationVo;

import java.util.Map;

public interface IPayService {

    PaginationVo<Pay> pageList(Map<String, Object> map);

    Boolean save(Pay pay);

    Pay getPayListById(String id);

    Boolean update(Pay pay);

    boolean deletePay(String[] ids);
}
