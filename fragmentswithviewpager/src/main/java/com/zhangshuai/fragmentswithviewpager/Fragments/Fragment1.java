package com.zhangshuai.fragmentswithviewpager.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhangshuai.fragmentswithviewpager.R;
import com.zhangshuai.fragmentswithviewpager.adapters.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;
/**
 * 当fragment中使用ViewPager时，fragmentManager要选择getChildFragmentManager()，要不然切换的时候数据页面会消失
 * */
public class Fragment1 extends Fragment {
	private ViewPager viewPager;
	private List<BaseFragment>baseFragments;
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.layout_fragment1,container,false);
		viewPager=v.findViewById(R.id.fragment_viewpager);
		baseFragments=new ArrayList<BaseFragment>();
		baseFragments.add(new FragmentInner1());
		baseFragments.add(new FragmentInner2());
		baseFragments.add(new FragmentInner3());
		baseFragments.add(new FragmentInner4());

		MyPagerAdapter adapter=new MyPagerAdapter(getChildFragmentManager(),baseFragments);
		viewPager.setAdapter(adapter);

		return v;
	}
}
