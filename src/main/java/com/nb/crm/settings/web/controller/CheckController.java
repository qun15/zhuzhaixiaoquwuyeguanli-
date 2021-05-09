package com.nb.crm.settings.web.controller;

import com.nb.crm.settings.domain.Check;
import com.nb.crm.settings.domain.Park;
import com.nb.crm.settings.service.ICheckService;
import com.nb.crm.settings.service.IParkService;
import com.nb.crm.settings.service.impl.ICheckServiceImpl;
import com.nb.crm.settings.service.impl.IParkServiceImpl;
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

public class CheckController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("报修控制器");
        String path =req.getServletPath();
        if("/setting/check/pageList.do".equals(path)){
          pageList(req,resp);
        }else if("/settings/check/getCheckListById.do".equals(path)) {
            getCheckListById(req,resp);
        }else if("/settings/check/editCheckListById.do".equals(path)) {
            editCheckListById(req,resp);
        }else if("/settings/check/deleteCheck.do".equals(path)) {
            deleteCheck(req,resp);
        }else if("/settings/check/CreateCheckList.do".equals(path)) {
            CreateCheckList(req,resp);
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

    private void CreateCheckList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入创建报修管理保存控制器");
        String id= UUIDUtil.getUUID();
        String userId=req.getParameter("name");
        String content=req.getParameter("content");
        String time=req.getParameter("time");
        String result=req.getParameter("result");

        Check check=new Check();
        check.setId(id);
        check.setCheckTime(time);
        check.setUserId(userId);
        check.setContent(content);
        check.setResult(result);
        ICheckService ic= (ICheckService) ServiceFactory.getService(new ICheckServiceImpl());
        Boolean flag=ic.save(check);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void deleteCheck(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("删除报修信息");
        String ids[]=req.getParameterValues("id");
        ICheckService ic= (ICheckService) ServiceFactory.getService(new ICheckServiceImpl());
        boolean flag=ic.deleteCheck(ids);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void editCheckListById(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("点击更新按钮");
        String id= req.getParameter("id");
        String content=req.getParameter("content");
        String time=req.getParameter("time");
        String result=req.getParameter("result");

      Check check=new Check();
      check.setResult(result);
      check.setCheckTime(time);
      check.setId(id);
      check.setContent(content);
        ICheckService ic= (ICheckService) ServiceFactory.getService(new ICheckServiceImpl());
        Boolean flag=ic.update(check);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void getCheckListById(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("获得需要修改的报修信息");
        String id=req.getParameter("id");
        ICheckService ic= (ICheckService) ServiceFactory.getService(new ICheckServiceImpl());
        Check check=ic.getCheckListById(id);
        PrintJson.printJsonObj(resp,check);
    }

    private void pageList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("显示或者刷新报修管理页面");
        String pageNoStr=req.getParameter("pageNo");
        int pageNo=Integer.valueOf(pageNoStr);
        String pageSizeStr=req.getParameter("pageSize");
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount =(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        ICheckService ic= (ICheckService) ServiceFactory.getService(new ICheckServiceImpl());
        PaginationVo<Check> vo=ic.pageList(map);
        PrintJson.printJsonObj(resp,vo);
    }


}
