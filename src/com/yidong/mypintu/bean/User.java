package com.yidong.mypintu.bean;

public class User {
	String username;
	//是否以游客形式登陆，若是，则username为null
	boolean youke;
	
	//匿名创建User
	public User()
	{
		youke=true;
		username=null;
	}
	
	public User(String username){
		this.username=username;
	}
	
	public void setUsername(String usString){
		this.username=usString;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public boolean isYouke(){
		return this.youke;
	}
	
	public void setYouke(boolean isYouke){
		this.youke=isYouke;
	}
}
