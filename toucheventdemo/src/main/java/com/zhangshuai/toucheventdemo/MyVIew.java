package com.zhangshuai.toucheventdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyVIew extends View {
	private MyOnClickListener onClickListener;

	public void setOnClickListener(MyOnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	public MyVIew(Context context) {
		super(context);
	}

	public MyVIew(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public MyVIew(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.setMeasuredDimension(300,300);
	}

	
	



	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		super.onDraw(canvas);
	}



	int startX,startY;//记录按下时候的起点
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()){
			case MotionEvent.ACTION_DOWN://手指按下时触发事件
				startX= (int) event.getX();
				startY= (int) event.getY();
				break;
			case MotionEvent.ACTION_MOVE://手指移动时 触发事件
				break;
			case MotionEvent.ACTION_UP://手指抬起时触发事件
				int EndX= (int) event.getX();
				int EndY= (int) event.getY();
				int differenceX =EndX-startX;
				int differenceY=EndY-startY;
				int absX=Math.abs(differenceX);
				int absY=Math.abs(differenceY);
				if (absX<=5&&absY<=5){
					//执行触发操作
					if (onClickListener!=null){
						onClickListener.onClick(this);
					}
				}
				break;
		}

		return super.onTouchEvent(event);
	}

	public interface MyOnClickListener{
		void onClick(View v);
	}
	
}
