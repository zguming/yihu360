package cn.net.dingwei.myView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

public class MyViewPager extends ViewPager{
	 private float xDistance, yDistance, xLast, yLast;
		private int mTouchSlop;  
	public MyViewPager(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}
	 public MyViewPager(Context context, AttributeSet attrs) {  
	        super(context, attrs);  
	        mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	   }  
	 
	 @Override  
	    public boolean onInterceptTouchEvent(MotionEvent ev) {  
	        try {
				switch (ev.getAction()) {  
				    case MotionEvent.ACTION_DOWN:  
				        xDistance = yDistance = 0f;  
				        xLast = ev.getX();  
				        yLast = ev.getY();  
				        break;  
				    case MotionEvent.ACTION_MOVE:  
				        final float curX = ev.getX();  
				        final float curY = ev.getY();  
  
				        xDistance += Math.abs(curX - xLast);  
				        yDistance += Math.abs(curY - yLast);  
				        xLast = curX;  
				        yLast = curY;  
				        if(xDistance < yDistance && yDistance>=mTouchSlop){  
				            return false;  
				        }else{ 
				        	return true;
				        }
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
	        return super.onInterceptTouchEvent(ev);  
	 }
}
