package com.yidong.mypintu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class MyImageView extends ImageView {

	int startX,startY;//按下事件起始的x/y坐标
	int currentX,currentY;//移动事件当前xy坐标
	int topmargin,leftmargin;//起始的布局信息
	int dX,dY;//XY方向移动的距离
	int sizeX,sizeY;//获取图片的大小
	
	Context context;
	WindowManager wm;
    int screenWidth,screenHeight;
    
    int currentImgX,currentImgY;//移动过程中IMg的上、左边距
    
    //用于判断双击事件
    long curTime,lastTime;
    onLocationChangedListener listener;
    
	
	RelativeLayout.LayoutParams params;
	
	public MyImageView(Context context) {
		super(context);
		this.context=context;
		init();
		params =(LayoutParams) this.getLayoutParams();
		
	}
	
	public MyImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		init();
		params =(LayoutParams) this.getLayoutParams();
	}
	
	public MyImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context=context;
		init();
		params =(LayoutParams) this.getLayoutParams();
	}
	
	void init(){
		//listener = (onLocationChangedListener)context;
		wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		//screenWidth = wm.getDefaultDisplay().getWidth();
        //screenHeight= wm.getDefaultDisplay().getHeight();
		DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        screenWidth=dm.widthPixels;
        screenHeight=dm.heightPixels;
        this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//这样写不好！第一下一定会触发单击事件
				curTime= System.currentTimeMillis();
				if(curTime -lastTime< 300)
				{//双击事件
					Toast.makeText(context, "double click", Toast.LENGTH_SHORT).show();
					listener.onDoubleClick();
				}else{
					//Toast.makeText(context, "one click", Toast.LENGTH_SHORT).show();
				}
				lastTime=curTime;
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(listener==null){
			//没有设置listener
			Toast.makeText(context, "没有设置listener",Toast.LENGTH_LONG).show();
			listener =new onLocationChangedListener() {
				
				@Override
				public void onDoubleClick() {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onChanged(int imgX, int img_sizeX, int imgY) {
					// TODO Auto-generated method stub
					
				}
			};
		}
		// TODO Auto-generated method stub
		if(params == null)
		{
			params =(LayoutParams) this.getLayoutParams();
			Log.i("tag", "params为空！");
			
			sizeX=this.getWidth();
			sizeY=this.getHeight();
		}
		
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			startX=(int) event.getRawX();
			startY=(int) event.getRawY();
			topmargin=params.topMargin;
			leftmargin=params.leftMargin;
			break;
		case MotionEvent.ACTION_MOVE:
			currentX=(int) event.getRawX();
			currentY=(int) event.getRawY();
			dX=currentX-startX;
			dY=currentY-startY;
			params.topMargin = topmargin+dY;
			params.leftMargin = leftmargin+dX;
			//限制topMargin
			if(params.topMargin> screenHeight-sizeY-150)
			{
				params.topMargin=screenHeight-sizeY-150;
			}else if(params.topMargin<0)
			{
				params.topMargin=0;
			}
			//限制leftMargin
			if(params.leftMargin> screenWidth-sizeX)
			{
				params.leftMargin=screenWidth-sizeX;
			}else if(params.leftMargin<0)
			{
				params.leftMargin=0;
			}
			this.setLayoutParams(params);
			currentImgX=params.leftMargin;
			currentImgY=params.topMargin;
			listener.onChanged(currentImgX, sizeX, currentImgY);
			break;
		case MotionEvent.ACTION_UP:
			//topmargin=params.topMargin;
			//leftmargin=params.leftMargin;
			currentImgX=params.leftMargin;
			currentImgY=params.topMargin;
			listener.onChanged(currentImgX, sizeX, currentImgY);
			performClick();
			break;
		}
		return true;
	}

	@Override
	public boolean performClick() {
		// TODO Auto-generated method stub
		super.performClick();
		return true;
	}
	
	interface onLocationChangedListener{
		void onChanged(int imgX,int img_sizeX,int imgY);
		
		void onDoubleClick();
	}
	
	public void setOnLocationChangedListener(onLocationChangedListener listener){
		this.listener=listener;
	}
	


}
