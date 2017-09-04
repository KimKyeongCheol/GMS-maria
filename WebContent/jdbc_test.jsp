<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- %@ : 디렉티브(디렉티브는 설정을 하는 역할)  %:스크립트릿(스크립트릿은 자바에서 클래스 외부의 역할) 라고 부른다. -->
<%@ include file="WEB-INF/view/constants/db.jsp"%>
<%@ page import="java.sql.*"%>
<%
	Class.forName(ORACLE_DRIVER);
	Connection conn = DriverManager.getConnection(ORACLE_URL, USERNAME, PASSWORD);
	Statement stmt = conn.createStatement();
	String sql = String.format("SELECT * FROM %s WHERE %s='kim'",TABLE_MEMBER,MEMBER_ID);
	ResultSet rs = stmt.executeQuery(sql);
	String findName ="";
	if (rs.next()) {
	findName = rs.getString("name");
	}
%>

<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8" />
	<title>Document</title>
</head>
<body>
	<h1>hello <%=findName%>!!</h1>
</body>
</html>