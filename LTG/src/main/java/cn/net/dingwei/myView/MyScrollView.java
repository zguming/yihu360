package cn.net.dingwei.myView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

	// 拖动的距离 size = 4 的意思 只允许拖动屏幕的1/4
	private static final int size = 4;
	private View inner;
	private float y;
	private Rect normal = new Rect();;


	// 滑动距离及坐标
	private float xDistance, yDistance, xLast, yLast;
	private int mTouchSlop;  //一个距离  如果小于  不触发效果  大于 触发移动
	private Activity activity;

	public MyScrollView(Context context) {
		super(context);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	@Override
	protected void onFinishInflate() {
		if (getChildCount() > 0) {
			inner = getChildAt(0);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		try {
			try {
				if(null!=activity){
					InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
					if(activity.getCurrentFocus()!=null && activity.getCurrentFocus().getWindowToken()!=null){
						manager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
					}
				}
				if (inner == null) {
					return super.onTouchEvent(ev);
				} else {
					commOnTouchEvent(ev);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		return super.onTouchEvent(ev);
	}

	public void commOnTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				y = ev.getY();
				break;
			case MotionEvent.ACTION_UP:
				if (isNeedAnimation()) {
					// Log.v("mlguitar", "will up and animation");
					animation();
				}
				break;
			case MotionEvent.ACTION_MOVE:
				final float preY = y;
				float nowY = ev.getY();
				/**
				 * size=4 表示 拖动的距离为屏幕的高度的1/4
				 */
				int deltaY = (int) (preY - nowY) / size;
				// 滚动
				// scrollBy(0, deltaY);

				y = nowY;
				// 当滚动到最上或者最下时就不会再滚动，这时移动布局
				if (isNeedMove()) {
					if (normal.isEmpty()) {
						// 保存正常的布局位置
						normal.set(inner.getLeft(), inner.getTop(),
								inner.getRight(), inner.getBottom());
						return;
					}
					int yy = inner.getTop() - deltaY;

					// 移动布局
					inner.layout(inner.getLeft(), yy, inner.getRight(),
							inner.getBottom() - deltaY);
				}
				break;
			default:
				break;
		}
	}

	// 开启动画移动

	public void animation() {
		// 开启移动动画
		TranslateAnimation ta = new TranslateAnimation(0, 0, inner.getTop(),
				normal.top);
		ta.setDuration(200);
		inner.startAnimation(ta);
		// 设置回到正常的布局位置
		inner.layout(normal.left, normal.top, normal.right, normal.bottom);
		normal.setEmpty();
	}

	// 是否需要开启动画
	public boolean isNeedAnimation() {
		return !normal.isEmpty();
	}

	// 是否需要移动布局
	public boolean isNeedMove() {
		int offset = inner.getMeasuredHeight() - getHeight();
		int scrollY = getScrollY();
		if (scrollY == 0 || scrollY == offset) {
			return true;
		}
		return false;
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}

		return super.onInterceptTouchEvent(ev);
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

}
