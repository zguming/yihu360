package cn.net.dingwei.myView;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/9/29.
 */
    public class NoScorllViewPager extends ViewPager {

        private boolean isCanScroll = true;

        public NoScorllViewPager(Context context) {
            super(context);
        }

        public NoScorllViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public void setScanScroll(boolean isCanScroll){
            this.isCanScroll = isCanScroll;
        }


        @Override
        public void scrollTo(int x, int y){
            if (isCanScroll){
                super.scrollTo(x, y);
            }
        }

    }
