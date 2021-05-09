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
				var  $xz=$("input[name=xz]:checked");
				if($xz.length==0){
					alert("请选择需要修改的记录");
				}else if($xz.length>1){
					alert("只能选择一条记录进行修改");
				}else {
					var id=$xz.val();
					$.ajax({
						url:"settings/park/getParkListById.do",
						data: {
							"id":id
						},
						type:"get",
						dataType:"json",
						success:function (data){
							$("#edit-parkId").val(data.id);
							$("#edit-parkChePai").val(data.chePai);
							$("#edit-parkCheWei").val(data.cheWei);
							$("#editParkModal").modal("show");
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
					url:"settings/park/editParkListById.do",
					data: {
						"id": $.trim($("#edit-parkId").val()),
						"chePai": $.trim($("#edit-parkChePai").val()),
						"cheWei": $.trim($("#edit-parkCheWei").val()),
					},
						type: "post",
						dataType: "json",
						success: function (data) {
							$("#parkUpdateForm")[0].reset();
							$("#editParkModal").modal("hide");
							pageList(1,2);
					}
				})
			})

            $("#addBtn").click(function (){
            	$("#createParkModal").modal("show");
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
					url:"settings/park/CreateParkList.do",
					data: {
						"name":$.trim($("#create-userName").val()),
						"chePai":$.trim($("#create-ParkChePai").val()),
						"cheWei":$.trim($("#create-ParkCheWei").val()),
					},
					type:"post",
					dataType:"json",
					success:function (data){
						pageList(1,2);
						$("#parkAddForm")[0].reset();
						$("#createParkModal").modal("hide");
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
						url:"settings/park/deletePark.do",
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
				url:"setting/park/pageList.do",
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
						html +='<td>'+n.cheWei+'</td> ';
						html +='<td>'+n.chePai+'</td> ';
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
<div class="modal fade" id="editParkModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 85%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel">修改住户 </h4>
			</div>
			<div class="modal-body">
				<form class="form-horizontal" role="form" id="parkUpdateForm">
					<input type="hidden" id="edit-parkId">
					<div class="form-group">
						<label for="edit-customerOwner" class="col-sm-2 control-label">停车位<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-parkCheWei" >

							</select>
						</div>
						<label for="edit-customerName" class="col-sm-2 control-label">车牌号<span style="font-size: 15px; color: red;">*</span></label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="edit-parkChePai" >
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
<div class="modal fade" id="createParkModal" role="dialog">
	<div class="modal-dialog" role="document" style="width: 85%;">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					<span aria-hidden="true">×</span>
				</button>
				<h4 class="modal-title" id="myModalLabel1">新增停车信息</h4>
			</div>
			<div class="modal-body">
				<form id="parkAddForm" class="form-horizontal" role="form">

					<div class="form-group">

					<label for="create-customerOwner" class="col-sm-2 control-label">住户姓名<span style="font-size: 15px; color: red;">*</span></label>
					<div class="col-sm-10" style="width: 300px;">
						<select class="form-control" id="create-userName">

						</select>
					</div>
					</div>
					<div class="form-group">
						<label for="create-website" class="col-sm-2 control-label">停车位</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-ParkCheWei">
						</div>
						<label for="create-phone" class="col-sm-2 control-label">车牌号</label>
						<div class="col-sm-10" style="width: 300px;">
							<input type="text" class="form-control" id="create-ParkChePai">
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
			<h3>停车管理</h3>
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
					<td>住户姓名</td>
					<td>停车位</td>
					<td>车牌号</td>
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