<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${css }/member.css">
<link rel="stylesheet" href="${css }/footer.css">
<link rel="stylesheet" href="${ctx }/resources/css/login.css">
<jsp:include page="common_head.jsp"/>
<title>학생관리</title>
</head>
<body>
<div  id="wrapper">
<header>


   </header></div>
      <!-- 로그인화면  -->
      
      <div id="container-login">
         <div id="contatiner-form">
            <form  id="login_box" name="login_box" > 
              
       
                  <span id="span-form">ID</span> 
                  <input type="text" id="login_id" name="login_id" placeholder="ID는 숫자 포함 8자 이내"><br> 
                  <span id="span-form">PASSWORD</span>
                  <input name="login_password" id="login_password"  type="password" placeholder="비밀번호"><br><br>
                  <input type="submit" value="LOGIN" id="login_Btn"/> 
                  <input type="reset" value="CANCEL"/>
             
               <input type="hidden" name="page" value="main"/> 
               <input type="hidden" name="action" value="login"/> 
               
            </form>
            
         </div>
      </div>

<div id="footer">
<footer>
    
  <a href="${ctx }/jdbc_test.jsp">DB연결테스트</a>
  <a href="${ctx }/util/update_email.jsp">이메일 업데이트</a>
  <p>Contact information: <a href="mailto:someone@example.com">
  someone@example.com</a>.</p>
 
</footer>
</div>
<script> 
	member.init();
	//function loginAlert() {/*  document.getElementById('login_id') 객체, dom객체,      .value :attribute */
	//	var input_id =	document.getElementById('login_id').value;/*내장 객체 이기 때문에 document document = new document 이렇게 선언을 안해 주어도 된다.  */
	/* 	var input_pass = document.getElementById('login_password').value;
		if(input_id===""){
			alert('id를 입력하세요')
			return false;
		}
		if(input_pass===""){
			alert('비밀번호를 입력하세요')
			return false;
		}
		var form=document.getElementById('login');
		form.action="${ctx}/common.do";  //
		form.method="post";
		return true; */
	
	/* 밸류는 텍스트박스에 값을 넣어진것을 가져오는 것이기 때문에 get밸류의 의미를 가지지만 액션은 값을 설정해주어야 하기때문에 세터의 의미를 갖는다.
	그래서 액션은 이런식의 처리가 불가능하다. 
	var form=document.getElementById('login_box').action 
	form="${ctx}/common.do"*/
	
	/* 이렇게 폼에 액션을 빼고 여기에다가 넣어준 이유는 기능과 속성을 분리시켜 결합도는 낮추고 응집도를 높이기 위해서 이다.  */
	
	//}
</script>

</body>
</html>