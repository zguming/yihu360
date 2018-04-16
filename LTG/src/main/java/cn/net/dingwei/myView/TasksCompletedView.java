package cn.net.dingwei.myView;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import cn.net.dingwei.util.MyApplication;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

/**
 * @author naiyu(http://snailws.com)
 * @version 1.0
 */
public class TasksCompletedView extends View {
	private MyApplication application;
	//画圆环的另外一半
	private Paint mPaint;
	// 画实心圆的画笔
	private Paint mCirclePaint;
	// 画圆环的画笔
	private Paint mRingPaint;
	// 画字体的画笔
	private Paint mTextPaint;
	// 圆形颜色
	private int mCircleColor;
	// 圆环颜色
	private int mRingColor;
	// 半径
	private float mRadius;
	// 圆环半径
	private float mRingRadius;
	// 圆环宽度
	private float mStrokeWidth;
	// 圆心x坐标
	private int mXCenter;
	// 圆心y坐标
	private int mYCenter;
	// 字的长度
	private float mTxtWidth;
	// 字的高度
	private float mTxtHeight;
	// 总进度
	private int mTotalProgress = 100;
	// 当前进度
	private int mProgress;
	private int pex;
	private Bitmap bitmap;//中间那张图片
	public TasksCompletedView(Context context, AttributeSet attrs) {
		super(context, attrs);
		application = MyApplication.myApplication;
		// 获取自定义的属性
		initAttrs(context, attrs);
		initVariable();
	}
	//设置重绘
	public void setOndraw(int mRadius){
		this.mRadius =mRadius;
		mRingRadius = mRadius + mStrokeWidth / 2;
		invalidate();
	}
	//设置重绘
	public void setOndrawBitmap(Drawable drawable){
		this.bitmap=loadBitmaps(drawable);

		invalidate();
	}
	private void initAttrs(Context context, AttributeSet attrs) {
		TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.TasksCompletedView, 0, 0);
		mRadius = typeArray.getDimension(R.styleable.TasksCompletedView_radius, 80);
		mStrokeWidth = typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidth, 10);
		mCircleColor = typeArray.getColor(R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
		mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xFFFFFFFF);

		mRingRadius = mRadius + mStrokeWidth / 2;
	}

	private void initVariable() {
		mCirclePaint = new Paint();
		mCirclePaint.setAntiAlias(true);
		mCirclePaint.setColor(mCircleColor);
		mCirclePaint.setStyle(Paint.Style.FILL);

		mRingPaint = new Paint();
		mRingPaint.setAntiAlias(true);
		mRingPaint.setColor(Color.WHITE);
		mRingPaint.setStyle(Paint.Style.STROKE);
		mRingPaint.setStrokeWidth(mStrokeWidth);

		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setStyle(Paint.Style.FILL);
		mTextPaint.setARGB(255, 255, 255, 255);
		mTextPaint.setTextSize(mRadius / 2);

		FontMetrics fm = mTextPaint.getFontMetrics();
		mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);

		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setColor(Color.parseColor("#4BAE99"));
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeWidth(3);
	}

	public Bitmap loadBitmaps(Drawable d){
		//pex=DensityUtil.DipToPixels(getContext(), (int) mRadius); //注意 这里填写的是圆的直径
		Bitmap bitmap = Bitmap.createBitmap((int)mRadius*2-10,(int)mRadius*2-10,Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		d.setBounds(0, 0,(int)mRadius*2-10, (int)mRadius*2-10);
		d.draw(canvas);
		return bitmap;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				float distance = (float)Math.sqrt(((mXCenter-event.getX())*(mXCenter-event.getX()) + (mYCenter-event.getY())*(mYCenter-event.getY())));
				break;

			default:
				break;
		}

		return super.onTouchEvent(event);
	}
	@Override
	protected void onDraw(Canvas canvas) {

		mXCenter = getWidth() / 2;
		mYCenter = getHeight() / 2;
		//画圆
		//canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
		//画图
		//Bitmap bitmap=loadBitmaps(getResources().getDrawable(R.drawable.test));
		if(null==bitmap){
		}else{
			canvas.drawBitmap(bitmap, mXCenter-mRadius+5, mYCenter-mRadius+5, mCirclePaint);
		}


		RectF oval = new RectF();
		oval.left = (mXCenter - mRingRadius-5);
		oval.top = (mYCenter - mRingRadius-5);
		oval.right = mRingRadius * 2 + (mXCenter - mRingRadius)+5;
		oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius)+5;
		canvas.drawArc(oval, -90, ((float)mProgress / mTotalProgress) * 360, false, mRingPaint); //
		if(mProgress>0){
			canvas.drawArc(oval, -90, ((float)mProgress / mTotalProgress) * 360-360, false, mPaint);
		}else{
			canvas.drawArc(oval, -90, -360, false, mPaint);
		}
		//
//			canvas.drawCircle(mXCenter, mYCenter, mRadius + mStrokeWidth / 2, mRingPaint);
		//String txt = mProgress + "%";  画文字
		//测量文字宽度
		//mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
		//canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 4, mTextPaint);
	}

	public void setProgress(int progress) {
		mProgress = progress;
//		invalidate();
		postInvalidate();
	}

	public float getmRadius() {
		return mRadius;
	}

	public void setmRadius(float mRadius) {
		this.mRadius = mRadius;
	}

	public float getmStrokeWidth() {
		return mStrokeWidth;
	}

	public void setmStrokeWidth(float mStrokeWidth) {
		this.mStrokeWidth = mStrokeWidth;
	}

}
