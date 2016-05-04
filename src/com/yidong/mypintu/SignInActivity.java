package com.yidong.mypintu;

import com.yidong.mypintu.application.MyApplication;
import com.yidong.mypintu.bean.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

//登陆
public class SignInActivity extends Activity implements OnClickListener{
	EditText ed_username,ed_psword;
	Button btn_login;
	Button btn_register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signin);
		
		ed_username=(EditText)findViewById(R.id.sign_in_username);
		ed_psword=(EditText)findViewById(R.id.sign_in_psword);
		
		btn_login=(Button)findViewById(R.id.sign_in_login);
		btn_login.setOnClickListener(this);
		
		btn_register=(Button)findViewById(R.id.sign_in_register);
		btn_register.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		String usrname=ed_username.getText().toString();
		String psword=ed_psword.getText().toString();
		switch (v.getId()) {
		case R.id.sign_in_login:
			if(Utils.isStringEmpty(usrname) ||
					Utils.isStringEmpty(psword)){
				Utils.showToast(this, "用户名和密码不能为空");
				return ;
			}
			//处理与服务器端的通信
			
			//设置当前用户
			MyApplication.setCurrentUser(new User(usrname));
			//跳到主界面
			startActivity(new Intent(SignInActivity.this,MainActivity.class));
			
			//
			break;
		case R.id.sign_in_register:
			startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
			break;

		default:
			break;
		}
		
	}
}
