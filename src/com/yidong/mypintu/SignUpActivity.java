package com.yidong.mypintu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

//注册
public class SignUpActivity extends Activity implements OnClickListener{
	EditText ed_username,ed_psword,ed_email;
	Button btn_register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		
		ed_username=(EditText)findViewById(R.id.sign_up_username);
		ed_psword=(EditText)findViewById(R.id.sign_up_psword);
		ed_email=(EditText)findViewById(R.id.sign_up_email);
		
		btn_register=(Button)findViewById(R.id.sign_in_register);
		btn_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String usrname=ed_username.getText().toString();
		String psword=ed_psword.getText().toString();
		
		//处理与服务器端的通信
		
		startActivity(new Intent(SignUpActivity.this,MainActivity.class));
	}
}
