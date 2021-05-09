<%--
  Created by IntelliJ IDEA.
  User: 群群的电子数字计算机 嘿嘿
  Date: 2021/4/13
  Time: 13:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";%>
<base href="<%=basePath%>">
<html>
<head>
    <title>Title</title>
</head>
<body>
$.ajax({
url:" ",
data: {

},
type:"post",
dataType:"json",
success:function (data){

}
})


String createTime= DateTimeUtil.getSysTime();
String createBy= ((User) req.getSession().getAttribute("user")).getName();
</body>
</html>
