package com.zhangshuai.mylistview;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter1 extends BaseAdapter implements View.OnClickListener {

	private Context context;

	List<String> integers = new ArrayList<>();

	public MyAdapter1(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		return 100;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.layout_item1, null);
		}
		CheckBox cb = ((CheckBox) convertView.findViewById(R.id.cb1));
		cb.setTag(position);
		if (integers.contains(""+position)){
			cb.setChecked(true);
		}else{
			cb.setChecked(false);
		}
		/*
		匿名内部类 造成checkbox的状态不能及时变化
		cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked){
					integers.add(position+"");
				}else{
					integers.remove(position+"");//position 代表索引，所以将其集合改为字符串类型，否则会造成数组越界
				}
			}
		});*/

//		cb.setOnCheckedChangeListener(onCheckedChangeListener);
		cb.setOnClickListener(this);
		return convertView;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.cb1:
				int position= (int) v.getTag();
				if (!((CheckBox) v).isChecked()){
//					((CheckBox) v).setChecked(false);
					integers.remove(position);
				}else{
//					((CheckBox) v).setChecked(true);
					if (integers.contains(""+position)){
						return;
					}
					integers.add(""+position);

//					具体操作

				}
				break;
		}
	}

	/*
	此方法监听解决了复选，但使界面操作上频繁增删造成卡顿
	private CompoundButton.OnCheckedChangeListener onCheckedChangeListener=new CompoundButton.OnCheckedChangeListener(){

		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			int position = (int) buttonView.getTag();
			if (isChecked&&!integers.contains(""+position)){
				integers.add(position+"");
			}else{
				integers.remove(position+"");//position 代表索引，所以将其集合改为字符串类型，否则会造成数组越界
			}
		}
	};*/

}



