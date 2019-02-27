package com.zhangshuai.fragmentswithviewpager.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.zhangshuai.fragmentswithviewpager.Fragments.BaseFragment;

import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
	private List<BaseFragment>baseFragments;
	public MyPagerAdapter(FragmentManager fm,List<BaseFragment>baseFragments) {
		super(fm);
		this.baseFragments=baseFragments;
	}

	@Override
	public Fragment getItem(int position) {
		return baseFragments.get(position);
	}

	@Override
	public int getCount() {
		return baseFragments==null?0:baseFragments.size();
	}
}
