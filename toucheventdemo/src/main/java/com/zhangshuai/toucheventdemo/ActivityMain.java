package com.zhangshuai.toucheventdemo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ActivityMain extends AppCompatActivity implements MyVIew.MyOnClickListener{

	public static final String className= "ActivityMain";


	private MyVIew my;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		my = (MyVIew) findViewById(R.id.MyView);
		my.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		Log.e(className,"自定义View被触发了");
	}
}
