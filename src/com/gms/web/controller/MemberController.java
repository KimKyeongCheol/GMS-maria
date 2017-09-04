package com.gms.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gms.web.command.Command;
import com.gms.web.constant.Action;
import com.gms.web.dao.MemberDaoImpl;
import com.gms.web.domain.MajorBean;
import com.gms.web.domain.MemberBean;
import com.gms.web.domain.StudentBean;
import com.gms.web.proxy.BlockHandler;
import com.gms.web.proxy.PageHandler;
import com.gms.web.proxy.PageProxy;
import com.gms.web.service.MemberService;
import com.gms.web.service.MemberServiceImpl;
import com.gms.web.util.DispatcherServlet;
import com.gms.web.util.ParamsIterator;
import com.gms.web.util.Separator;


@WebServlet("/member.do")
public class MemberController extends HttpServlet {
   private static final long serialVersionUID = 1L;
   protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      System.out.println("member controller 진입");
      Separator.init(request);
      MemberBean member= new MemberBean();
      MemberService service=MemberServiceImpl.getInstance();
      Map<?,?> map=ParamsIterator.execute(request);
      PageProxy pxy=new PageProxy(request);
      Command cmd=new Command();
      pxy.setPageSize(5);
 	 pxy.setBlockSize(5);
      switch (Separator.cmd.getAction()) {
      case Action.MOVE:
         DispatcherServlet.send(request, response);
         break;
      case Action.ADD:
         System.out.println("add 진입");
        map=new HashMap<>();
         	member.setId((String)map.get("id"));
			member.setPw((String)map.get("password"));
			member.setName((String)map.get("name"));
			member.setAddr((String)map.get("addr"));
			member.setPhone((String)map.get("phone"));
			member.setBirth((String)map.get("birthday"));
			member.setGender((String)map.get("gender"));
			member.setEmail((String)map.get("email"));
			member.setMajor_id((String)map.get("major"));
			member.setSubject((String)map.get("subject"));
         // major 은 여러행을 입력해야함
         
         String[] subjects=((String)map.get("subject")).split(",");
         System.out.println("subjects"+subjects);
         List<MajorBean> list= new ArrayList<>();
         MajorBean major=null;
         for(int i=0;i<subjects.length;i++){
            major=new MajorBean();
            major.setMajor_id(String.valueOf((int)(Math.random()*9999)+1000));
            major.setTitle((String)map.get("name"));
            major.setId(member.getId());
            major.setSubj_Id(subjects[i]);
            list.add(major);
          
         }
         System.out.println("과목"+list.toString());
         Map<String,Object>tempMap=new HashMap<>();
         tempMap.put("member", member);
         tempMap.put("major", list);
         service.add(tempMap);
         Separator.cmd.setDir("common");
         Separator.cmd.process();
         DispatcherServlet.send(request, response);
         break;
      case Action.LIST:
    	  System.out.println("MemberList enter....");
   
    	
    	 pxy.setTheNumberOfRow(Integer.parseInt(service.count(cmd)));
    	 pxy.setPageNumber(Integer.parseInt(request.getParameter("pageNumber")));
    	 pxy.execute(BlockHandler.attr(pxy), service.list(PageHandler.attr(pxy)));
    	  DispatcherServlet.send(request, response);
    	 
    	  break;
      /*case Action.SEARCH :
    	  	map=ParamsIterator.execute(request);
    	  	cmd.setColumn("name");
    	  	cmd.setSearch(String.valueOf(map.get("search")));
    	  	
    	  	System.out.println("map.get(search) :"+map.get("search"));
    	  	pxy.setTheNumberOfRow(Integer.parseInt(service.count(cmd)));
    	  	//Map<String,String> searchMap=new HashMap<>();
    	  	cmd=PageHandler.attr(pxy);
    	  	cmd.setPageNumber(request.getParameter("pageNumber"));
    	  	System.out.println("파라미터 pagenumber : "+request.getParameter("pageNumber"));
    	  	cmd.setStartRow(PageHandler.attr(pxy).getStartRow());
    	  	
    	  	cmd.setEndRow(PageHandler.attr(pxy).getEndRow());
    	  	System.out.println("pagenumber "+cmd.getPageNumber());
    	  	pxy.setPageNumber(Integer.parseInt(cmd.getPageNumber()));
    	  	System.out.println("*********페이지 번호 :"+cmd.getPageNumber());
    	  	pxy.execute(BlockHandler.attr(pxy), service.MemberByName(cmd));
    	  	
    	  	request.setAttribute("list",service.MemberByName(cmd));
    	  	pxy.setPageNumber(Integer.parseInt(cmd.getPagaNumber()));
    	  	 pxy.setPageNumber(Integer.parseInt(request.getParameter("pageNumber")));
        	 pxy.execute(BlockHandler.attr(pxy), service.list(PageHandler.attr(pxy)));
    	  	pxy.execute(BlockHandler.attr(pxy), service.getMemberByName(PageHandler.attr(pxy)));
    	  	
    	  	DispatcherServlet.send(request, response);
    	  	
    	  	break;*/
      case Action.SEARCH:
			map=ParamsIterator.execute(request);
			
			cmd.setColumn("name");
			cmd.setSearch(String.valueOf(map.get("search")));
			pxy.setTheNumberOfRow(
					Integer.parseInt(service.count(cmd)));
			cmd=PageHandler.attr(pxy);
			cmd.setColumn("name");
			cmd.setSearch(String.valueOf(map.get("search")));
			cmd.setPageNumber(
					request.getParameter("pageNumber"));
			cmd.setStartRow(PageHandler.attr(pxy).getStartRow());
			cmd.setEndRow(PageHandler.attr(pxy).getEndRow());
			pxy.setPageNumber(
					Integer.parseInt(
					cmd.getPageNumber()));
			pxy.execute(BlockHandler.attr(pxy),
					service.MemberByName(cmd));
			DispatcherServlet.send(request, response);
			break; 
      case Action.UPDATE:
    	  map=ParamsIterator.execute(request);
    	  System.out.println("수정할  ID : "+map.get("id"));
    	  System.out.println("수정할  email : "+map.get("email"));
    	  cmd.setSearch(request.getParameter("id"));
    	 // service.modify(service.memberById(cmd));
    	  DispatcherServlet.send(request, response);
     	 
    	  break;
      case Action.DELETE:
    	  //service.remove(request.getParameter("id"));
    	  String path=request.getContextPath();
    	  
    	  response.sendRedirect(request.getContextPath()+"/member.do?action=list&page=member_list&pageNumber=1");
    	 /* DispatcherServlet.send(request, response);*/
     	 
    	  break;
      case Action.DETAIL:
    	  cmd.setSearch(request.getParameter("id"));
    	  request.setAttribute("student",service.memberById(cmd));
    	  
    	  DispatcherServlet.send(request, response);
     	 
    	  break;
    	  
      default:System.out.println("command fail.........");
         break;
      }
   }
}