package com.zhangshuai.mylistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


/**
 * listView嵌套Button时，抢焦点问题。将 android:focusable="false"就可以了。
 */
public class ActivityMain extends AppCompatActivity implements AdapterView.OnItemClickListener {

	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.main_lv);
		listView.setAdapter(new MyAdapter(this));
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Log.e("ActivityMain","当前点击的是"+position);

	}
}
