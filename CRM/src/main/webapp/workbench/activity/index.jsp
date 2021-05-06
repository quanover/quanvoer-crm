<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" +
request.getServerName() + ":" + request.getServerPort() +
request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js" ></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js" ></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js" ></script>

	<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">
	<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<%--乱码解决2：文件另存为为带BOM的utf-8格式--%>
	<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

	<script type="text/javascript">

	//乱码解决1;导入日历后乱码，就直接贴这里了
	// $.fn.datetimepicker.dates['zh-CN'] = {
	// 	days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
	// 	daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
	// 	daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
	// 	months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
	// 	monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
	// 	today: "今天",
	// 	suffix: [],
	// 	meridiem: ["上午", "下午"]
	// };

	$(function(){


		$(".time").datetimepicker({
			minView: "month",		// 设置只显示到月份
			language:  'zh-CN',
			format: 'yyyy-mm-dd', 	// 显示格式
			autoclose: true,		// 选中自动关闭
			todayBtn: true,			// 显示今日按钮
			pickerPosition: "bottom-left"
		});
		//创建按钮
		$("#addBtn").click(function () {
			// $(".time").datetimepicker({
			// 	minView: "month",		// 设置只显示到月份
			// 	language:  'zh-CN',
			// 	format: 'yyyy-mm-dd', 	// 显示格式
			// 	autoclose: true,		// 选中自动关闭
			// 	todayBtn: true,			// 显示今日按钮
			// 	pickerPosition: "bottom-left"
			// });
			/*
				操作模态窗口的方式：
					需要操作的jQuery对象调用modal()方法，传参：show：打开动态窗口 hide:关闭
			*/
			$.ajax({
				url:"activity/getUserList.do",
				dataType:"json",
				success:function (resp) {
					$("#create-marketActivityOwner").empty();

					$.each(resp,function (index, e) {
						$("#create-marketActivityOwner").append("<option value='"+e.id+"'>"+e.name+"</option>")
						//alert(e.name);
					})

					// 在js中使用el表达式,el表达式一定要套用在字符串中
					var id = "${user.id}";
					$("#create-marketActivityOwner").val(id);   // 默认select标签选中当前用户
					$("#createActivityModal").modal("show");
				}
			})
		})


		//创建模态窗口中的保存按钮
		$("#saveBtn").click(function () {
			$.ajax({
				url:"activity/saveActivity.do",
				data:{
					"owner":$.trim($("#create-marketActivityOwner").val()),
					"name":$.trim($("#create-marketActivityName").val()),
					"startDate":$.trim($("#create-startTime").val()),
					"endDate":$.trim($("#create-endTime").val()),
					"cost":$.trim($("#create-cost").val()),
					"description":$.trim($("#create-describe").val())

				},
				type:"post",
				dataType:"json",
				success:function (resp) {

					if(resp){
						//保存后，回到第一页，保持条数
						pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
						//清空表单
						$("#addAcForm")[0].reset();

						$("#createActivityModal").modal("hide");

					}else {
						alert("添加失败")
					}
				}
			})
		})
		
		pageList(1,2);

		//查询按钮
		$("#searchBtn").click(function () {
			/*
				将搜索的信息保存在隐藏域中，为了查询前将隐藏域的数据给搜索框
				这样点击分页组件就不会携带请求参数
			 */
			$("#hidden-name").val($.trim($("#search-name").val()));
			$("#hidden-owner").val($.trim($("#search-owner").val()));
			$("#hidden-startDate").val($.trim($("#search-startTime").val()));
			$("#hidden-endDate").val($.trim($("#search-endTime").val()));
			pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
		})


		//全选框触发全选事件
		$("#checkAll").click(function () {
			//attr：checked和undefined  prop： true和false
			$("input[name=check]").prop("checked",this.checked)
		})

		/*
			动态生成的元素，不能以普通绑定事件的形式来操作
			动态生成的元素，需要以on方法的形式触发
			语法：
				$(需要绑定的有效的外层元素).on(绑定事件，需要绑定的元素jQuery对象，回调函数)
		 */
		$("#activityBody").on("click",$("input[name=check]"),function () {
			//获取元素的个数 size()和length结果一样
			$("#checkAll").prop("checked",$("input[name=check]").length==$("input[name=check]:checked").length)
		})


		//删除按钮
		$("#deleteBtn").click(function () {
			var $check=$("input[name=check]:checked")
			if($check.length==0){
				alert('请选择要删除的记录')
			}else {
				if(confirm("确定删除所选中的记录吗？")){
					var param=""

					$check.each(function (index, e) {
						param+="id="+e.value;
						if(index<$check.length-1){
							param+="&";
						}

					})
					//alert(param)
					$.ajax({
						url:"activity/delete.do",
						data:param,
						type:"post",
						dataType:"json",
						success:function (resp) {

							if(resp){
								pageList(1,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));
							}else {
								alert("删除失败")
							}
						}

					})
				}

			}
		})


		//修改按钮
		$("#editBtn").click(function () {
			var $check=$("input[name=check]:checked")
			if($check.length==0){
				alert("请选择要修改的记录")
			}else if($check.length>1){
				alert("只能选择一条记录")
			}else {
				var id=$check.val();
				$.ajax({
					url:"activity/getOne.do",
					data:{
						"id":id
					},
					type:"get",
					dataType:"json",
					success:function (resp) {

						var html="";
						$.each(resp.userList,function (index, e) {
							html+="<option value='"+e.id+"'>"+e.name+"</option>"
						})

						$("#edit-marketActivityOwner").html(html);

						$("#edit-marketActivityOwner").val(resp.a.owner);

						$("#hidden-edit-id").val(resp.a.id);
						$("#edit-marketActivityName").val(resp.a.name);
						$("#edit-startTime").val(resp.a.startDate);
						$("#edit-endTime").val(resp.a.endDate);
						$("#edit-cost").val(resp.a.cost);
						$("#edit-describe").val(resp.a.description);

						$("#editActivityModal").modal("show")
					}

				})
			}
		})

		//修改模态窗口中的更新按钮
		/*

			在实际项目开发中，一定是按照先做添加，再做修改的这种顺序
			所以，为了节省开发时间，修改操作一般都是copy添加操作

		 */
		$("#updateBtn").click(function () {
			$.ajax({
				url:"activity/update.do",
				data:{
					"id":$.trim($("#hidden-edit-id").val()),
					"owner":$.trim($("#edit-marketActivityOwner").val()),
					"name":$.trim($("#edit-marketActivityName").val()),
					"startDate":$.trim($("#edit-startTime").val()),
					"endDate":$.trim($("#edit-endTime").val()),
					"cost":$.trim($("#edit-cost").val()),
					"description":$.trim($("#edit-describe").val())

				},
				type:"post",
				dataType:"json",
				success:function (resp) {

					if(resp){
						//修改后，维持当前页面，维持每页条数
						pageList($("#activityPage").bs_pagination('getOption', 'currentPage')
								,$("#activityPage").bs_pagination('getOption', 'rowsPerPage'));



						$("#editActivityModal").modal("hide");

					}else {
						alert("修改失败")
					}
				}
			})
		})
	});

	/*
		pageNo：页码
		pageSize：每天条数
		此方法使用时机：
			1：点击左侧市场活动
			2：创建，修改，删除后刷新页面
			3：查询
			4：点分页
	*/
	function pageList(pageNo,pageSize) {

		//取消全选框的勾选
		$("#checkAll").prop("checked",false);

		$("#search-name").val($.trim($("#hidden-name").val()));
		$("#search-owner").val($.trim($("#hidden-owner").val()));
		$("#search-startDate").val($.trim($("#hidden-startTime").val()));
		$("#search-endDate").val($.trim($("#hidden-endTime").val()));

		$.ajax({
			url:"activity/pageList.do",
			data:{
				"pageNo":pageNo,
				"pageSize":pageSize,
				"name":$.trim($("#search-name").val()),
				"owner":$.trim($("#search-owner").val()),
				"startDate":$.trim($("#search-startTime").val()),
				"endDate":$.trim($("#search-endTime").val())

			},
			dataType:"json",
			success:function (resp) {

				//alert(resp.dataList[0].name)
				var html="";
				$.each(resp.dataList,function (index, e) {
					html+='<tr class="active">'
					html+='<td><input type="checkbox" value="'+e.id+'" name="check" /></td>'
					html+='<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'activity/detail.do?id='+e.id+'\'">'+e.name+'</a></td>'
					html+='<td>'+e.owner+'</td>'
					html+='<td>'+e.startDate+'</td>'
					html+='<td>'+e.endDate+'</td>'
					html+='</tr>'
				})
				//alert(html)
				$("#activityBody").html(html);

				var totalPages=Math.ceil(resp.total/pageSize);
				//分页展示
				$("#activityPage").bs_pagination({
					currentPage: pageNo, // 页码
					rowsPerPage: pageSize, // 每页显示的记录条数
					maxRowsPerPage: 20, // 每页最多显示的记录条数
					totalPages: totalPages, // 总页数
					totalRows: resp.total, // 总记录条数

					visiblePageLinks: 3, // 显示几个卡片

					showGoToPage: true,
					showRowsPerPage: true,
					showRowsInfo: true,
					showRowsDefaultInfo: true,

					//点击分页组件是触发
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

	<%--隐藏域--%>
	<input type="hidden" id="hidden-name"/>
	<input type="hidden" id="hidden-owner"/>
	<input type="hidden" id="hidden-startDate"/>
	<input type="hidden" id="hidden-endDate"/>
	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="addAcForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner" >

								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control  time" id="create-startTime">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control  time" id="create-endTime">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<%--
						data-dismiss="modal" 关闭模态窗口
					--%>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="saveBtn">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form" >

						<input type="hidden" id="hidden-edit-id">
					
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner"></select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" >
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-startTime" >
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" id="edit-endTime" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" >
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<%--
									文本域textarea
										1）一定是以标签对的形式来呈现的，正常状态下标签对要紧紧的挨着
										2）textarea虽然是以标签对的形式呈现，但是它也是属于表单元素范畴
											我们所有对于textarea的取值和赋值操作，应该统一使用val（）方法（而不是html方法）
								--%>
								<textarea class="form-control" rows="3" id="edit-describe"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">

					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" id="updateBtn">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text"  id="search-name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="search-owner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control time" type="text" id="search-startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control time" type="text" id="search-endTime">
				    </div>
				  </div>
				  
				  <button id="searchBtn" type="button" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
					<%--
						data-toggle="modal" 表示触发按钮打开一个模态窗口
						data-target="#createActivityModal" 表示需要打开哪个模态窗口 通过"#id"找
					--%>

				  <button type="button" class="btn btn-primary" id="addBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editBtn"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteBtn"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkAll"/></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">
						<%--<tr class="active">--%>
							<%--<td><input type="checkbox" /></td>--%>
							<%--<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>--%>
                            <%--<td>zhangsan</td>--%>
							<%--<td>2020-10-10</td>--%>
							<%--<td>2020-10-20</td>--%>
						<%--</tr>--%>
                        <%--<tr class="active">--%>
                            <%--<td><input type="checkbox" /></td>--%>
                            <%--<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">发传单</a></td>--%>
                            <%--<td>zhangsan</td>--%>
                            <%--<td>2020-10-10</td>--%>
                            <%--<td>2020-10-20</td>--%>
                        <%--</tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">

				<div id="activityPage"></div>

			</div>
			
		</div>
		
	</div>
</body>
</html>