package com.zhangshuai.fragmentswithviewpager.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhangshuai.fragmentswithviewpager.R;

public class BaseFragment extends Fragment {
private TextView textView;
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
				View v=	inflater.inflate(R.layout.layout_base_fragment,null);
				textView=v.findViewById(R.id.base_fragment_tv1);
				textView.setText(getClass().getName());
		return v;
	}
}
