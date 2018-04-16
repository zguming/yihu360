package com.liantigou.read_question;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


public class Read_Question_parent_ViewPager extends ViewPager {


public Read_Question_parent_ViewPager(Context context, AttributeSet attrs) {

super(context, attrs);
// TODO Auto-generated constructor stub

}
    private int abc = 1;
    private float mLastMotionX;
    String TAG="@";
   
    private float firstDownX;
    private float firstDownY;
    private boolean flag=false;


   
    public boolean dispatchTouchEvent(MotionEvent ev) {
            // TODO Auto-generated method stub       
            try {
				final float x = ev.getX();
				switch (ev.getAction()) {
				case MotionEvent.ACTION_DOWN:
				        getParent().requestDisallowInterceptTouchEvent(true);
				        abc=1;
				        mLastMotionX=x;
				        break;
				case MotionEvent.ACTION_MOVE:
				        if (abc == 1) {
				               /* if (x - mLastMotionX > 5 && getCurrentItem() == 0) {
				                        abc = 0;
				                        getParent().requestDisallowInterceptTouchEvent(false);
				                }*/
				                if (x - mLastMotionX < -5 && getCurrentItem()  == getAdapter().getCount()-1) {
				                        abc = 0;
				                        getParent().requestDisallowInterceptTouchEvent(false);
				                }
				        }
				        break;
				case MotionEvent.ACTION_UP:
         /* case MotionEvent.ACTION_CANCEL:
					 getParent().requestDisallowInterceptTouchEvent(false);
				        break;*/
				}
				return super.dispatchTouchEvent(ev);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return false;
			}
            
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
            // TODO Auto-generated method stub
            return super.onInterceptTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
            // TODO Auto-generated method stub
            return super.onTouchEvent(event);
    }
}