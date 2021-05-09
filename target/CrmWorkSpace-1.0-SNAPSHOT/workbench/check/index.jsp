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
           pageList(1,2)
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
						url:"settings/check/getCheckListById.do",
						data: {
							"id":id
						},
						type:"get",
						dataType:"json",
						success:function (data){
							$("#edit-checkId").val(data.id);
							$("#edit-checkContent").val(data.content);
							$("#edit-checkResult").val(data.result);
							$("#edit-checkResult").val(data.checkTime);
							$("#editCheckModal").modal("show");
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

			$("#editBtn").click(function (){
				$.ajax({
					url:"settings/check/editCheckListById.do",
					data: {
						"id": $.trim($("#edit-checkId").val()),
						"content":$.trim($("#edit-checkContent").val()),
						"result":$.trim($("#edit-checkResult").val()),
						"time":$.trim($("#edit-checkResult").val()),
					},
						type: "post",
						dataType: "json",
						success: function (data) {
							$("#checkUpdateForm")[0].reset();
							$("#editCheckModal").modal("hide");
							pageList(1,2);
					}
				})
			})

            $("#addBtn").click(function (){
				$(".time").datetimepicker({
					minView: "month",
					language:  'zh-CN',
					format: 'yyyy-mm-dd',
					autoclose: true,
					todayBtn: true,
					pickerPosition: "bottom-left"
				});
            	$("#createCheckModal").modal("show");
				$.ajax({
					url:"settings/park/getParkList.do",
					type:"get",
					dataType:"json",
					success:function (resp){
						var html="";
						$.each(resp,function (i,n){
							html +="<option value='"+n.id+"'>"+n.name+"</option>";
						})
						$("#create-userName").html(html);
					}
				})
			})
			$("#saveBtn").click(function (){
				$.ajax({
					url:"settings/check/CreateCheckList.do",
					data: {
						"name":$.trim($("#create-userName").val()),
						"content":$.trim($("#create-checkContent").val()),
						"result":$.trim($("#create-checkResult").val()),
						"time":$.trim($("#create-checkTime").val()),
					},
					type:"post",
					dataType:"json",
					success:function (data){
						pageList(1,2);
						$("#checkAddForm")[0].reset();
						$("#createCheckModal").modal("hide");
					}

				})

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
						url:"settings/check/deleteCheck.do",
						data: param,
						type:"post",
						dataType:"json",
						success:function (data){
							if(data.success){
								pageList(1,2);
							}else {
								alert("删除信息失败")
							}
						}
					})
				}
			})
		});
		function pageList(pageNo,pageSize){
			$("#qx").prop("checked",false);
			$.ajax({
				url:"setting/check/pageList.do",
				data: {
					"pageNo":pageNo,
					"pageSize":pageSize,
				},
				type:"get",
				dataType:"json",
				success:function (data){
					var html="";
					$.each(data.dataList,function (i,n){
						html +='<tr class="active"> ';
						html +='<td><input type="checkbox" name="xz" value="'+n.id+'"/></td> ';
						html +='<td>'+n.userId+'</td> ';
						html +='<td>'+n.content+'</td> ';
						html +='<td>'+n.checkTime+'</td> ';
						html +='<td>'+n.result+'</td> ';
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
<div class="modal fade" id="editCheckModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 85%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">修改住户报修 </h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" id="checkUpdateForm">
					<input type="hidden" id="edit-checkId">
					<div class="form-group">
						<label for="edit-customerOwner" class="col-sm-2 control-label">报修内容<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-checkContent" >

							</select>
						</div>
						<label for="edit-customerName" class="col-sm-2 control-label">报修时间<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control time" id="edit-checkTime" >
						</div>
					</div>
					<div class="form-group">
					<label for="edit-customerName" class="col-sm-2 control-label">报修结果<span style="font-size: 15px; color: red;">*</span></label>
					<div class="col-sm-10" style="width: 300px;">
						<input type="text" class="form-control" id="edit-checkResult" >
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
<!-- 创建停车管理的模态窗口 -->
<div class="modal fade" id="createCheckModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 85%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel1">新增报修信息</h4>
			</div>
			<div class="modal-body">
				<form id="checkAddForm" class="form-horizontal" role="form">

					<div class="form-group">

					<label for="create-customerOwner" class="col-sm-2 control-label">报修住户<span style="font-size: 15px; color: red;">*</span></label>
					<div class="col-sm-10" style="width: 300px;">
						<select class="form-control" id="create-userName">

						</select>
					</div>
					</div>
					<div class="form-group">
						<label for="create-website" class="col-sm-2 control-label">报修内容</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-checkContent">
						</div>
						<label for="create-phone" class="col-sm-2 control-label">报修时间</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control time" id="create-checkTime" readonly>
						</div>
					</div>
					<div class="form-group">
						<label for="create-website" class="col-sm-2 control-label">报修结果</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-checkResult">
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
			<h3>报修信息管理</h3>
		</div>
	</div>
</div>

<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">

	<div style="width: 100%; position: absolute;top: 5px; left: 10px;">


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
					<td>报修住户</td>
					<td>报修内容</td>
					<td>报修时间</td>
					<td>报修结果</td>
				</tr>
				</thead>
				<tbody id="userTbody">

				</tbody>
			</table>
		</div>

		<div style="height: 50px; position: relative;top: 30px;">
			<div id="userPage"></div>

		</div>

	</div>

</div>
</body>
</html>