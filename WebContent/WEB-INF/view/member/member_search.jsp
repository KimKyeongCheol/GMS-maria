<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<jsp:include page="/WEB-INF/view/common/main_head.jsp"/>
	
		<a href="${ctx }/index.jsp" id="href">Main</a>	
		<div id="container">
		<form action="${ctx }/member/service_search.jsp" id="member_search_box" method="get">
	검색조건<select name="option">
		<option value="id" selected>ID</option>
		<option value="name">이름</option>
	</select> 
	<input type="text" name="search">
	<input type="submit" value="등록" ></input>
	</form>
	</div>
	<div id="footer">
		<jsp:include page="/WEB-INF/view/common/footer.jsp"/>
		</div>


