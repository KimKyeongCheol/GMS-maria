package com.gms.web.service;
import java.util.List;
import java.util.Map;

import com.gms.web.command.Command;
import com.gms.web.domain.MemberBean;
import com.gms.web.domain.StudentBean;



public interface MemberService {
	public String add(Map<String, Object> map);
	public List<?> list(Command cmd);
	public List<?> MemberByName(Command cmd);
	public StudentBean memberById(Command cmd);
	public String count(Command cmd);
	public String modify(MemberBean member);
	//public String modify(StudentBean member);
	public String remove(Command cmd);
	public Map<String, Object> login(MemberBean member);//패스워드를 은닉화해야하기 때문에 멤버빈의 있는 패스워드를 호출하는 방식으로 해야하기 때문에 파라미터값을 멤버빈으로 한다.
}
