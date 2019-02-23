package com.zhangshuai.myslidingmenudemo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;
import com.zhangshuai.myslidingmenudemo.R;

public class MySlidingMenu extends HorizontalScrollView {
	private LinearLayout mLinearLayout;
	private ViewGroup mMenu, mContent;//菜单，内容区域
	private int mScreenWidth, mPaddingRight;//屏幕的宽度，菜单距离屏幕右侧的距离（屏幕宽度-mPaddingRight=mScreenWidth）
	int mMenuWidth;//菜单的宽度
	boolean isFirst = false, isopen;
	private int indexCount;


	public MySlidingMenu(Context context) {
		this(context, null);
	}

	public MySlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);


	}

	/***
	 * 当设置了自定义属性时调用
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public MySlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		//		获取自定义属性
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MySlidingMenu, defStyleAttr, 0);
		indexCount = a.getIndexCount();
		for (int i = 0; i < indexCount; i++) {
			int index = a.getIndex(i);
			switch (index) {
				case R.styleable.MySlidingMenu_paddingRight:
					mPaddingRight = a.getDimensionPixelSize(index, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));
					break;
			}
		}
		a.recycle();//释放资源

		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics metrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(metrics);
		mScreenWidth = metrics.widthPixels;//获取屏幕的宽度
		//		mPaddingRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());

	}

	/**
	 * 这个方法可能会多次调用，定义一个boolean变量来控制它（isFrist）
	 *
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mLinearLayout = (LinearLayout) getChildAt(0);
		if (!isFirst) {
			mMenu = ((ViewGroup) mLinearLayout.getChildAt(0));
			mContent = ((ViewGroup) mLinearLayout.getChildAt(1));
			/**将屏幕的宽度减去距离右侧的宽度赋值给菜单的宽度*/
			mContent.getLayoutParams().width = mScreenWidth;
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mPaddingRight;
			isFirst = true;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		Log.e("onLayout", changed + "");
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			scrollTo(mMenuWidth, 0);
			isopen = false;
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
			case MotionEvent.ACTION_UP:
				int scrollX = getScrollX();//获取scrollView左上角的坐标
				if (scrollX >= mMenuWidth / 2) {
					smoothScrollTo(mMenuWidth, 0);
					isopen = false;
				} else {
					smoothScrollTo(0, 0);
					isopen = true;
				}
				return true;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		float scale = 1.0f * l / mMenuWidth;//滑动比例  1-0
		Log.e("滑动比例", scale + "--" + l);

		ViewHelper.setTranslationX(mMenu, (float) (l * 0.8f));
		/**区别1 ：内容区域越来越小，比例 1.0-0.7     0.7+0.3*scale
		 * 菜单区域越来越大，透明度越来越不透明 缩放 0.7-1  1-0.3*scale  透明度 0.6-1  1-0.4*scale
		 * */

		float contentScale = 0.7f + 0.3f * scale;
		float menuScale = 1.0f - 0.3f * scale;
		float menuAlpha = 1.0f - 0.4f * scale;
		//菜单区域的缩放
		ViewHelper.setScaleX(mMenu, menuScale);
		ViewHelper.setScaleY(mMenu, menuScale);
		ViewHelper.setAlpha(mMenu, menuAlpha);
		//内容区域的缩放

		ViewHelper.setPivotX(mContent, 0);
		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
		ViewHelper.setScaleX(mContent, contentScale);
		ViewHelper.setScaleY(mContent, contentScale);

		super.onScrollChanged(l, t, oldl, oldt);
	}

	/**
	 * 打开菜单
	 */

	public void open() {
		if (isopen)
			return;
		smoothScrollTo(0, 0);
		isopen = true;
	}


	/**
	 * 关闭菜单
	 */
	public void close() {
		if (!isopen)
			return;
		smoothScrollTo(mMenuWidth, 0);
		isopen = false;
	}

	/**
	 * 切换菜单显示状态
	 */
	public void toggle() {
		if (isopen) {
			close();
		} else {
			open();
		}
	}
}
