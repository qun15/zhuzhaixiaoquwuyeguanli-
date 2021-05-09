package com.nb.crm.settings.web.controller;

import com.nb.crm.settings.domain.Park;
import com.nb.crm.settings.domain.User;
import com.nb.crm.settings.service.IParkService;
import com.nb.crm.settings.service.IUserService;
import com.nb.crm.settings.service.impl.IParkServiceImpl;
import com.nb.crm.settings.service.impl.IUserServiceImpl;
import com.nb.crm.utils.MD5Util;
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

public class ParkController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("用户控制器");
        String path =req.getServletPath();
        if("/setting/park/pageList.do".equals(path)){
          pageList(req,resp);
        }else if("/settings/park/getParkListById.do".equals(path)) {
            getParkListById(req,resp);
        }else if("/settings/park/editParkListById.do".equals(path)) {
            editParkListById(req,resp);
        }else if("/settings/park/deletePark.do".equals(path)) {
            deletePark(req,resp);
        }else if("/settings/park/CreateParkList.do".equals(path)) {
            CreateParkList(req,resp);
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

    private void CreateParkList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入创建停车管理保存控制器");
        String id= UUIDUtil.getUUID();
        String userId=req.getParameter("name");
        String chePai=req.getParameter("chePai");
        String cheWei=req.getParameter("cheWei");

        Park park=new Park();
        park.setCheWei(cheWei);
        park.setId(id);
        park.setChePai(chePai);
        park.setUserId(userId);
        System.out.println("_______"+park);
        IParkService ip= (IParkService) ServiceFactory.getService(new IParkServiceImpl());
        Boolean flag=ip.save(park);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void deletePark(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("删除停车信息");
        String ids[]=req.getParameterValues("id");
        IParkService ip= (IParkService) ServiceFactory.getService(new IParkServiceImpl());
        boolean flag=ip.deletePark(ids);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void editParkListById(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("点击更新按钮");
        String id= req.getParameter("id");
        String chePai=req.getParameter("chePai");
        String cheWei=req.getParameter("cheWei");

      Park park=new Park();
      park.setId(id);
      park.setChePai(chePai);
      park.setCheWei(cheWei);
        IParkService ip= (IParkService) ServiceFactory.getService(new IParkServiceImpl());
        Boolean flag=ip.update(park);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void getParkListById(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("获得需要修改的停车信息");
        String id=req.getParameter("id");
        IParkService ip= (IParkService) ServiceFactory.getService(new IParkServiceImpl());
        Park park=ip.getParkListById(id);
        PrintJson.printJsonObj(resp,park);
    }

    private void pageList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("显示停车管理页面");
        String pageNoStr=req.getParameter("pageNo");
        int pageNo=Integer.valueOf(pageNoStr);
        String pageSizeStr=req.getParameter("pageSize");
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount =(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        IParkService ip= (IParkService) ServiceFactory.getService(new IParkServiceImpl());
        PaginationVo<Park> vo=ip.pageList(map);
        PrintJson.printJsonObj(resp,vo);
    }


}
