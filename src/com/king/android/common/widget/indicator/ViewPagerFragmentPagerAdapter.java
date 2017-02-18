package com.king.android.common.widget.indicator;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * @author xiao_ming
 *
 */
public class ViewPagerFragmentPagerAdapter extends FragmentPagerAdapter {

	private List<Fragment> mList = null;
	
	public ViewPagerFragmentPagerAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		// TODO Auto-generated constructor stub
		this.mList = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList == null ? 0 : mList.size();
	}

}
