package com.king.android.common.widget.indicator;

import java.util.List;

import com.king.android.common.R;
import com.king.android.common.widget.badge.BGABadgeRadioButton;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author xiao_ming
 *
 */
public class FooterTabAdapter extends BaseAdapter {

	private LayoutInflater mLayoutInflater = null;
	private List<TabModel> mDataList = null;
	private int mPreTabItem = -1;
	
	public FooterTabAdapter(Context context){
		this.mLayoutInflater = LayoutInflater.from(context);
	}
	
	public FooterTabAdapter(Context context, List<TabModel> list){
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mDataList = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mDataList == null ? 0 : mDataList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mDataList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(final int arg0, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (view == null){
			view = mLayoutInflater.inflate(R.layout.footer_tab_item, arg2, false);
			holder = new ViewHolder();
			holder.tv = (BGABadgeRadioButton) view.findViewById(R.id.footer_tab_tv);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		
		TabModel tab = mDataList.get(arg0);
		holder.tv.setCompoundDrawablesWithIntrinsicBounds(0, tab.getDrawableId(), 0, 0);
		holder.tv.setText(tab.getName());
		holder.tv.setSelected(tab.isState()); 
		if (tab.isState()){
			holder.tv.setTextColor(tab.getSelectColor());
			holder.tv.setTextSize(tab.getSelectSize());
		} else {
			holder.tv.setTextColor(tab.getUnSelectColor());
			holder.tv.setTextSize(tab.getUnSelectSize());
		}
		
		if (tab.getTips() > 0){
			if (tab.getTips() <= 99){
				holder.tv.showTextBadge(String.valueOf(tab.getTips()));
			} else {
				holder.tv.showTextBadge(String.valueOf(99));
			}
		} else {
			holder.tv.hiddenBadge();
		}
	
		holder.tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mOnTabTouchListener != null)
					mOnTabTouchListener.onTabTouchListener(v, mPreTabItem, arg0);
				mPreTabItem = arg0;
			}
		});
		
        return view;
	}
	
	class ViewHolder { 
		public BGABadgeRadioButton tv = null;
	}
	
	private OnTabTouchListener mOnTabTouchListener = null;
	public void setOnTabTouchListener(OnTabTouchListener listener){
		this.mOnTabTouchListener = listener;
	}
	
	public interface OnTabTouchListener{
		public void onTabTouchListener(View view, int preTabItem, int currentTabItem);
	}
}
