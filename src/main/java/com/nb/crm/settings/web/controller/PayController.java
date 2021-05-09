package com.nb.crm.settings.web.controller;

import com.nb.crm.settings.domain.Check;
import com.nb.crm.settings.domain.Park;
import com.nb.crm.settings.domain.Pay;
import com.nb.crm.settings.service.ICheckService;
import com.nb.crm.settings.service.IParkService;
import com.nb.crm.settings.service.IPayService;
import com.nb.crm.settings.service.impl.ICheckServiceImpl;
import com.nb.crm.settings.service.impl.IParkServiceImpl;
import com.nb.crm.settings.service.impl.IPayServiceImpl;
import com.nb.crm.utils.PrintJson;
import com.nb.crm.utils.ServiceFactory;
import com.nb.crm.utils.UUIDUtil;
import com.nb.crm.vo.PaginationVo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PayController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("缴费控制器");
        String path =req.getServletPath();
        if("/setting/pay/pageList.do".equals(path)){
          pageList(req,resp);
        }else if("/settings/pay/getPayListById.do".equals(path)) {
            getPayListById(req,resp);
        }else if("/settings/pay/editPayListById.do".equals(path)) {
            editPayListById(req,resp);
        }else if("/settings/pay/deletePay.do".equals(path)) {
            deletePay(req,resp);
        }else if("/settings/pay/CreatePayList.do".equals(path)) {
            CreatePayList(req,resp);
        }else if("/settings/park/getParkList.do".equals(path)) {
            getParkList(req,resp);
        }
    }

    private void getParkList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("取得用户信息列表");
        IParkService ip= (IParkService) ServiceFactory.getService(new IParkServiceImpl());
        List<Park> list=ip.getUserList();
        PrintJson.printJsonObj(resp,list);
    }

    private void CreatePayList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入创建缴费管理保存控制器");
        String id= UUIDUtil.getUUID();
        String userId=req.getParameter("name");
        String waterElr=req.getParameter("waterElr");
        String parkPay=req.getParameter("parkPay");

        Pay pay=new Pay(id,userId,waterElr,parkPay);
        IPayService ip= (IPayService) ServiceFactory.getService(new IPayServiceImpl());
        Boolean flag=ip.save(pay);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void deletePay(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("删除缴费信息");
        String ids[]=req.getParameterValues("id");
        IPayService ip= (IPayService) ServiceFactory.getService(new IPayServiceImpl());
        boolean flag=ip.deletePay(ids);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void editPayListById(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("点击更新按钮");
        String id= req.getParameter("id");
        String waterElr=req.getParameter("waterElr");
        String parkPay=req.getParameter("parkPay");

        Pay pay=new Pay();
        pay.setId(id);
        pay.setParkPay(parkPay);
        pay.setWaterElr(waterElr);
        IPayService ip= (IPayService) ServiceFactory.getService(new IPayServiceImpl());
        Boolean flag=ip.update(pay);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void getPayListById(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("获得需要修改的缴费信息");
        String id=req.getParameter("id");
        IPayService ip= (IPayService) ServiceFactory.getService(new IPayServiceImpl());
        Pay pay=ip.getPayListById(id);
        PrintJson.printJsonObj(resp,pay);
    }

    private void pageList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("显示或者刷新缴费管理页面");
        String pageNoStr=req.getParameter("pageNo");
        int pageNo=Integer.valueOf(pageNoStr);
        String pageSizeStr=req.getParameter("pageSize");
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount =(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        IPayService ip= (IPayService) ServiceFactory.getService(new IPayServiceImpl());
        PaginationVo<Pay> vo=ip.pageList(map);
        PrintJson.printJsonObj(resp,vo);
    }


}
