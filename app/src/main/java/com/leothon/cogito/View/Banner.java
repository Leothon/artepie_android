package com.leothon.cogito.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.R;

import java.util.List;

/**
 * created by leothon on 2018.7.26
 * banner的封装
 */
public class Banner extends RelativeLayout {

    private static final int RMP = LayoutParams.MATCH_PARENT;//设置为matchparent
    private static final int RWP = LayoutParams.WRAP_CONTENT;
    private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;
    private static final int WHAT_AUTO_PLAY = 1000;//自动轮播间隔时间

    //预先设置多种point显示位置
    public static final int CENTER = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;

    //point所在的容器
    private LinearLayout mPointRealContainerLl;

    private ViewPager mViewPager;

    //本地图片资源
    private List<Integer> mImages;
    //网络图片资源
    private List<String> mImageUrls;
    //是否是网络图片
    private boolean isImageUrl = false;
    //是否只有一张图片
    private boolean isOneImage = false;
    //是否可以自动播放
    private boolean isAutoPlay = true;
    //是否正在自动播放
    private boolean isAutoPlaying = false;
    //自动播放时间
    private int mAutoPlayTime = 5000;
    //当前页面位置
    private int currentPosition;
    //point位置
    private int pointPosition = CENTER;
    //point资源
    private int pointDrawableResId = R.drawable.selector_banner_point;
    //point背景
    private Drawable pointContainerBackgroundDrawable;
    //point容器布局规则
    private LayoutParams pointRealContainerLp;
    //指示语
    private TextView mTips;

    private List<String> mtipsDatas;

    private boolean pointIsVisible = true;

    private Handler autoPlayHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            currentPosition ++;
            mViewPager.setCurrentItem(currentPosition);
            autoPlayHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY,mAutoPlayTime);
        }
    };

    public Banner(Context context){
        this(context,null);
    }
    public Banner(Context context, AttributeSet attributeSet){
        this(context,attributeSet,0);
    }
    public Banner(Context context,AttributeSet attributeSet,int defStyleAttr){
        super(context,attributeSet,defStyleAttr);
        init(context,attributeSet);
    }

    public void init(Context context,AttributeSet attributeSet){
        TypedArray a = context.obtainStyledAttributes(attributeSet,R.styleable.banner);

        pointIsVisible = a.getBoolean(R.styleable.banner_points_visibility,true);
        pointPosition = a.getInt(R.styleable.banner_points_position,CENTER);
        pointContainerBackgroundDrawable = a.getDrawable(R.styleable.banner_points_container_background);
        a.recycle();
        setLayout(context);
    }

    private void setLayout(Context context){
        setOverScrollMode(OVER_SCROLL_NEVER);

        if (pointContainerBackgroundDrawable == null){
            pointContainerBackgroundDrawable = new ColorDrawable(Color.parseColor("#00aaaaaa"));
        }

        mViewPager = new ViewPager(context);
        addView(mViewPager,new LayoutParams(RMP,RMP));

        RelativeLayout pointContainerRl = new RelativeLayout(context);
        if (Build.VERSION.SDK_INT >= 16){
            pointContainerRl.setBackground(pointContainerBackgroundDrawable);
        }else {
            pointContainerRl.setBackgroundDrawable(pointContainerBackgroundDrawable);
        }

        pointContainerRl.setPadding(20,10,40,10);

        LayoutParams pointContainerLp = new LayoutParams(RMP,RWP);
        pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        addView(pointContainerRl,pointContainerLp);
        //设置指示器容器
        mPointRealContainerLl = new LinearLayout(context);
        mPointRealContainerLl.setOrientation(LinearLayout.HORIZONTAL);

        pointRealContainerLp = new LayoutParams(RWP, RWP);
        pointRealContainerLp.bottomMargin = CommonUtils.dip2px(getContext(),7);
        pointContainerRl.addView(mPointRealContainerLl, pointRealContainerLp);
        //设置指示器容器是否可见
        if (mPointRealContainerLl != null) {
            if (pointIsVisible) {
                mPointRealContainerLl.setVisibility(View.VISIBLE);
            } else {
                mPointRealContainerLl.setVisibility(View.GONE);
            }
        }
        //设置指示器布局位置
        if (pointPosition == CENTER) {
            pointRealContainerLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        } else if (pointPosition == LEFT) {
            pointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        } else if (pointPosition == RIGHT) {
            pointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
    }

    /**
     * 设置本地的图片
     * @param images
     */
    public void setImages(List<Integer> images){
        isImageUrl = false;
        this.mImages = images;
        if (images.size() <= 1){
            isOneImage = true;
        }
        initViewPager();
    }

    /**
     * 设置网络图片
     * @param urls
     */
    public void setImageUrl(List<String> urls){
        isImageUrl = true;
        this.mImageUrls = urls;
        if (urls.size() <= 1){
            isOneImage = true;
        }
        initViewPager();
    }

    /**
     * 设置point是否可见
     * @param isVisiable
     */
    public void setPointsIsVisible(boolean isVisiable){
        if (mPointRealContainerLl != null){
            if (isVisiable){
                mPointRealContainerLl.setVisibility(VISIBLE);
            }else {
                mPointRealContainerLl.setVisibility(GONE);
            }
        }
    }

    /**
     * 设置point位置
     * @param position
     */
    public void setPointPosition(int position){
        if (position == CENTER){
            pointRealContainerLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        }else if (position == LEFT){
            pointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        }else if (position == RIGHT) {
            pointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        }
    }


    /**
     * 初始化
     */
    private void initViewPager() {
        //当图片多于1张时添加指示点
        if (!isOneImage) {
            addPoints();
        }
        //设置ViewPager
        BannerPageAdapter adapter = new BannerPageAdapter();
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        //跳转到首页
        mViewPager.setCurrentItem(1, false);
        //当图片多于1张时开始轮播
        if (!isOneImage) {
            startAutoPlay();
        }
    }

    /**
     * 返回真实的位置
     * @param position
     * @return
     */
    private int toRealPosition(int position){
        int realPosition;
        if (isImageUrl){
            realPosition = (position - 1) % mImageUrls.size();
            if (realPosition < 0){
                realPosition += mImageUrls.size();
            }
        }else {
            realPosition = (position - 1) % mImages.size();
            if (realPosition < 0){
                realPosition += mImages.size();
            }
        }
        return realPosition;
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {

            if (onPositionListener != null) {
                onPositionListener.onPositionChange((position - 1) % mImageUrls.size());

            }
        }

        @Override
        public void onPageSelected(int position) {
            if (isImageUrl) {
                currentPosition = position % (mImageUrls.size() + 2);
            } else {
                currentPosition = position % (mImages.size() + 2);
            }
            switchToPoint(toRealPosition(currentPosition));
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                int current = mViewPager.getCurrentItem();
                int lastReal = mViewPager.getAdapter().getCount() - 2;
                if (current == 0) {
                    mViewPager.setCurrentItem(lastReal, false);
                } else if (current == lastReal + 1) {
                    mViewPager.setCurrentItem(1, false);
                }
            }
        }
    };

    private class BannerPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //当只有一张图片时返回1
            if (isOneImage) {
                return 1;
            }
            //当为网络图片，返回网页图片长度
            if (isImageUrl)
                return mImageUrls.size() + 2;
            //当为本地图片，返回本地图片长度
            return mImages.size() + 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            ImageView imageView = new ImageView(getContext());
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(toRealPosition(position));
                    }
                }
            });
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0,5,0,5);
            if (isImageUrl) {
                ImageLoader.loadImageViewThumbnailwitherror(getContext(),mImageUrls.get(toRealPosition(position)),imageView,R.drawable.defalutimg);
            } else {
                imageView.setImageResource(mImages.get(toRealPosition(position)));
            }
            container.addView(imageView);

            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            if (object != null)
                object = null;
        }
    }

    /**
     * 添加指示点
     */
    private void addPoints() {
        mPointRealContainerLl.removeAllViews();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LWC, LWC);
        lp.setMargins(10, 10, 10, 10);
        ImageView imageView;
        int length = isImageUrl ? mImageUrls.size() : mImages.size();
        for (int i = 0; i < length; i++) {
            imageView = new ImageView(getContext());
            imageView.setLayoutParams(lp);
            imageView.setImageResource(pointDrawableResId);
            mPointRealContainerLl.addView(imageView);
        }

        switchToPoint(0);
    }

    /**
     * 切换指示器
     *
     * @param currentPoint
     */
    private void switchToPoint(final int currentPoint) {
        for (int i = 0; i < mPointRealContainerLl.getChildCount(); i++) {
            mPointRealContainerLl.getChildAt(i).setEnabled(false);
        }
        mPointRealContainerLl.getChildAt(currentPoint).setEnabled(true);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (isAutoPlay && !isOneImage) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    stopAutoPlay();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_OUTSIDE:
                    startAutoPlay();
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 开始播放
     */
    public void startAutoPlay() {
        if (isAutoPlay && !isAutoPlaying) {
            isAutoPlaying = true;
            autoPlayHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPlayTime);
        }
    }

    /**
     * 停止播放
     */
    public void stopAutoPlay() {
        if (isAutoPlay && isAutoPlaying) {
            isAutoPlaying = false;
            autoPlayHandler.removeMessages(WHAT_AUTO_PLAY);
        }
    }

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener Listener) {
        this.mOnItemClickListener = Listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnPositionListener onPositionListener;

    public void setOnPositionListener(OnPositionListener onPositionListener) {
        this.onPositionListener = onPositionListener;
    }

    public interface OnPositionListener {
        void onPositionChange(int position);
    }
}
