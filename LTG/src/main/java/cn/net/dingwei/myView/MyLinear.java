package cn.net.dingwei.myView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.Scroller;
/**
 * 为了实现答题实现的布局
 * @author Administrator
 *
 *
 */
public class MyLinear extends LinearLayout {
	private float mLastMotionX; // 记录滑动的X坐标
	private int screenWidth;
	private static final int SNAP_VELOCITY = 1000;
	private VelocityTracker mVelocityTracker; // 滑动效率
	private int index = 1;// 当前显示的是第几个Item
	private Scroller mScroller;
	private float action_downX,action_downY; //按下的X

	// 滑动距离及坐标  
	private float xDistance, yDistance, xLast, yLast;
	private float Xleft,Ytop_bottom;
	private int viewpager_now,viewpager_sum;
	private int mTouchSlop;  //一个距离  如果小于  不触发效果  大于 触发移动
	private int type=0;//0：正常    1:错题解析 2：整组解析

	//用于区别当前是哪个Activity
	private int classFlg =0;//0 AnsWerActiviy  1  Reading_QuestionActiviyt

	public MyLinear(Context context) {
		this(context, null, 0);
	}

	public MyLinear(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MyLinear(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mScroller = new Scroller(getContext());

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		View child0 = getChildAt(0);
		child0.measure(widthMeasureSpec, heightMeasureSpec);
		View child1 = getChildAt(1);
		child1.measure(widthMeasureSpec, heightMeasureSpec);
		View child2 = getChildAt(2);
		child2.measure(widthMeasureSpec, heightMeasureSpec);
		screenWidth = getWidth();
	}

	/** 事件拦截
	 * 与ViewPager 滑动冲突处理
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN:
				xDistance = yDistance = 0f;
				xLast = ev.getX();
				yLast = ev.getY();
				action_downX = ev.getX();
				action_downY = ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				final float curX = ev.getX();
				final float curY = ev.getY();
				xDistance += Math.abs(curX - xLast);
				yDistance += Math.abs(curY - yLast);
				Xleft = ev.getX() - xLast;
				Ytop_bottom = ev.getY()-yLast;
				//xLast = curX;
				//yLast = curY;
				if(Math.abs(Xleft)<mTouchSlop){
					return false;
				}
				if(Math.abs(Ytop_bottom)>Math.abs(Xleft)){
					return false;
				}else{
					if(Xleft>0 && viewpager_now!=viewpager_sum){
						return false;
					}else if(Xleft<0 && viewpager_now==viewpager_sum){
						return true;
					}
				}
				break;
		}

		return super.onInterceptTouchEvent(ev);
	}


	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		if(type!=0){//如果类型不是0 那么久不做处理
			return false;
		}

		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}
		mVelocityTracker.addMovement(ev);
		final int action = ev.getAction();
		final float x = ev.getX();
		float y = ev.getY();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				if (!mScroller.isFinished()) {
					mScroller.abortAnimation();
				}
				mLastMotionX = x;
				action_downX = ev.getX();
				action_downY = ev.getY();
				break;
			case MotionEvent.ACTION_MOVE:
				float scrollX = Math.abs((ev.getX()-action_downX));
				float scrollY = Math.abs((ev.getY()-action_downY));
				if(scrollX<scrollY){ //X  < Y  表示在进行Y轴移动  不滑动
					break;
				}

				final int deltaX = (int) (mLastMotionX - x);
				mLastMotionX = x;
				if (deltaX < 0) { // 往左边滑动意图
					//if (deltaX + getScrollX() >= 0 && index !=1) { // 当布局滑动距离大于0时
					if (deltaX + getScrollX() >= 0 ) { // 当布局滑动距离大于0时
						scrollBy(deltaX, 0);
					}
				} else if (deltaX > 0) { // 往右滑动
					final int availableToScroll = getChildAt(getChildCount() - 1).getRight() - getScrollX() - getWidth() * 3 / 2;
					if (availableToScroll > 0) {
						scrollBy(Math.min(availableToScroll, deltaX), 0);
					}
				}
				break;
			case MotionEvent.ACTION_UP:
				final VelocityTracker velocityTracker = mVelocityTracker;
				velocityTracker.computeCurrentVelocity(1000);
				int velocityX = (int) velocityTracker.getXVelocity();
				if (velocityX > SNAP_VELOCITY) { // 往左边移动
				/*if(index == 1){
					return false;
				}else{*/
					snapToScreen_left();
					//}

				} else if (velocityX < -SNAP_VELOCITY) {
					snapToScreen_right();
				} else {
					float action_upX = ev.getX();
					if (action_downX - action_upX < 0) {
						// 向左滑
						if (-(action_downX - action_upX) >= (screenWidth / 2)) {
							snapToScreen_left();
						} else {
							action_up_left();
						}

					} else if (action_downX - action_upX > 0) {
						// 向右滑
						if ((action_downX - action_upX) >= (screenWidth / 2)) {
							snapToScreen_right();
						} else {
							action_up_right();
						}

					}
					action_downX = 0;
				}
				break;
		}
		return true;
	}

	public void snapToScreen_right() {
		final int newX = getChildAt(0).getWidth();
		final int delta = newX - getScrollX();
		if (index == 1) {
			mScroller.startScroll(getScrollX(), 0, delta, 0,200);
			//由1到2  如果最后一题是多选  那么调用方法 (用户是选择了答案的)
			if(classFlg==0){
				//AnswerActivity.setLastIsDuoxuan();
			}else if(classFlg==1){

			}

		} else if (index == 2) {
			mScroller.startScroll(getScrollX(), 0, delta + newX / 2, 0,
					200);
		}
		invalidate();
		index = Math.min(3, index + 1);
	}
	protected void action_up_right() {
		final int newX = getChildAt(0).getWidth();
		if (index == 1) {
			mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0,200);
		} else if (index == 2) {
			mScroller.startScroll(getScrollX(), 0, -getScrollX() + newX, 0,200);
		}
		invalidate();
	}

	protected void action_up_left() {
		final int newX = getChildAt(0).getWidth();
		final int delta = newX - getScrollX();
		if (index == 3) {
			mScroller.startScroll(getScrollX(), 0, delta + newX / 2, 0,200);
		} else if (index == 2) {
			mScroller.startScroll(getScrollX(), 0, delta, 0,200);
		}else if (index==1){
			mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0,200);
		}
		invalidate();
	}

	protected void snapToScreen_left() {
		final int newX = getChildAt(0).getWidth();
		final int delta = newX - getScrollX();
		if (index == 3 ) {
			mScroller.startScroll(getScrollX(), 0, delta, 0,200);
		} else if (index == 2) {
			mScroller.startScroll(getScrollX(), 0, delta - newX, 0,200);
		}else if(index==1){
			mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0,200);
		}

		invalidate();
		index = Math.max(1, index - 1);
	}


	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}


	public void setViewpager_now(int viewpager_now) {
		this.viewpager_now = viewpager_now;
	}

	public void setViewpager_sum(int viewpager_sum) {
		this.viewpager_sum = viewpager_sum;
	}
	public void setType(int type) {
		this.type = type;
	}

	public int getClassFlg() {
		return classFlg;
	}
	/**
	 * 设置是哪个Activity调用的  0  answerActiviy  1 Reading_QuestionActivity
	 * @param classFlg
	 */
	public void setClassFlg(int classFlg) {
		this.classFlg = classFlg;
	}

}
