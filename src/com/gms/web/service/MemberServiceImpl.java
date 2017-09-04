package com.gms.web.service;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gms.web.command.Command;
import com.gms.web.constant.Database;
import com.gms.web.dao.MemberDao;
import com.gms.web.dao.MemberDaoImpl;
import com.gms.web.domain.MajorBean;
import com.gms.web.domain.MemberBean;
import com.gms.web.domain.StudentBean;




public class MemberServiceImpl implements MemberService{
	
	public static MemberServiceImpl getInstance() {
		
		return new MemberServiceImpl();
	}
	MemberDao dao;
	private MemberServiceImpl() {
		//dao=new MemberDaoImpl();
	}
	@Override
	public String add(Map<String, Object>  map) {//MemberDaoImpl.getInstance() 객체,   equals를 사용할 수 있는 이유는 리턴타입이 string객체로 리턴하였기때문에 사용가능.
		System.out.println("member service 진입");
		MemberBean m=(MemberBean) map.get("member");
		System.out.println("넘어온 회원의 정보 :"+ m);
		@SuppressWarnings("unchecked")
		List<MajorBean> list=(List<MajorBean>)map.get("major");
		System.out.println("넘어온 수강과목:"+list);
		
		/*MemberDaoImpl.getInstance().insert(map);*/
		return MemberDaoImpl.getInstance().insert(map)=="0"? "join":"main";
	}
	@Override
	public List<?> list(Command cmd) {
		return MemberDaoImpl.getInstance().selectAll(cmd);
	}
	@Override
	public String count(Command cmd) {
		return MemberDaoImpl.getInstance().count(cmd);
	}
	@Override
	public StudentBean memberById(Command cmd) {
		return MemberDaoImpl.getInstance().selectById(cmd);
	}
	/*@Override
	public List<MemberBean> getMemberByName(String search) {
		return MemberDaoImpl.getInstance().selectByName(search);
	}*/
	@Override
	public String modify(MemberBean param) {
	
	return (MemberDaoImpl.getInstance().update(param).equals("0"))?"회원정보수정 실패":"회원정보수정 성공";
	}
	@Override
	public String remove(Command cmd) {
		return (MemberDaoImpl.getInstance().delete(cmd).equals("0"))?"회원탈퇴실패":"회원탈퇴성공";
	}
	@Override
	public Map<String, Object> login(MemberBean member) {
		Map<String,Object> map = new HashMap<>();
		Command cmd= new Command();
		cmd.setSearch(member.getId());
		MemberBean m1=MemberDaoImpl.getInstance().login(cmd);
		String page=
		(m1!=null)?
				(member.getPw().equals(m1.getPw()))?
						"main":"login_fail":"join";
		map.put("page",page);
		map.put("user",m1);
		return map;
	}
	@Override
	public List<StudentBean> MemberByName(Command cmd) {
		System.out.println("######MemberByName : "+cmd.getSearch());
		
		return MemberDaoImpl.getInstance().selectByName(cmd);
	}
}
