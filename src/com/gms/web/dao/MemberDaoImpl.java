package com.gms.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gms.web.command.Command;
import com.gms.web.constant.Database;
import com.gms.web.constant.SQL;
import com.gms.web.constant.Vendor;
import com.gms.web.domain.MajorBean;
import com.gms.web.domain.MemberBean;
import com.gms.web.domain.StudentBean;
import com.gms.web.factory.DatabaseFactory;


public class MemberDaoImpl implements MemberDao{
	public static MemberDaoImpl getInstance() {return new MemberDaoImpl();}
	Connection conn;
	private MemberDaoImpl() {
		conn=null;
	}
	
	@Override
	public String insert(Map<?, ?> map) {
		String rs="";
		MemberBean member=(MemberBean)map.get("member");
		
		//MajorBean major=(MajorBean)map.get("major");
		//트랜잭션 : 조인의 반대 개념, 하나의 값을 두개의 테이블에 나눠서 넣는것.
		@SuppressWarnings("unchecked")
		List<MajorBean> list=(List<MajorBean>)map.get("major");
		PreparedStatement pstmt=null;
		
		try {
			conn=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
					.getConnection();
			conn.setAutoCommit(false);
			        pstmt=conn.prepareStatement(SQL.MEMBER_INSER);
					pstmt.setString(1,member.getId());
					pstmt.setString(2, member.getName());
					pstmt.setString(3, member.getPw());
					pstmt.setString(4,member.getBirth());
					pstmt.setString(5, member.getPhone() );
					pstmt.setString(6, member.getGender());
					pstmt.setString(7, member.getEmail());
					pstmt.setString(8, member.getAddr());
					pstmt.setString(9, member.getMajor_id());
					pstmt.setString(10, member.getProfile());
					pstmt.executeUpdate();
					System.out.println();
					
					
					for(int i=0;i<list.size();i++){
						pstmt=conn.prepareStatement(SQL.MAJOR_INSERT);
					pstmt.setString(1, list.get(i).getMajor_id());
					pstmt.setString(2, list.get(i).getTitle());
					pstmt.setString(3, list.get(i).getId() );
					pstmt.setString(4, list.get(i).getSubj_Id());
					rs=String.valueOf(pstmt.executeUpdate());
					System.out.println("GETID  :"+list.get(i).getId()+"GETTITLE :"+list.get(i).getTitle()+"getMajor_id :"+list.get(i).getMajor_id()+"getSubj_Id :"+list.get(i).getSubj_Id());
					
					}
					
					System.out.println("**"+SQL.MAJOR_INSERT);
					System.out.println("*****rs"+rs);
					System.out.println("PSTMT : "+ pstmt);
					conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(conn!=null){
				try{
					conn.rollback();
				}catch(SQLException ex){
					ex.printStackTrace();
				}
			}
		}try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return rs;
	}

	@Override
	public List<?> selectAll(Command cmd) {
		List<StudentBean> list=new ArrayList<>();
	
		
		try {
			StudentBean stu=null;
			conn=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
					.getConnection();
			PreparedStatement pstmt=conn.prepareStatement(SQL.STUDENT_LIST);
			pstmt.setString(1, String.valueOf(cmd.getStartRow()));
			pstmt.setString(2, String.valueOf(cmd.getEndRow()));
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				stu=new StudentBean();
				stu.setNum(rs.getString(Database.NUM));
				stu.setId(rs.getString(Database.ID));
				stu.setName(rs.getString(Database.MEMBER_NAME));
				stu.setRegdate(rs.getString(Database.MEMBER_REGDATE));
				stu.setBirth(rs.getString(Database.MEMBER_BIRTH));
				stu.setEmail(rs.getString(Database.MEMBER_EMAIL));
				stu.setPhone(rs.getString(Database.MEMBER_PHONE));
				stu.setTitle(rs.getString(Database.TITLE));
				list.add(stu);
				System.out.println("DATABASE :"+list);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/*@Override
	public String count(Command cmd) {
		String count="";
		System.out.println("count("+cmd.getSearch()+")");
		System.out.println("count("+cmd.getColumn()+")");
		try {
			conn=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
					.getConnection();
			PreparedStatement pstmt=null;
			if(cmd.getSearch()==null){
				System.out.println("cmd.getSearch() is null");
				pstmt=conn.prepareStatement(SQL.STUDENT_COUNT);
				pstmt.setString(1, "%");
			}else{
				System.out.println("cmd.getSearch() is not null");

				pstmt=conn.prepareStatement(SQL.STUDENT_COUNT);
				pstmt.setString(1, "%"+cmd.getSearch()+"%");
			}
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getString("count");
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(count);
	}*/

	@Override
	public String count(Command cmd) {
		System.out.println("count("+cmd.getSearch()+")");
		System.out.println("count("+cmd.getColumn()+")");
		String count="";
		try {
			conn=DatabaseFactory
					.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
					.getConnection();
			PreparedStatement pstmt=null;
			if(cmd.getSearch()==null){
				System.out.println("cmd.getSearch() is null");
				pstmt=conn.prepareStatement(SQL.STUDENT_COUNT);
				pstmt.setString(1, "%");
			}else{
				System.out.println("cmd.getSearch() is not null");
				pstmt=conn.prepareStatement(SQL.STUDENT_COUNT);
				pstmt.setString(1, "%"+cmd.getSearch()+"%");
			}
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getString("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DAO count: "+count);
		return count;
	}
	
	
	@Override
	public StudentBean selectById(Command cmd) {
		List<StudentBean> list=new ArrayList<>();
		StudentBean stu=null;
		try {
			PreparedStatement pstmt=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
					.getConnection().prepareStatement(SQL.STUDENT_FINDBYID);
			pstmt.setString(1, cmd.getSearch());
			ResultSet rs=pstmt.executeQuery();
			
			while(rs.next()){
				stu=new StudentBean();
				stu.setNum(rs.getString(Database.NUM));
				stu.setId(rs.getString(Database.ID));
				stu.setName(rs.getString(Database.MEMBER_NAME));
				stu.setBirth(rs.getString(Database.MEMBER_BIRTH));
				stu.setRegdate(rs.getString(Database.MEMBER_REGDATE));
				stu.setPhone(rs.getString(Database.MEMBER_PHONE));
				stu.setEmail(rs.getString(Database.MEMBER_EMAIL));
				stu.setTitle(rs.getString(Database.TITLE));
				list.add(stu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stu;
	}

	/*@Override
	public List<MemberBean> selectByName(String name) {
List<MemberBean> list=new ArrayList<>();
		
		try {
			MemberBean member=null;
			PreparedStatement pstmt=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
					.getConnection().prepareStatement(SQL.MEMBER_FINDBYNAME);
			pstmt.setString(1, name);
			
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()){
				member=new MemberBean();
				member.setId(rs.getString(Database.MEMBER_ID));
				member.setName(rs.getString(Database.MEMBER_NAME));
				member.setPw(rs.getString(Database.MEMBER_PASSWORD));
				member.setRegdate(rs.getString(Database.MEMBER_REGDATE));
				member.setBirth(rs.getString(Database.MEMBER_BIRTH));
				list.add(member);
				System.out.println("회원이름"+member.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}*/

	@Override
	public String update(MemberBean member) {
		Command cmd=new Command();
		cmd.setSearch(member.getId());
		//MemberBean temp=selectById(cmd);
		MemberBean temp=MemberDaoImpl.getInstance().login(cmd);
		String id=(member.getId().equals(""))?temp.getId():member.getId();
		String pw=(member.getPw().equals(""))?temp.getPw():member.getPw();
		String rs="";
			try {
				PreparedStatement pstmt=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
						.getConnection().prepareStatement(SQL.MEMBER_UPDATE);
				pstmt.setString(1, pw);
				pstmt.setString(2, id);
				rs=String.valueOf(pstmt.executeUpdate());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
			System.out.println("rs"+rs);
		return rs;
	}

	@Override
	public String delete(Command cmd) {
		String rs="";
		try {
			PreparedStatement pstmt=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
					.getConnection().prepareStatement(SQL.MEMBER_DELETE);
			pstmt.setString(1, cmd.getSearch());
			rs=String.valueOf(pstmt.executeUpdate());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;
	}
	//=======================================================================================================================
	
	/*@Override
	public List<StudentBean> selectByName(Command cmd) {
		
		System.out.println("select getSearch	: "+cmd.getSearch());
		System.out.println("select getColumn	: "+cmd.getColumn());
		List<StudentBean> list=new ArrayList<>();
		try {
			
			PreparedStatement pstmt=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
					.getConnection().prepareStatement(SQL.STUDENT_FINDBYNAME);
			pstmt.setString(1, "%"+cmd.getSearch()+"%");
			
			
			ResultSet rs=pstmt.executeQuery();
			StudentBean stu=null;
			while(rs.next()){
				stu=new StudentBean();
				stu.setNum(rs.getString(Database.NUM));
				stu.setId(rs.getString(Database.ID));
				stu.setName(rs.getString(Database.MEMBER_NAME));
				stu.setRegdate(rs.getString(Database.MEMBER_REGDATE));
				stu.setBirth(rs.getString(Database.MEMBER_BIRTH));
				stu.setEmail(rs.getString(Database.MEMBER_EMAIL));
				stu.setPhone(rs.getString(Database.MEMBER_PHONE));
				stu.setTitle(rs.getString(Database.TITLE));
				list.add(stu);
				System.out.println("회원이름"+stu.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
*/
	
	
	@Override
	public List<StudentBean> selectByName(Command cmd) {
		System.out.println("selectByName("+cmd.getSearch()+")");
		System.out.println("selectByName("+cmd.getColumn()+")");
		List<StudentBean>list=new ArrayList<>();
		try {
			PreparedStatement pstmt=DatabaseFactory
					.createDatabase(Vendor.ORACLE, Database.USERNAME, Database.PASSWORD)
					.getConnection().prepareStatement(SQL.STUDENT_FINDBYNAME);
			
			pstmt.setString(1, "%"+cmd.getSearch()+"%");
			ResultSet rs=pstmt.executeQuery();
			StudentBean member=null;
			while(rs.next()){
				member=new StudentBean();
				member.setNum(rs.getString(Database.NUM));
				member.setId(rs.getString(Database.ID));
				member.setName(rs.getString(Database.MEMBER_NAME));
				member.setEmail(rs.getString(Database.MEMBER_EMAIL));
				member.setPhone(rs.getString(Database.MEMBER_PHONE));
				member.setRegdate(rs.getString(Database.MEMBER_REGDATE));
				member.setBirth(rs.getString(Database.MEMBER_BIRTH));
				member.setTitle(rs.getString(Database.TITLE));
				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return list;
	}	
	
	
	
	// =========================================================================================================
	@Override
	public MemberBean login(Command cmd) {
		
		MemberBean member=null;
		try {
			PreparedStatement pstmt=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
					.getConnection().prepareStatement(SQL.MEMBER_FINDBYID);
			pstmt.setString(1, cmd.getSearch());
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()){
				member=new MemberBean();
				member.setName(rs.getString(Database.MEMBER_NAME));
				member.setId(rs.getString(Database.MEMBER_ID));
				member.setPw(rs.getString(Database.MEMBER_PASSWORD));
				member.setBirth(rs.getString(Database.MEMBER_BIRTH));
				member.setRegdate(rs.getString(Database.MEMBER_REGDATE));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}

	/*@Override
	public String count(Command cmd) {
		String count="";
		try {
			ResultSet rs=DatabaseFactory.createDatabase(Vendor.ORACLE, Database.USERNAME,Database.PASSWORD)
					.getConnection().prepareStatement(SQL.STUDENT_COUNT).executeQuery();
		
			if(rs.next()){
				
					count=rs.getString("count");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(count);
	}*/
	
	
}
