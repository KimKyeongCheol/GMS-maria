package com.gms.web.domain;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

import com.gms.web.constant.Database;



public class DatabaseBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private String driver,url,username,passowrd;
	private Connection connection;
	public DatabaseBean(String driver, String url, String username, String passowrd) {
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.passowrd = passowrd;
	}
	public Connection getConnection(){
		try {
			Class.forName(Database.ORACLE_DRIVER);
			connection=DriverManager.getConnection(Database.ORACLE_URL,Database.USERNAME,Database.PASSWORD);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}
	
	

	
	
	
}
