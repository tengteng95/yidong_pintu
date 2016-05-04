package com.yidong.mypintu.application;

import com.yidong.mypintu.bean.User;

import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import de.greenrobot.daotest.DaoMaster;
import de.greenrobot.daotest.DaoSession;
import de.greenrobot.daotest.LoginTimeDao;
import de.greenrobot.daotest.DaoMaster.DevOpenHelper;

public class MyApplication extends Application{

	
	static DaoMaster daoMaster;
	static DaoSession daoSession;
	static LoginTimeDao loginTimeDao;
	static SQLiteDatabase db;
	static Cursor cursor;
	
	static User currentUser;
	@Override
	public void onCreate() {
		super.onCreate();
		
		DevOpenHelper helper = new DaoMaster.DevOpenHelper( this, "notes-db", null);
		db = helper.getWritableDatabase();
		
		daoMaster = new DaoMaster(db);
		daoSession = daoMaster.newSession();
		loginTimeDao = daoSession.getLoginTimeDao();
	}
	
	public static LoginTimeDao getLoginTimeDao()
	{
		return loginTimeDao;
	}
	
	public static void setCurrentUser(User user){
		currentUser=user;
	}
	
	public static User getCurrentUser()
	{
		return currentUser;
	}

}
