package com.gms.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gms.web.command.Command;
import com.gms.web.constant.Action;
import com.gms.web.constant.Servlet;
import com.gms.web.domain.MemberBean;
import com.gms.web.service.MemberService;
import com.gms.web.service.MemberServiceImpl;
import com.gms.web.util.DispatcherServlet;
import com.gms.web.util.Separator;

@WebServlet({"/home.do","/common.do"})
public class CommonController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sessoion = request.getSession(); //기존의 것을 불러와야 하기 때문에 new를 쓰지않고 request를 쓴다.(로그인을 한 사람의 정보여야 하기때문에)
		System.out.println("common controller get 진입");
		Separator.init(request);
		switch (Separator.cmd.getAction()) {
		case Action.MOVE:
			DispatcherServlet.send(request, response);
			break;
		case Action.LOGIN:
			MemberService service=MemberServiceImpl.getInstance();
			MemberBean member=new MemberBean();
			member.setId(request.getParameter("login_id"));
			member.setPw(request.getParameter("login_password"));
			Map<String,Object> map=service.login(member);
			if(map.get("page").equals("main")){
				sessoion.setAttribute("user", map.get("user"));
			}
			Separator.cmd.setPage(String.valueOf(map.get("page")));
			Separator.cmd.process();
			DispatcherServlet.send(request, response);
			break;
			
		case Action.LOGOUT:
			System.out.println("logout 진입");
			sessoion.invalidate();
			DispatcherServlet.send(request, response);
			
			/*session.invalidate();*/
		default:
			
			break;
		}
	}

}
