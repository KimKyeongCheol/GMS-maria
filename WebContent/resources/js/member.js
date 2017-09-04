/**
 * member javaScript
 */
var app=(function(){
     var init=function(ctx){
           session.init(ctx);
           member.init();
           onCreate();
     };
     var ctx=function(){
           return session.getPath('ctx');
     };
     var js=function(){
           return session.getPath('js');
     };
     var img=function(){
           return session.getPath('img');
     };
     var css=function(){
           return session.getPath('css');
     };
     
     var setContentView=function(){
           alert('aaa');
     };
     var onCreate=function(){
           setContentView();
           location.href=ctx()+"/home.do";
     };
     
     
     return {
           init : init,
           ctx : ctx,
           img : img,
            js : js,
           css : css   //리턴값 (클로저)에 포함시키지 않으면 외부에서 접근이 불가능하다. 그래서 은닉화 해야할정보는 다른 객체에서 호출해서 사용하면 된다.
     };
})();
var session=(function(){
     var init=function(ctx){
           sessionStorage.setItem('ctx',ctx);
           sessionStorage.setItem('js',ctx+'/resources/js');
           sessionStorage.setItem('img',ctx+'/resources/img');
           sessionStorage.setItem('css',ctx+'/resources/css');
     };
     var getPath=function(x){
           return sessionStorage.getItem(x);
           
     };
     return {
           init : init,
           getPath : getPath
           
     };
})();
var main=(function(){
     var init=function(){
           onCreate();
     };
     var onCreate=function(){
           setContentView();
           $('.list-group-item a').eq(0).on('click',function(){
               controller.moveTo('member','member_add');
            });
            $('.list-group-item a').eq(1).on('click',function(){
               controller.list('member','member_list','1');
            });
            $('.list-group-item a').eq(2).on('click',function(){
         	   /*if(confirm('당신은 바보입니까?')){
         		   alert('반갑습니다. 당신은 바보입니다.');
         	   }else{
         		   alert('바보만 입장가능합니다.');
         	   };*/
               controller.detailStudent(prompt('ID조회'));
            });
            $('.list-group-item a').eq(3).on('click',function(){
               controller.moveTo('member','member_delete');
                /*controller.deleteStudent();*/
            });
     };
     var setContentView=function(){
           
          var $u1=$("#main_ul_stu");
          var $u2=$("#main_ul_grade");
          var $u3=$("#main_ul_board");
           $u1.addClass("list-group");
           $u2.addClass("list-group");
           $u3.addClass("list-group");
          $('.list-group').children().addClass("list-group-item");
         
     };
     return {
           init : init
     }
})();
var home=(function(){
     var init=function(){
           onCreate();
     };
     var onCreate=function(){
           setContentView();
     };
     var setContentView=function(){
           
     };
     return {
           init : init
     }
})();

var memberDatail=(function(){
	var init=function(){
		onCreate();
	};
	var onCreate=function(){
		setContentView();
		$('#updateBtn').on('click',function(){
			   sessionStorage.setItem('id',$('#datail_id').text());
	           sessionStorage.setItem('phone',$('#datail_phone').text());
	           sessionStorage.setItem('email',$('#datail_email').text());
	           sessionStorage.setItem('title',$('#datail_title').text());
	          controller.moveTo('member','member_update');
		});
		
		//기능
	};
	var setContentView=function(){
		alert('memberDatail!!');
		//화면
		
		
	};
	return {
		init : init
	};
})();
var memberUpdate=(function(){
	var init=function(){
		onCreate();
	};
	var onCreate=function(){
		setContentView();
		
		
	};
	var setContentView=function(){
		var id=sessionStorage.getItem('id');
		
		var phone=sessionStorage.getItem('phone');
		var email=sessionStorage.getItem('email');
		var password=$('#confirm').val();
		
		$('#phone').attr('placeholder',phone);
		$('#email').attr('placeholder',email);
		$('#confirmBtn').on('click',function(){
			alert('수정할 아이디 : '+ id);
			controller.updateStudent(id,$('#email').val());
		});
	};
	return{
		init : init
	}
})();
/*var memberUpdate=(function(){
	var init=function(){
		onCreate();
	};
	var onCreate=function(){
		setContentView();
		$('phone').text(sessionStorage.getItem(phone));
		$('email').text(sessionStorage.getItem(email));
		$('title').text(sessionStorage.getItem(title));
		$('updateBtn').on('click',function(){
			 controller.detailStudent();
		})
	};
	var setContentView=function(){
		
	}
	return {
		init : init
	}
})();*/

var controller=(function(){
     var init=function(){
           /*moveTo(dir,page);*/
     };
     var moveTo=function(dir,page){
          location.href=app.ctx()+'/'+dir+".do?action=move&page="+page;
     };
     var logout=function(dir,page){
           location.href = app.ctx()+'/' + dir + ".do?action=logout&page="+page;
     };
     var deleteTarget=function(target){
           prompt(target+'의 ID?');
     };
     var list=function(dir,page,pageNumber){
          location.href=app.ctx()+'/'+dir+".do?action=list&page="+page+"&pageNumber="+pageNumber;
     };
     var updateStudent=function(id,email){
           alert('수정할 id'+id);
          location.href=app.ctx()+"/member.do?action=update&page=member_update&id="+id+"email="+email;
     };
     var deleteStudent=function(id){
           alert('삭제할 id'+id);
          location.href=app.ctx()+'/'+"member.do?action=delete&page=member_list&id="+id;
     };
     var detailStudent=function(id){
           alert('조회할 id : '+id);
           location.href=
        	   app.ctx()+"/member.do?action=detail&page=member_detail&id="+id;
     };
     var searchStudent=function(){
           var search=document.getElementById('search').value;
          location.href=app.ctx()+'/'+"/member.do?action=search&page=member_list&search="+search;
     };
     
     
     return {
           init : init,
           moveTo : moveTo,
           logout : logout,
           deleteTarget : deleteTarget,
           list :list,
           updateStudent : updateStudent,
           deleteStudent : deleteStudent,
           detailStudent : detailStudent,
           searchStudent : searchStudent
     }
})();
var navbar=(function(){
        var init=function(){
           onCreate();
        };
        var onCreate=function(){
           setContentView();
           
           $('.dropdown-menu a').eq(0).on('click',function(){
              alert('0');
              controller.moveTo('member','member_add');
           });
           $('.dropdown-menu a').eq(1).on('click',function(){
              alert('1');
              controller.list('member','member_list','1');
           });
           $('.dropdown-menu a').eq(2).on('click',function(){
              alert('2');
              controller.moveTo('member','member_detail');
           });
           $('.dropdown-menu a').eq(3).on('click',function(){
              alert('3');
              controller.moveTo('member','member_delete');
               /*controller.deleteStudent();*/
           });
           $('#home a').on('click',function(){
        	  controller.moveTo('common','main'); 
           });
           $('#logout').on('click',function(){
        	   controller.moveTo('common','home'); 
           });
        };
        var setContentView=function(){
        	
           var $u1=$("#navbar_ul_stu");
           var $u2=$("#navbar_ul_grade");
           var $u3=$("#navbar_ul_board");
           
           $u1.addClass("dropdown-menu");
           $u2.addClass("dropdown-menu");
           $u3.addClass("dropdown-menu");
           var home=$("#home");
           var logout=$("#logout");
         
        };
        return {
           init : init
        };
     })();


var member=(function(){
     var init=function(){
           $('#login_Btn').on('click',function(){
                alert('로그인 버튼 click');
          if($('#login_id').val()===""){
                alert('ID 를 입력해 주세요');
                return false;
          }
          if($('#login_password').val()===""){
                alert('PASS 를 입력해 주세요');
                return false;
          }
              $('#login_box').attr('action',app.ctx()+"/common.do");
          $('#login_box').attr('method',"post");
          return true;
           }); /*function(){} 이 이벤트 핸들러가 된다.*/
     };   
 
     return {
           init : init //생성자는 항상 오픈해야 한다
     };
})();
