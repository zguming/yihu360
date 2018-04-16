package cn.net.dingwei.myView;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class ElasticScrollView extends ScrollView {  
    private View inner;  
    private float y;  
    private Rect normal = new Rect();  
    private boolean animationFinish = true;  
    private GestureDetector mGestureDetector;
    private int color=Color.WHITE;
 // 滑动距离及坐标  
    private float xDistance, yDistance, xLast, yLast;  
    private int mTouchSlop;  //一个距离  如果小于  不触发效果  大于 触发移动
    private Boolean isScroll=true;
    public ElasticScrollView(Context context) {  
        super(context);  
        init();
    }  
  
    public ElasticScrollView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        init();
    }  
    private void init() {
    	mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		mGestureDetector = new GestureDetector(getContext(),
				new YScrollDetector());
		setFadingEdgeLength(0);
	}
    @Override  
    protected void onFinishInflate() {  
        if (getChildCount() > 0) {  
            inner = getChildAt(0);  
        }  
    }  
      	/**
      	 * 与ViewPager 滑动冲突处理
      	 */
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
					       
					        if(xDistance > yDistance){  
					            return false;  
					        }else if(yDistance >mTouchSlop){ 
					        	return true;
					        }
					       
					}
					 return super.onInterceptTouchEvent(ev);  
				} catch (Exception e) {
					// TODO Auto-generated catch block
					 return false;  
				}  
    	  
    	       
	}
  
    @Override  
    public boolean onTouchEvent(MotionEvent ev) {  
        try {
			if (inner == null) {  
			    return super.onTouchEvent(ev);  
			} else {  
			    commOnTouchEvent(ev);  
			}
			return super.onTouchEvent(ev);  
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return false;  
		}  
        
    }  
  
    public void commOnTouchEvent(MotionEvent ev) {  
        if (animationFinish) {  
            int action = ev.getAction();  
            switch (action) {  
            case MotionEvent.ACTION_DOWN:  
                y = ev.getY();  
                super.onTouchEvent(ev);  
                break;  
            case MotionEvent.ACTION_UP:  
                y = 0;  
                if (isNeedAnimation()) {  
                    animation();  
                }  
                super.onTouchEvent(ev);  
                break;  
            case MotionEvent.ACTION_MOVE:  
                final float preY = y == 0 ? ev.getY() : y;  
                float nowY = ev.getY();  
                int deltaY = (int) (preY - nowY);  
                // 滚动  
//              scrollBy(0, deltaY);  
  
                y = nowY;  
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局  
                if (isNeedMove()) {  
                    if (normal.isEmpty()) {  
                        // 保存正常的布局位置  
                        normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());  
                    }  
                    // 移动布局  
                   
                	   if((inner.getTop() - deltaY / 2)>400){ //超过400PX就不向下滑动了
                		   inner.layout(inner.getLeft(), 400, inner.getRight(), inner.getBottom() - deltaY / 2);
                		   setBackgroundColor(Color.TRANSPARENT);
                		   //如果是由下往上滑  设背景颜色为白色  否则设为透明
                	   }else if(inner.getTop()<0){
                		   setBackgroundColor(color);
                		   if((inner.getBottom() - deltaY / 2) <0){
                			   inner.layout(inner.getLeft(), -inner.getMeasuredHeight()+1, inner.getRight(), 1);
                			   isScroll =false;
                		   }else{
                			   inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2, inner.getRight(), inner.getBottom() - deltaY / 2);
                		   }
                	   } else{
                		   setBackgroundColor(Color.TRANSPARENT);
                		   inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2, inner.getRight(), inner.getBottom() - deltaY / 2);
                	   }
                } else {  
                    super.onTouchEvent(ev);  
                }  
                break;  
            default:  
                break;  
            }  
        }  
    }  
  
    // 开启动画移动  
  
    public void animation() {  
        // 开启移动动画  
        TranslateAnimation ta = new TranslateAnimation(0, 0, 0, normal.top - inner.getTop());  
        ta.setDuration(200);  
        ta.setAnimationListener(new AnimationListener() {  
            @Override  
            public void onAnimationStart(Animation animation) {  
                animationFinish = false;  
  
            }  
  
            @Override  
            public void onAnimationRepeat(Animation animation) {  
  
            }  
  
            @Override  
            public void onAnimationEnd(Animation animation) {  
                inner.clearAnimation();  
                // 设置回到正常的布局位置  
                inner.layout(normal.left, normal.top, normal.right, normal.bottom);  
                normal.setEmpty();  
                animationFinish = true;
                isScroll = true;
            }  
        });  
        inner.startAnimation(ta);  
    }  
  
    // 是否需要开启动画  
    public boolean isNeedAnimation() {  
        return !normal.isEmpty();  
    }  
  
    // 是否需要移动布局  
    public boolean isNeedMove() {  
        int offset = inner.getMeasuredHeight() - getHeight();  
        int scrollY = getScrollY();  
        if(isScroll==true){
        	if (scrollY == 0 || scrollY == offset) {  
        		return true;  
        	}  
        }
        return false;  
    }  
    private class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			
			if (Math.abs(distanceY) >= Math.abs(distanceX)) {
				return true;
			}
			return false;
		}
	}
	public void setColor(int color) {
		this.color = color;
	}
    
}  