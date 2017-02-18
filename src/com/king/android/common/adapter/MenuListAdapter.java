package com.king.android.common.adapter;

import com.king.android.common.R;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuListAdapter extends BaseAdapter {

	private Context mContext = null;
	private LayoutInflater mLayoutInflater = null;
	private String[] mData = null;
	private int mSelectedItem = 0;
	
	public MenuListAdapter(Context context, String[] data){
		this.mContext = context;
		this.mLayoutInflater = LayoutInflater.from(context);
		this.mData = data;
	}
	
	public void setSelectedItem(int i){
		mSelectedItem = i;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mData == null ? 0 : mData.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mData[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null){
			convertView = mLayoutInflater.inflate(R.layout.list_menu_item, parent, false);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.menu_item_txt);
			holder.imgv = (ImageView) convertView.findViewById(R.id.menu_item_imgv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.imgv.setBackgroundResource(R.drawable.ic_launcher);
		holder.tv.setText(mData[position]);
		if (position == mSelectedItem){
			convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.list_item_light));
		} else {
			convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.background_material_light));
		}
		
		return convertView;
	}
	
	class ViewHolder { 
		public TextView tv = null;
		public ImageView imgv = null;
	}

}
