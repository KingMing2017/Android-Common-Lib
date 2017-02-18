package com.king.android.common.widget.indicator;

import com.king.android.common.widget.indicator.Indicator.OnItemSelectedListener;

import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

/**
 * @author xiao_ming
 *
 */
public class IndicatorViewPager {

	private Indicator mIndicator = null;
	private ViewPager mViewPager = null;
	
	public IndicatorViewPager(Indicator indicator, ViewPager viewPager){
		this.mIndicator = indicator;
		this.mIndicator.setOnItemSelectedListener(mOnItemSelectedListener);
		this.mViewPager = viewPager;
		this.mViewPager.addOnPageChangeListener(onPageChangeListener);
	}
	
	public int getTabIndex(){
		return mIndicator.getTabIndex();
	}
	
	// µ×À¸Footerµã»÷ÊÂ¼þ¼àÌý
	private OnItemSelectedListener mOnItemSelectedListener = new OnItemSelectedListener() {

		@Override
		public void onItemSelectedListener(int currentTabItem) {
			// TODO Auto-generated method stub
			mViewPager.setCurrentItem(currentTabItem);
			if (mOnIndicatorPageChangeListener != null)
				mOnIndicatorPageChangeListener.onIndicatorPageChangeListener(currentTabItem, mIndicator.getTabName());
		}

	};
	
	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener(){

		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			mIndicator.selectTab(arg0);
		}
		
	};
	
	private OnIndicatorPageChangeListener mOnIndicatorPageChangeListener = null;
	public void setOnIndicatorPageChangeListener(OnIndicatorPageChangeListener listener){
		this.mOnIndicatorPageChangeListener = listener;
	}
	public interface OnIndicatorPageChangeListener {
		public void onIndicatorPageChangeListener(int position, String tabName);
	}
}
