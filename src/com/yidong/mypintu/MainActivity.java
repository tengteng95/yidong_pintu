package com.yidong.mypintu;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.yidong.mypintu.application.MyApplication;
import com.yidong.mypintu.bean.User;
import com.yidong.mypintu.services.SoundService;
import com.yidong.mypintu.utils.ActivityUtils;
import com.yidong.mypintu.view.GamePintuLayout;
import com.yidong.mypintu.view.GamePintuLayout.GamePintuListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.daotest.LoginTime;
import de.greenrobot.daotest.LoginTimeDao;
import de.greenrobot.daotest.LoginTimeDao.Properties;

public class MainActivity extends Activity 
{

	private GamePintuLayout mGamePintuLayout;
	private TextView mLevel ; 
	private TextView mTime;
	
	static public int[] mGamePintuLayout_location =new int[2];//获取拼图所在位置的Y坐标
	
	SoundService sound_service;
	User currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//隐藏标题栏（ActionBar)
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 隐藏状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
//		VIEW_SIGNIN=inflater.inflate(R.layout.signin, null);
//		VIEW_SIGNUP=inflater.inflate(R.layout.signup, null);
//		VIEW_GAME_MAIN=inflater.inflate(R.layout.activity_main, null);
		
		currentUser=MyApplication.getCurrentUser();
		
		setContentView(R.layout.activity_main);
		//setContentView(VIEW_SIGNIN);

		mTime = (TextView) findViewById(R.id.id_time);
		mLevel = (TextView) findViewById(R.id.id_level);
		
		mGamePintuLayout = (GamePintuLayout) findViewById(R.id.id_gamepintu);
		mGamePintuLayout.setTimeEnabled(true);
		
		
		mGamePintuLayout.getLocationOnScreen(mGamePintuLayout_location);
		
		mGamePintuLayout.setOnGamePintuListener(new GamePintuListener()
		{
			@Override
			public void timechanged(int currentTime)
			{
				mTime.setText(""+currentTime);
			}

			@Override
			public void nextLevel(final int nextLevel)
			{
				new AlertDialog.Builder(MainActivity.this)
						.setTitle("Game Info").setMessage("LEVEL UP !!!")
						.setPositiveButton("NEXT LEVEL", new OnClickListener()
						{
							@Override
							public void onClick(DialogInterface dialog,
									int which)
							{
								mGamePintuLayout.nextLevel();
								mLevel.setText(""+nextLevel);
							}
						}).show();
			}

			@Override
			public void gameover()
			{
				new AlertDialog.Builder(MainActivity.this)
				.setTitle("Game Info").setMessage("Game over !!!")
				.setPositiveButton("RESTART", new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog,
							int which)
					{
						mGamePintuLayout.restart();
					}
				}).setNegativeButton("QUIT",new OnClickListener()
				{
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						finish();
					}
				}).show();
			}
		});
		
		startSound();
		
		/**
		 * 每日首次登陆签到
		 */
		FirstLogin();
		


	}
	
	private void FirstLogin(){
		LoginTimeDao dao=MyApplication.getLoginTimeDao();
		
		QueryBuilder builder=dao.queryBuilder();
		Date now=new Date();
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(now);
		//可以根据需要设置时区
		//cal.setTimeZone(TimeZone.getDefault());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		//毫秒可根据系统需要清除或不清除
		cal.set(Calendar.MILLISECOND, 0);
		long startTime = cal.getTimeInMillis();
		//查询用户当天是否有登陆过，两个条件（用户名，登陆时间）
		
		if(currentUser==null){
			ActivityUtils.showToast(this, "您当前以游客模式登陆，暂不支持签到功能");
			return;
		}
		
		builder.where(Properties.Date.ge(startTime),
				Properties.Username.eq(currentUser.getUsername()));//也可以用now
		List notelist=builder.list();
		if(notelist.isEmpty()){
			//第一次登陆
			ActivityUtils.showToast(this, "这是您今天首次登陆，快来签到吧");
			//显示签到界面
			
			//将当日登陆信息存放到数据库中
			LoginTime time =new LoginTime(null, currentUser.getUsername(), new Date());
			dao.insert(time);
		}else{
			ActivityUtils.showToast(this, "您今天已经签过到啦，明天再来哦");
		}
		
	}
	

	
	private void startSound(){
		startService(new Intent(MainActivity.this,SoundService.class));
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		stopService(new Intent(MainActivity.this,SoundService.class));
		mGamePintuLayout.pause();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		startService(new Intent(MainActivity.this,SoundService.class));
		mGamePintuLayout.resume();
	}


}
