package com.zhangshuai.fragmentswithviewpager;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zhangshuai.fragmentswithviewpager.Fragments.Fragment1;
import com.zhangshuai.fragmentswithviewpager.Fragments.Fragment2;

/***
 * 在activity中通过使用getSupportFragmentManager()|getFragmentManager()来管理自己内部的一层fragment
 * 在fragment中 如果嵌套了fragment，例如用viewpager，要管理这些内部嵌套的Fragment要用getChildFragmentManager()(管理一层)
 *在fragment还有一种 getFragmentManager()谁管理的我，我获取谁的manager。同级操作
 */
public class ActivityMain extends AppCompatActivity {
	private Fragment1 fragment1;
	private Fragment2 fragment2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		fragment1=new Fragment1();
		fragment2=new Fragment2();

		getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
	}

	public void onClick(View view) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		switch (view.getId()){
			case R.id.bt1:
				fragmentTransaction.replace(R.id.container, fragment1).commit();
				break;
			case R.id.bt2:
				fragmentTransaction.replace(R.id.container, fragment2).commit();
				break;
		}
	}
}
