package com.yidong.mypintu.utils;

import android.content.Context;
import android.widget.Toast;

public class ActivityUtils {
	public static void showToast(Context context,String s)
	{
		Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
	}
}
