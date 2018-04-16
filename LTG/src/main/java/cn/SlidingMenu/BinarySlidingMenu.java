package cn.SlidingMenu;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import cn.net.tmjy.mtiku.futures.R;

/**
 * http://blog.csdn.net/lmj623565791
 *
 * @author zhy
 *
 */
public class BinarySlidingMenu extends HorizontalScrollView
{
	/**
	 * 菜单的宽度
	 */
	private int mMenuWidth;
	private int mHalfMenuWidth;

	private boolean isOperateRight;
	private boolean isOperateLeft;

	private boolean once;

	//private ViewGroup mLeftMenu;
	private ViewGroup mContent;
	private ViewGroup mRightMenu;
	private ViewGroup mWrapper;

	private boolean isLeftMenuOpen;
	private boolean isRightMenuOpen;
	//自己添加的
	private Boolean isCanScroll=false; //是否允许滑动
	// 滑动距离及坐标  
	private float xDistance, yDistance, xLast, yLast;
	private float Xleft,Ytop_bottom;
	private float action_downX,action_downY; //按下的X
	private int mTouchSlop;  //一个距离  如果小于  不触发效果  大于 触发移动
	/**
	 * 回调的接口
	 *
	 * @author zhy
	 *
	 */
	public interface OnMenuOpenListener
	{
		/**
		 *
		 * @param isOpen
		 *            true打开菜单，false关闭菜单
		 * @param flag
		 *            0 左侧， 1右侧
		 */
		void onMenuOpen(boolean isOpen, int flag);
	}

	public OnMenuOpenListener mOnMenuOpenListener;

	public void setOnMenuOpenListener(OnMenuOpenListener mOnMenuOpenListener)
	{
		this.mOnMenuOpenListener = mOnMenuOpenListener;
	}

	public BinarySlidingMenu(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);

	}

	/**
	 * 屏幕宽度
	 */
	private int mScreenWidth;

	/**
	 * dp 菜单距离屏幕的右边距
	 */
	private int mMenuRightPadding;

	public BinarySlidingMenu(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		mScreenWidth = ScreenUtils.getScreenWidth(context);

		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.BinarySlidingMenu, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++)
		{
			int attr = a.getIndex(i);
			switch (attr)
			{
				case R.styleable.BinarySlidingMenu_rightPadding:
					// 默认50
					mMenuRightPadding = a.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f,getResources().getDisplayMetrics()));// 默认为10DP
					break;
			}
		}
		a.recycle();
	}

	public BinarySlidingMenu(Context context)
	{
		this(context, null, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
		/**
		 * 显示的设置一个宽度
		 */
		if (!once)
		{

			mWrapper = (LinearLayout) getChildAt(0);
			//mLeftMenu = (ViewGroup) mWrapper.getChildAt(0);
			mContent = (ViewGroup) mWrapper.getChildAt(0);
			mRightMenu = (ViewGroup) mWrapper.getChildAt(1);

			mMenuWidth = mScreenWidth - mMenuRightPadding;
			mHalfMenuWidth = mMenuWidth / 2;
			//mLeftMenu.getLayoutParams().width = mMenuWidth;
			mContent.getLayoutParams().width = mScreenWidth;
			mRightMenu.getLayoutParams().width = mMenuWidth;

		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		super.onLayout(changed, l, t, r, b);
		if (changed)
		{
			// 将菜单隐藏
			//this.scrollTo(mMenuWidth, 0);
			once = true;
		}

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
				// >0  向左边   《0 右边
				if(isOperateRight){ // 如果打开了右侧菜单
					return true;
				}else if(Xleft<0&&isCanScroll){
					return true;
				}else{//默认不滑动
					return false;
				}

		}

		return super.onInterceptTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev)
	{
		if(isCanScroll){
			int action = ev.getAction();
			switch (action)
			{
				// Up时，进行判断，如果显示区域大于菜单宽度一半则完全显示，否则隐藏
				case MotionEvent.ACTION_UP:
					int scrollX = getScrollX();
					// 如果是操作左侧菜单
					if (isOperateLeft)
					{
						// 如果影藏的区域大于菜单一半，则影藏菜单
						if (scrollX > mHalfMenuWidth)
						{
							this.smoothScrollTo(mMenuWidth, 0);
							// 如果当前左侧菜单是开启状态，且mOnMenuOpenListener不为空，则回调关闭菜单
							if (isLeftMenuOpen && mOnMenuOpenListener != null)
							{
								// 第一个参数true：打开菜单，false：关闭菜单;第二个参数 0 代表左侧；1代表右侧
								mOnMenuOpenListener.onMenuOpen(false, 0);
							}
							isLeftMenuOpen = false;

						} else
						// 关闭左侧菜单
						{
							this.smoothScrollTo(0, 0);
							// 如果当前左侧菜单是关闭状态，且mOnMenuOpenListener不为空，则回调打开菜单
							if (!isLeftMenuOpen && mOnMenuOpenListener != null)
							{
								mOnMenuOpenListener.onMenuOpen(true, 0);
							}
							isLeftMenuOpen = true;
						}
					}

					// 操作右侧
					if (isOperateRight)
					{
						// 打开右侧侧滑菜单
						if (scrollX > mHalfMenuWidth + mMenuWidth)
						{
							this.smoothScrollTo(mMenuWidth + mMenuWidth, 0);
						} else
						// 关闭右侧侧滑菜单
						{
							this.smoothScrollTo(mMenuWidth, 0);
						}
					}

					return true;
			}
		}else{//不滑动
			return true;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt)
	{
		if(isCanScroll){
			super.onScrollChanged(l, t, oldl, oldt);
			if (l > mMenuWidth)
			{

				isOperateRight = true;
				isOperateLeft = false;
			} else
			{
				isOperateRight = false;
				isOperateLeft = true;
			}
		}
	}

	public void setmMenuRightPadding(int mMenuRightPadding) {
		this.mMenuRightPadding = mMenuRightPadding;
	}

	public void setIsCanScroll(Boolean isCanScroll) {
		this.isCanScroll = isCanScroll;
	}

}
