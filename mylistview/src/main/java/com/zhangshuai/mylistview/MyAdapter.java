package com.zhangshuai.mylistview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class MyAdapter extends BaseAdapter  {

	private Context context;

	public MyAdapter(Context context) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView= LayoutInflater.from(context).inflate(R.layout.layout_item,null);
		}

		Button bt1= ((Button) convertView.findViewById(R.id.bt1));
		bt1.setTag(position);
		bt1.setText("当前"+position);
		bt1.setOnClickListener(new MyListener());
		return convertView;
	}


}
   class MyListener implements View.OnClickListener{

	@Override
	public void onClick(View v) {
		int tag = (int) v.getTag();
		Log.e("list",tag+"");
	}
}
