package cn.net.dingwei.adpater;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class TabViewPagerAdpater extends PagerAdapter {
	private List<View> list;
	private String[] _titles;
	public TabViewPagerAdpater(List<View> list, String[] titles) {
		// TODO Auto-generated constructor stub
		this.list = list;
		_titles=titles;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		return _titles[position];
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return  arg0 == arg1;
	}
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView(list.get(position));
	}
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		((ViewPager) container).addView(list.get(position));
		return list.get(position);
	}
	@Override
	public int getItemPosition(Object object) {
		// TODO Auto-generated method stub
		return POSITION_NONE;
	}

	public void set_titles(String[] _titles) {
		this._titles = _titles;
	}
}
