<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../constants/db.jsp" %>
<%
	Class.forName(ORACLE_DRIVER);
	String sql="SELECT * FROM Member";
	PreparedStatement pstmt=DriverManager.getConnection(ORACLE_URL,USERNAME,PASSWORD).prepareStatement(sql);
	ResultSet rs=pstmt.executeQuery();
	List<String> list=new ArrayList<>();
	int res=0;
	while(rs.next()){
		list.add(rs.getString("id"));
	}
	for(int i=0;i<list.size();i++){
		sql="UPDATE Member SET email=? where id=?";
		PreparedStatement pstmt1=DriverManager.getConnection(ORACLE_URL,USERNAME,PASSWORD).prepareStatement(sql);
		pstmt.setString(1, list.get(i)+"@gmail.com");
		pstmt.setString(2, list.get(i));
		res=pstmt.executeUpdate();
	}
	
	/* while(rs.next()){
		sql="UPDATE Member SET email=? where id=?";
		pstmt=DriverManager.getConnection(ORACLE_URL,USERNAME,PASSWORD).prepareStatement(sql);
		pstmt.setString(1, rs.getString("id")+"@test.com");
		pstmt.setString(2, rs.getString("id"));
		res=pstmt.executeUpdate();
	}	 */
	if(res==1){
%>
<script type="text/javascript">
	alert('이메일 수정완료')
	location.href="<%=request.getContextPath()%>/login.jsp";
</script>
<%
	}else{
%>
<script type="text/javascript">
	alert('이메일 수정실패')
	location.href="<%=request.getContextPath()%>/login.jsp";
</script>
<% } %>