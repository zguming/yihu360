package cn.net.dingwei.ui;


import uk.co.senab.photoview.PhotoView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import cn.net.dingwei.Bean.Get_guide_msg_listBean;
import cn.net.dingwei.myView.HackyViewPager;
import cn.net.dingwei.myView.TasksCompletedView_jindu;
import cn.net.dingwei.util.LoadImageViewUtil;
import cn.net.dingwei.util.MyFlg;
import cn.net.tmjy.mtiku.futures.R;

public class Image_ViewPager_Activity extends Activity {

	private HackyViewPager viewpager;
	private static Button btPhoto;
	private static PhotoView photoView;
	private SamplePagerAdapter mSamplePagerAdapter;

	private Get_guide_msg_listBean.images[] images;
	private Get_guide_msg_listBean.msglist msglist;
	private int index;

	private ViewHolder viewHolder;
	private TextView title_index;
	//假数据
	//private Get_guide_msg_listBean.images[] images_test = new Get_guide_msg_listBean.images[6];
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyFlg.all_activitys.add(this);
		setContentView(R.layout.activity_image_viewpager);
		viewpager=(HackyViewPager)findViewById(R.id.viewpager);
		//设置每个Page的间距
		viewpager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.margin_10dip));
		title_index =(TextView)findViewById(R.id.title_index);
		btPhoto = new Button(this);
		initData();

	}
	private void initData() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		msglist = (cn.net.dingwei.Bean.Get_guide_msg_listBean.msglist) intent.getSerializableExtra("msglist");
		index = intent.getIntExtra("index", 0);
		if(null==msglist){//没有数据
			finish();
		}else if(null==msglist.getImages()){
			finish();
		}else{
			images = msglist.getImages();
		}


		//设置假数据
		/*String thumb_url = images[0].getThumb_url();
		images =   new Get_guide_msg_listBean.images[4];
		
		Get_guide_msg_listBean.images image1 = new Get_guide_msg_listBean().new images();
		image1.setImage_url("http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fe.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2Fa686c9177f3e6709ad41255c3fc79f3df9dc55cb.jpg");
		image1.setThumb_url(thumb_url);
		images[0]=image1;
		
		Get_guide_msg_listBean.images image2 = new Get_guide_msg_listBean().new images();
		image2.setImage_url("http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fwww.bz55.com%2Fuploads%2Fallimg%2F130708%2F1-130FPR037-50.jpg");
		image2.setThumb_url(thumb_url);
		images[1]=image2;
		
		Get_guide_msg_listBean.images image3 = new Get_guide_msg_listBean().new images();
		image3.setImage_url("http://ww2.sinaimg.cn/bmiddle/0064heaIjw1evdpozlpetj30db0eut9z.jpg");
		image3.setThumb_url(thumb_url);
		images[2]=image3;
		
		Get_guide_msg_listBean.images image4 = new Get_guide_msg_listBean().new images();
		image4.setImage_url("http://tp2.sinaimg.cn/1764222885/50/5714301852/1");
		image4.setThumb_url(thumb_url);
		images[3]=image4;*/
		//****************
		title_index.setText((index+1)+" / "+images.length);

		if (mSamplePagerAdapter == null) {
			viewHolder = new ViewHolder();
			mSamplePagerAdapter = new SamplePagerAdapter();
		}
		viewpager.setAdapter(mSamplePagerAdapter);
		viewpager.setCurrentItem(index);
		viewpager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				title_index.setText((arg0+1)+" / "+images.length);
				delayRecoverHandler.sendEmptyMessageDelayed(0, 300);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	private Handler delayRecoverHandler = new Handler() {

		public void dispatchMessage(Message msg) {
			//photoView.setMinScale(1.0f);

			mSamplePagerAdapter.notifyDataSetChanged();
		};
	};
	public class SamplePagerAdapter extends PagerAdapter {
		private int mChildCount = 0;
		/*int[] sDrawables =new int[]{ R.drawable.cunguihua1, R.drawable.cunguihua2,
				R.drawable.cunguihua3, R.drawable.cunguihua4 };*/
		@Override
		public int getCount() {

			return images.length;
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {

			/*photoView = new PhotoView(container.getContext(), btPhoto);
			LoadImageViewUtil.setImageBitmap(photoView, images_test[position].getImage_url());

			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			

			return photoView;*/
			View view = setViewPagerItem(images[position]);
			((ViewPager) container).addView(view);
			return	view;
		}

		@Override
		public void notifyDataSetChanged() {
			mChildCount = getCount();
			super.notifyDataSetChanged();

		}

		@Override
		public int getItemPosition(Object object) {
			if (mChildCount > 0) {
				mChildCount--;
				return POSITION_NONE;
			}
			return super.getItemPosition(object);

		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}
	private View setViewPagerItem(Get_guide_msg_listBean.images images){
		View view = View.inflate(this, R.layout.gudie_viewpager_item, null);

		viewHolder.framelayout = (FrameLayout) view.findViewById(R.id.framelayout);

		//
		Button button = new Button(this);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				MyFlg.all_activitys.remove(Image_ViewPager_Activity.this);
				finish();
			}
		});
		viewHolder.photoView = new PhotoView(this,button);
		viewHolder.framelayout.addView(viewHolder.photoView, 0);

		viewHolder.jindu = (TasksCompletedView_jindu) view.findViewById(R.id.jindu);
		viewHolder.jindu.setProgress(0);
		LoadImageViewUtil.setImageBitmap_pergress(viewHolder.photoView, images.getImage_url(),images.getThumb_url(), viewHolder.jindu,Image_ViewPager_Activity.this);
		return view;
	}
	class ViewHolder{
		PhotoView photoView;
		TasksCompletedView_jindu jindu;
		FrameLayout framelayout;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK ) {
			overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
			MyFlg.all_activitys.remove(Image_ViewPager_Activity.this);
			finish();
			return false;
		}
		return false;
	}
}
