package com.nb.crm.workbench.web.controller;

import com.nb.crm.settings.domain.User;
import com.nb.crm.settings.service.IUserService;
import com.nb.crm.settings.service.impl.IUserServiceImpl;
import com.nb.crm.utils.*;
import com.nb.crm.vo.PaginationVo;
import com.nb.crm.workbench.domain.Activity;
import com.nb.crm.workbench.domain.ActivityRemark;
import com.nb.crm.workbench.service.IActivityService;
import com.nb.crm.workbench.service.impl.IActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("进入活动控制器");
        String path = req.getServletPath();
        if ("/workbench/activity/getUserList.do".equals(path)) {
//            login(req,resp);
            getUserList(req, resp);
        } else if ("/workbench/activity/save.do".equals(path)) {
            save(req, resp);

        } else if ("/workbench/activity/pageList.do".equals(path)) {
            pageList(req, resp);
        } else if ("/workbench/activity/delete.do".equals(path)) {
            delete(req, resp);
        }else if ("/workbench/activity/updateActivity.do".equals(path)) {
            updateActivity(req, resp);
        }else if ("/workbench/activity/update.do".equals(path)) {
            update(req, resp);
        }else if ("/workbench/activity/detail.do".equals(path)) {
            detail(req, resp);
        }
        else if ("/workbench/activity/getRemakeListByAid.do".equals(path)) {
            getRemakeListByAid(req, resp);
        }
        else if ("/workbench/activity/deleteRemark.do".equals(path)) {
            deleteRemark(req, resp);
        }else if ("/workbench/activity/saveRemark.do".equals(path)) {
            saveRemark(req, resp);
        }else if ("/workbench/activity/updateRemark.do".equals(path)) {
            updateRemark(req, resp);
        }
    }

    private void updateRemark(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("执行修改备注的操作");
        String noteContent=req.getParameter("noteContent");
        String id=req.getParameter("id");
        String editTime=DateTimeUtil.getSysTime();
        String editBy=((User)req.getSession().getAttribute("user")).getName();
        String editFlag="1";
        ActivityRemark ar=new ActivityRemark();
        ar.setId(id);
        ar.setEditFlag(editFlag);
        ar.setNoteContent(noteContent);
        ar.setEditBy(editBy);
        ar.setEditTime(editTime);
        IActivityService as= (IActivityService) ServiceFactory.getService(new IActivityServiceImpl());
        Boolean flag=as.updateRemark(ar);

        Map<String,Object> map=new HashMap<String, Object>();
        map.put("ar",ar);
        map.put("success",flag);
        PrintJson.printJsonObj(resp,map);

    }

    private void saveRemark(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("执行添加备注操作");
        String notContent=req.getParameter("noteContent");
        String activityId=req.getParameter("activityId");
        String id=UUIDUtil.getUUID();
        String createTime=DateTimeUtil.getSysTime();
        String createBy=((User)req.getSession().getAttribute("user")).getName();
        String editFlag="0";

        ActivityRemark ar=new ActivityRemark();
        ar.setId(id);
        ar.setNoteContent(notContent);
        ar.setActivityId(activityId);
        ar.setCreateBy(createBy);
        ar.setCreateTime(createTime);
        ar.setEditFlag(editFlag);
        IActivityService as= (IActivityService) ServiceFactory.getService(new IActivityServiceImpl());
        boolean flag=as.saveRemark(ar);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("ar",ar);
        map.put("success",flag);
        PrintJson.printJsonObj(resp,map);
    }

    private void deleteRemark(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println(" 删除备注操作");
        String id=req.getParameter("id");
        IActivityService as= (IActivityService) ServiceFactory.getService(new IActivityServiceImpl());
        Boolean flag=as.deleteRemark(id);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void getRemakeListByAid(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("根据住户投诉id取得备注信息列表");
        String activityId=req.getParameter("activityId");
        IActivityService as= (IActivityService) ServiceFactory.getService(new IActivityServiceImpl());
        List<ActivityRemark> list=as.getRemakeListByAid(activityId);
        PrintJson.printJsonObj(resp,list);
    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("跳转到详细信息页");
        String id=req.getParameter("id");
        IActivityService as= (IActivityService) ServiceFactory.getService(new IActivityServiceImpl());
        Activity a=as.detail(id);
        req.setAttribute("a",a);
        req.getRequestDispatcher("/workbench/activity/detail.jsp").forward(req,resp);
    }

    private void update(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("修改模态窗口更新按钮");
        String id= req.getParameter("id");
        String owner=req.getParameter("owner");
        String name=req.getParameter("name");
        String startDate=req.getParameter("startDate");
        String endDate=req.getParameter("endDate");
        String cost=req.getParameter("cost");
        String description=req.getParameter("description");
        String editTime= DateTimeUtil.getSysTime();
        String editBy= ((User) req.getSession().getAttribute("user")).getName();
        Activity a=new Activity();
        a.setId(id);
        a.setCost(cost);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setOwner(owner);
        a.setName(name);
        a.setDescription(description);
        a.setEditTime(editTime);
        a.setEditBy(editBy);
        IActivityService as= (IActivityService) ServiceFactory.getService(new IActivityServiceImpl());
        boolean flag=as.update(a);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void updateActivity(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入修改操作控制器");
        String id=req.getParameter("id");
        IActivityService as= (IActivityService) ServiceFactory.getService(new IActivityServiceImpl());
        Map<String,Object> map=as.activityUpdate(id);
        PrintJson.printJsonObj(resp,map);
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入删除操作");
        String ids[]=req.getParameterValues("id");
        IActivityService as= (IActivityService) ServiceFactory.getService(new IActivityServiceImpl());
        boolean flag=as.delete(ids);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void pageList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入到查询投诉信息列表的操作");
        String name=req.getParameter("name");
        String owner=req.getParameter("owner");
        String startDate=req.getParameter("startDate");
        String endDate=req.getParameter("endDate");
        String pageNoStr=req.getParameter("pageNo");
        int pageNo=Integer.valueOf(pageNoStr);
        String pageSizeStr=req.getParameter("pageSize");
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount=(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        IActivityService as= (IActivityService) ServiceFactory.getService(new IActivityServiceImpl());
        PaginationVo<Activity> vo=as.pageList(map);
        PrintJson.printJsonObj(resp,vo);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("执行投诉添加操作");
        String id= UUIDUtil.getUUID();
        String owner=req.getParameter("owner");
        String name=req.getParameter("name");
        String startDate=req.getParameter("startDate");
//        System.out.println("------------"+startDate);
        String endDate=req.getParameter("endDate");
        String cost=req.getParameter("cost");
        String description=req.getParameter("description");
        String createTime= DateTimeUtil.getSysTime();
        String createBy= ((User) req.getSession().getAttribute("user")).getName();
        Activity a=new Activity();
        a.setId(id);
        a.setCost(cost);
        a.setStartDate(startDate);
        a.setEndDate(endDate);
        a.setOwner(owner);
        a.setName(name);
        a.setDescription(description);
        a.setCreateTime(createTime);
        a.setCreateBy(createBy);
        IActivityService as= (IActivityService) ServiceFactory.getService(new IActivityServiceImpl());
        boolean flag=as.save(a);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("取得用户信息列表");
        IUserService us= (IUserService) ServiceFactory.getService(new IUserServiceImpl());
        List<User> uList=us.getUserList();
        PrintJson.printJsonObj(resp,uList);
    }

}
