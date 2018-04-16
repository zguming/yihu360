package cn.net.dingwei.adpater;

import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class ViewPagerAdpater_lianxi extends PagerAdapter {
	private List<View> list;

	public ViewPagerAdpater_lianxi(List<View> list) {
		// TODO Auto-generated constructor stub
		this.list = list;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return  arg0 == arg1;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		/*System.out.println("删除Item 位置"+position);
		((ViewPager) container).removeView(list.get(position));*/
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		if(null==list.get(position).getTag()){
			((ViewPager) container).addView(list.get(position));
			list.get(position).setTag(1);
		}
		return list.get(position);
	}
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}
}
