package com.yidong.mypintu;

import com.yidong.mypintu.view.GamePintuLayout;
import com.yidong.mypintu.view.GamePintuLayout.GamePintuListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener
{

	private GamePintuLayout mGamePintuLayout;
	private TextView mLevel ; 
	private TextView mTime;
	
	private View VIEW_SIGNUP,VIEW_SIGNIN,VIEW_GAME_MAIN;
	
	private final int F_VIEW_SIGNUP=0;
	private final int F_VIEW_SIGNIN=1;
	private final int F_VIEW_GAME_MAIN=2;
	private int currentView=F_VIEW_SIGNIN;
	
	private LayoutInflater inflater;
	
	
	static public int[] mGamePintuLayout_location =new int[2];//获取拼图所在位置的Y坐标

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//隐藏标题栏（ActionBar)
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 隐藏状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
						WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		inflater=LayoutInflater.from(MainActivity.this);
//		VIEW_SIGNIN=inflater.inflate(R.layout.signin, null);
//		VIEW_SIGNUP=inflater.inflate(R.layout.signup, null);
//		VIEW_GAME_MAIN=inflater.inflate(R.layout.activity_main, null);
		
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

	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		
		mGamePintuLayout.pause();
	}
	
	@Override
	protected void onResume()
	{
		super.onResume();
		mGamePintuLayout.resume();
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}

}
