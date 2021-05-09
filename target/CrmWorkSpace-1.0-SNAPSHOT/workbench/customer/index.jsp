<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme()+"://"+
request.getServerName()+":"+request.getServerPort()+
request.getContextPath()+"/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
       <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
	   <script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
           	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	    <script type="text/javascript" src="jquery/bs_pagination/en.js"></script>
	<script type="text/javascript">

	$(function(){
		$("#addBtn").click(function (){


			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});
			 $("#createCustomerModal").modal("show");
			$.ajax({
				url:"settings/user/getUserList.do",
				type:"get",
				dataType:"json",
				success:function (resp){
					var html="";
					$.each(resp,function (i,n){
						html +="<option value='"+n.id+"'>"+n.name+"</option>";
					})
					$("#create-customerOwner").html(html);
				}
			})
		})
		$("#saveBtn").click(function (){
			$.ajax({
				url:"settings/user/CreateUserList.do",
				data: {
                   "name":$.trim($("#create-userName").val()),
				"phone":$.trim($("#create-userPhone").val()),
				"sfz":$.trim($("#create-userSfz").val()),
				"expireTime":$.trim($("#create-UserExpireTime").val()),
				"content":$.trim($("#create-userContent").val()),
				"park":$.trim($("#create-userPark").val()),
				"address":$.trim($("#create-userAddress").val()),
				},
				type:"post",
				dataType:"json",
				success:function (data){
                    pageList(1,2);
					$("#userAddForm")[0].reset();
					$("#createCustomerModal").modal("hide");
				}

			})

		})
		$("#editBtn").click(function (){
			$.ajax({
				url:"settings/user/editUserListById.do",
				data: {
					"id":$.trim($("#edit-userId").val()),
					"name":$.trim($("#edit-userName").val()),
					"phone":$.trim($("#edit-userPhone").val()),
					"sfz":$.trim($("#create-userSfz").val()),
					"expireTime":$.trim($("#edit-UserExpireTime").val()),
					"content":$.trim($("#edit-userContent").val()),
					"park":$.trim($("#edit-userPark").val()),
					"address":$.trim($("#edit-userAddress").val()),
				},
				type:"post",
				dataType:"json",
				success:function (data){
					$("#userUpdateForm")[0].reset();
					$("#editCustomerModal").modal("hide");
					pageList(1,2);
				}

			})

		})


		//定制字段
		$("#definedColumns > li").click(function(e) {
			//防止下拉菜单消失
	        e.stopPropagation();
	    });
		pageList(1,2)
		$("#searchBtn").click(function (){
			pageList(1,2);

		})
		$("#deleteBtn").click(function (){

			var $xz =$("input[name=xz]:checked");
			if($xz.length==0){
				alert("请选择需要删除的记录")
			}else {
				var param="";
				for(var i=0;i<$xz.length;i++){
					param +="id="+$($xz[i]).val();
					if(i<$xz.length-1){
						param +="&";
					}
				}
				$.ajax({
					url:"settings/user/deleteUser.do",
					data: param,
					type:"post",
					dataType:"json",
					success:function (data){
						if(data.success){
							pageList(1,2);
						}else {
							alert("删除住户失败")
						}
					}
				})
			}

		})
		//为全选的复选框绑定事件， 触发全选操作
		$("#qx").click(function (){

			$("input[name=xz]").prop("checked",this.checked);
		})
		$("#userTbody").on("click",$("input[name=xz]"),function (){
			$("#qx").prop("checked",$("input[name=xz]").length==$("input[name=xz]:checked").length)
		})

		$("#updateBtn").click(function (){
			$(".time").datetimepicker({
				minView: "month",
				language:  'zh-CN',
				format: 'yyyy-mm-dd',
				autoclose: true,
				todayBtn: true,
				pickerPosition: "bottom-left"
			});
			var  $xz=$("input[name=xz]:checked");
			if($xz.length==0){
				alert("请选择需要修改的记录");
			}else if($xz.length>1){
				alert("只能选择一条记录进行修改");
			}else {
				var id=$xz.val();
				$.ajax({
					url:"settings/user/getUserListById.do",
					data: {
						"id":id
					},
					type:"get",
					dataType:"json",
					success:function (data){
						var html="<option></option>";
						html +="<option value='"+data.id+"'>"+data.name+"</option>"

						$("#edit-UserOwner").html(html);
						$("#edit-userName").val(data.name);
						$("#edit-userPhone").val(data.phone);
						$("#edit-userContent").val(data.content);
						$("#edit-userExpireTime").val(data.expireTime);
						$("#edit-userPark").val(data.park);
						$("#edit-userSfz").val(data.sfz);
						$("#edit-userAddress").val(data.address);
						$("#edit-userId").val(data.id);
						$("#editCustomerModal").modal("show");
					}
				})

			}
		})

	});

	function pageList(pageNo,pageSize){
		$("#qx").prop("checked",false);
		$.ajax({
			url:"settings/user/pageList.do",
			data: {
				"pageNo":pageNo,
				"pageSize":pageSize,
				"name":$.trim($("#search-userName").val()),
				"address":$.trim($("#search-userAddress").val()),
				"expireTime":$.trim($("#search-userExpireTime").val()),
				"phone":$.trim($("#search-userPhone").val()),
			},
			type:"get",
			dataType:"json",
			success:function (data){
				var html="";
				$.each(data.dataList,function (i,n){
					html +='<tr class="active"> ';
					html +='<td><input type="checkbox" name="xz" value="'+n.id+'"/></td> ';
					html +='<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/customer/detail.do?id='+n.id+'\';">'+n.name+'</a></td> ';
					html +='<td>'+n.phone+'</td> ';
					html +='<td>'+n.expireTime+'</td> ';
					html +='<td>'+n.address+'</td> ';
					html +='</tr> ';

				})
				$("#userTbody").html(html);
				var totalPages=data.total%pageSize==0?data.total/pageSize:parseInt(data.total/pageSize)+1;
				$("#userPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数x
					totalRows: data.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}
				});

			}
		})

	}
</script>
</head>
<body>

<!-- 修改客户的模态窗口 -->
<div class="modal fade" id="editCustomerModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 85%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">修改住户 </h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" id="userUpdateForm">
					<input type="hidden" id="edit-userId">
					<div class="form-group">
						<label for="edit-customerOwner" class="col-sm-2 control-label">修改者<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<select class="form-control" id="edit-UserOwner">

							</select>
						</div>
						<label for="edit-customerName" class="col-sm-2 control-label">住户姓名<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-userName" >
						</div>
					</div>

					<div class="form-group">
						<label for="edit-website" class="col-sm-2 control-label">手机号码</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-userPhone" value="http://www.bjpowernode.com">
						</div>
						<label for="edit-phone" class="col-sm-2 control-label">身份证号</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-userSfz" value="010-84846003">
						</div>
					</div>

					<div class="form-group">
						<label for="edit-userExpireTime" class="col-sm-2 control-label">入住时间</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control time" id="edit-userExpireTime">
						</div>
					</div>



					<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

					<div style="position: relative;top: 15px;">
						<div class="form-group">
							<label for="create-contactSummary1" class="col-sm-2 control-label">备注信息</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-userContent"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label for="create-nextContactTime2" class="col-sm-2 control-label">停车位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-userPark">
							</div>
						</div>
					</div>

					<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

					<div style="position: relative;top: 20px;">
						<div class="form-group">
							<label for="create-address" class="col-sm-2 control-label">详细地址</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="1" id="edit-userAddress">北京大兴大族企业湾</textarea>
							</div>
						</div>
					</div>
				</form>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="editBtn">更新</button>
			</div>
		</div>
	</div>
</div>
	<!-- 创建客户的模态窗口 -->
	<div class="modal fade" id="createCustomerModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">新增住户</h4>
				</div>
				<div class="modal-body">
					<form id="userAddForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-customerOwner" class="col-sm-2 control-label">管理员（新增住户者）<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-customerOwner">

								</select>
							</div>
							<label for="create-customerName" class="col-sm-2 control-label">住户姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-userName">
							</div>
						</div>
						
						<div class="form-group">
                            <label for="create-website" class="col-sm-2 control-label">手机号码</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-userPhone">
                            </div>
							<label for="create-phone" class="col-sm-2 control-label">身份证号</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-userSfz">
							</div>
						</div>
						<div class="form-group">
							<label for="create-experTime" class="col-sm-2 control-label">入住时间</label>
							<div class="col-sm-10" style="width: 300px;">
<%--								<textarea class="form-control" rows="3" id="create-describe"></textarea>--%>
								<input type="text" class="form-control time" id="create-UserExpireTime">
							</div>
						</div>
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="create-contactSummary" class="col-sm-2 control-label">备注信息</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="create-userContent"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="create-nextContactTime" class="col-sm-2 control-label">停车位</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control" id="create-userPark">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="create-address1" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-userAddress"></textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	

	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>住户信息列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">住户姓名</div>
				      <input class="form-control" type="text" id="search-userName">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">住户地址</div>
				      <input class="form-control" type="text" id="search-userAddress">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">电话号码</div>
				      <input class="form-control" type="text" id="search-userPhone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">入住时间</div>
				      <input class="form-control" type="text" id="search-userExpireTime">
				    </div>
				  </div>
				  
				  <button type="button" id="searchBtn" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="updateBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="qx"/></td>
							<td>住户姓名</td>
							<td>电话号码</td>
							<td>入住时间</td>
							<td>住户地址</td>
						</tr>
					</thead>
					<tbody id="userTbody">
<%--					<tr class="active">--%>
<%--							<td><input type="checkbox" /></td>--%>
<%--							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">动力节点</a></td>--%>
<%--							<td>zhangsan</td>--%>
<%--							<td>010-84846003</td>--%>
<%--							<td>http://www.bjpowernode.com</td>--%>
<%--						</tr>--%>
<%--                        <tr class="active">--%>
<%--                            <td><input type="checkbox" /></td>--%>
<%--                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">动力节点</a></td>--%>
<%--                            <td>zhangsan</td>--%>
<%--                            <td>010-84846003</td>--%>
<%--                            <td>http://www.bjpowernode.com</td>--%>
<%--                        </tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="userPage"></div>
<%--				<div>--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">共<b>50</b>条记录</button>--%>
<%--				</div>--%>
<%--				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>--%>
<%--					<div class="btn-group">--%>
<%--						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">--%>
<%--							10--%>
<%--							<span class="caret"></span>--%>
<%--						</button>--%>
<%--						<ul class="dropdown-menu" role="menu">--%>
<%--							<li><a href="#">20</a></li>--%>
<%--							<li><a href="#">30</a></li>--%>
<%--						</ul>--%>
<%--					</div>--%>
<%--					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>--%>
<%--				</div>--%>
<%--				<div style="position: relative;top: -88px; left: 285px;">--%>
<%--					<nav>--%>
<%--						<ul class="pagination">--%>
<%--							<li class="disabled"><a href="#">首页</a></li>--%>
<%--							<li class="disabled"><a href="#">上一页</a></li>--%>
<%--							<li class="active"><a href="#">1</a></li>--%>
<%--							<li><a href="#">2</a></li>--%>
<%--							<li><a href="#">3</a></li>--%>
<%--							<li><a href="#">4</a></li>--%>
<%--							<li><a href="#">5</a></li>--%>
<%--							<li><a href="#">下一页</a></li>--%>
<%--							<li class="disabled"><a href="#">末页</a></li>--%>
<%--						</ul>--%>
<%--					</nav>--%>
<%--				</div>--%>
			</div>
			
		</div>
		
	</div>
</body>
</html>