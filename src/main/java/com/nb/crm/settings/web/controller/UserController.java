package com.nb.crm.settings.web.controller;

import com.nb.crm.settings.domain.User;
import com.nb.crm.settings.service.IUserService;
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

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("用户控制器");
        String path =req.getServletPath();
        if("/settings/user/login.do".equals(path)){
            login(req,resp);
        }else if("/settings/user/getUserList.do".equals(path)) {
            getUserList(req,resp);
        }else if ("/settings/user/CreateUserList.do".equals(path)){
            CreateUserList(req,resp);
        }
        else if ("/settings/user/pageList.do".equals(path)){
           pageList(req,resp);
        }
        else if ("/settings/user/deleteUser.do".equals(path)){
           deleteUser(req,resp);
        } else if ("/settings/user/getUserListById.do".equals(path)){
            getUserListById(req,resp);
        }else if ("/settings/user/editUserListById.do".equals(path)){
            editUserListById(req,resp);
        }else if ("/workbench/customer/detail.do".equals(path)){
            detail(req,resp);
        }else if ("/settings/user/miMa.do".equals(path)){
            miMa(req,resp);
        }
    }

    private void miMa(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("修改管理员密码");
        String id=req.getParameter("id");
        String oldPwd1=req.getParameter("oldPwd");
        String newPwd1=req.getParameter("newPwd");
        String oldPwd=MD5Util.getMD5(oldPwd1);
        String newPwd=MD5Util.getMD5(newPwd1);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("id",id);
        map.put("oldPwd",oldPwd);
        map.put("newPwd",newPwd);
        IUserService is= (IUserService) ServiceFactory.getService(new IUserServiceImpl());
        Boolean flag=is.updatePwd(map);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void detail(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("详细信息页面");
        String id=req.getParameter("id");
        IUserService is= (IUserService) ServiceFactory.getService(new IUserServiceImpl());
        User u=is.detail(id);
        req.setAttribute("u",u);
        try {
            req.getRequestDispatcher("/workbench/customer/detail.jsp").forward(req,resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editUserListById(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("点击更新按钮");
        String id= req.getParameter("id");
        String name=req.getParameter("name");
        String phone=req.getParameter("phone");
        String sfz=req.getParameter("sfz");
        String expireTime=req.getParameter("expireTime");
        String content=req.getParameter("content");
        String park=req.getParameter("park");
        String address=req.getParameter("address");

        User user=new User();
        user.setName(name);
        user.setId(id);
        user.setPark(park);
        user.setPhone(phone);
        user.setSfz(sfz);
        user.setExpireTime(expireTime);
        user.setContent(content);
        user.setAddress(address);
        IUserService is= (IUserService) ServiceFactory.getService(new IUserServiceImpl());
        Boolean flag=is.update(user);
        PrintJson.printJsonFlag(resp,flag);

    }

    private void getUserListById(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("获得需要修改的住户信息");
        String id=req.getParameter("id");
        IUserService is= (IUserService) ServiceFactory.getService(new IUserServiceImpl());
        User user=is.getUserListById(id);
        PrintJson.printJsonObj(resp,user);

    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("删除住户");
        String ids[]=req.getParameterValues("id");
        IUserService is= (IUserService) ServiceFactory.getService(new IUserServiceImpl());
        boolean flag=is.deleteUser(ids);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void pageList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("分页查询");
        String name=req.getParameter("name");
        String address=req.getParameter("address");
        String expireTime=req.getParameter("expireTime");
        String phone=req.getParameter("phone");
        String pageNoStr=req.getParameter("pageNo");
        int pageNo=Integer.valueOf(pageNoStr);
        String pageSizeStr=req.getParameter("pageSize");
        int pageSize=Integer.valueOf(pageSizeStr);
        int skipCount =(pageNo-1)*pageSize;
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("name",name);
        map.put("address",address);
        map.put("expireTime",expireTime);
        map.put("phone",phone);
        map.put("skipCount",skipCount);
        map.put("pageSize",pageSize);
        IUserService is= (IUserService) ServiceFactory.getService(new IUserServiceImpl());
        PaginationVo<User> u=is.pageList(map);
        PrintJson.printJsonObj(resp,u);
    }

    private void CreateUserList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入创建用户保存控制器");
        String id= UUIDUtil.getUUID();
        String name=req.getParameter("name");
        String phone=req.getParameter("phone");
        String sfz=req.getParameter("sfz");
        String expireTime=req.getParameter("expireTime");
        String content=req.getParameter("content");
        String park=req.getParameter("park");
        String address=req.getParameter("address");

        User user=new User();
        user.setName(name);
        user.setId(id);
        user.setPark(park);
        user.setPhone(phone);
        user.setSfz(sfz);
        user.setExpireTime(expireTime);
        user.setContent(content);
        user.setAddress(address);
        IUserService is= (IUserService) ServiceFactory.getService(new IUserServiceImpl());
        Boolean flag=is.save(user);
        PrintJson.printJsonFlag(resp,flag);
    }

    private void login(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("进入到验证登录操作");
        String loginAct=req.getParameter("loginAct");
        String loginPwd=req.getParameter("loginPwd");
        loginPwd= MD5Util.getMD5(loginPwd);
        IUserService us= (IUserService) ServiceFactory.getService(new IUserServiceImpl());
     try{
         User user = us.login(loginAct,loginPwd);
         req.getSession().setAttribute("user",user);
         PrintJson.printJsonFlag(resp,true);
     }catch (Exception e){
         e.printStackTrace();
         String msg=e.getMessage();
         Map<String,Object> map=new HashMap<String,Object>();
         map.put("success",false);
         map.put("msg",msg);
         PrintJson.printJsonObj(resp,map);
     }
    }
    private void getUserList(HttpServletRequest req, HttpServletResponse resp) {
        System.out.println("取得用户信息列表");
        IUserService us= (IUserService) ServiceFactory.getService(new IUserServiceImpl());
        List<User> uList=us.getUserList();
        PrintJson.printJsonObj(resp,uList);
    }
}
