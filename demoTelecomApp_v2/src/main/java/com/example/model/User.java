package com.example.model;

import java.util.List;

public class User {
	private String user_login_name;
	private String name;
	private String text_number;
	private int administrator_privileges;
	private String user_dep_id;
	public String getUser_login_name() {
		return user_login_name;
	}
	public void setUser_login_name(String user_login_name) {
		this.user_login_name = user_login_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText_number() {
		return text_number;
	}
	public void setText_number(String text_number) {
		this.text_number = text_number;
	}
	public int getAdministrator_privileges() {
		return administrator_privileges;
	}
	public void setAdministrator_privileges(int administrator_privileges) {
		this.administrator_privileges = administrator_privileges;
	}
	public String getUser_dep_id() {
		return user_dep_id;
	}
	public void setUser_dep_id(String user_dep_id) {
		this.user_dep_id = user_dep_id;
	}
	
	
}
