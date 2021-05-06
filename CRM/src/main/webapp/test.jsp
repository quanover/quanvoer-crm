<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" +
            request.getServerName() + ":" + request.getServerPort() +
            request.getContextPath() + "/";
%>

<html>
<head>
    <title>Title</title>
    <base href="<%=basePath%>">
</head>
<body>

    <!--
         add:跳转到添加页，或者打开添加操作的模态窗口
         save：执行添加操作
         edit;跳转到修改页，或者打开修改操作的模态窗口
         update：修改操作
         get：查询操作
     -->

    $.ajax({
        url:"",
        data:{},
        type:"",
        dataType:"json",
        success:function (resp) {

        }

    })

    String id= UUIDUtil.getUUID();
    String createTime= DateTimeUtil.getSysTime();
    String createBy= ((User)request.getSession().getAttribute("user")).getName();

    String createBy= ((User)session.getAttribute("user")).getName();


    $(".time").datetimepicker({
    minView: "month",		// 设置只显示到月份
    language:  'zh-CN',
    format: 'yyyy-mm-dd', 	// 显示格式
    autoclose: true,		// 选中自动关闭
    todayBtn: true,			// 显示今日按钮
    pickerPosition: "top-left"
    });
</body>
</html>
