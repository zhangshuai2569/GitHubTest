package com.zhangshuai.myslidingmenudemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhangshuai.myslidingmenudemo.view.MySlidingMenu;

public class ActivityMain extends AppCompatActivity {

	private MySlidingMenu mySlidingMenu;//自定义侧滑菜单

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mySlidingMenu = (MySlidingMenu) findViewById(R.id.MySlidingMenu_id);
		mySlidingMenu.close();
	}

	public void onClick(View view) {

		mySlidingMenu.toggle();
	}
}
