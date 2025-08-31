<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.task.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
TaskVO taskVO = (TaskVO) request.getAttribute("taskVO"); //TaskServlet.java(Concroller), 存入req的taskVO物件
%>

<html>
<head>
<title>任務資料 - listOneTask.jsp</title>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
}
</style>

</head>
<body bgcolor='white'>

	<h4>此頁暫練習採用 Script 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>員工資料 - listOneTask.jsp</h3>
				<h4>
					<a href="select_page.jsp"><img src="images/back1.gif"
						width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>任務流水號</th>
			<th>對應任務類型</th>
			<th>任務名稱</th>
			<th>任務目標值</th>
			<th>任務單位</th>
			<th>任務開始日</th>
			<th>任務結束日</th>
			<th>點數</th>
			<th>任務圖騰</th>
			<th>任務建立人員 ID</th>
		</tr>
		<tr>
			<td>${taskVO.taskId}</td>
			<td>${taskVO.taskTypeId}</td>
			<td>${taskVO.taskName}</td>
			<td>${taskVO.targetValue}</td>
			<td>${taskVO.unit}</td>
			<td>${taskVO.startTime}</td>
			<td>${taskVO.endTime}</td>
			<td>${taskVO.points}</td>
			<td>${taskVO.taskIcon}</td>
			<td>${taskVO.adminId}</td>
		</tr>
	</table>

</body>
</html>