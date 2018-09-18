package com.leothon.cogito.Mvp.View.Activity.MicClassActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Bean.DetailClass;
import com.leothon.cogito.Bean.DetailClassInfo;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Fragment.MicClassDetailFragment.ClassDetailFragment;
import com.leothon.cogito.Mvp.View.Fragment.MicClassDetailFragment.ClassListFragment;
import com.leothon.cogito.Mvp.View.Fragment.MicClassFragment.Mic1Fragment;
import com.leothon.cogito.Mvp.View.Fragment.MicClassFragment.Mic2Fragment;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.View.ObserveScrollView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class Mic2ClassActivity extends BaseActivity {

    @BindView(R.id.mic2_bar)
    CardView detailBar;
    @BindView(R.id.class_detail_img_2)
    RoundedImageView classDetailImg;
    @BindView(R.id.class_detail_title_2)
    TextView classDetailTitle;
    @BindView(R.id.class_detail_level_2)
    TextView classDetailLevel;
    @BindView(R.id.class_detail_author_2)
    TextView classDetailAuthor;
    @BindView(R.id.class_detail_time_2)
    TextView classDetailTime;
    @BindView(R.id.class_detail_price_2)
    TextView classDetailPrice;
    @BindView(R.id.class_detail_count_2)
    TextView classDetailCount;
    @BindView(R.id.class_detail_2)
    RelativeLayout classDetail;

    @BindView(R.id.detail_class_tab)
    TabLayout detailTab;
    @BindView(R.id.detail_class_viewpager)
    ViewPager detailViewPager;
    private Intent intent;
    private Bundle bundle;

    private List<String> titleList = new ArrayList<>();
    private ClassDetailFragment fragmentMic1;
    private ClassListFragment fragmentMic2;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private ArrayList<Fragment> fragments;


    private DetailClassInfo detailClassInfo;
    private ArrayList<DetailClass> detailClasses;
    private String[] titles = {"怎么形成声音","声音的高低","声音的腹腔共鸣","腹式呼吸","声音的高度","声音的音域","总结","尾声"};
    @Override
    public int initLayout() {
        return R.layout.activity_mic2_class;
    }

    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();

        ImageLoader.loadImageViewThumbnailwitherror(this,bundle.getString("img"),classDetailImg,R.drawable.defalutimg);
        classDetailAuthor.setText(bundle.getString("author"));
        classDetailTitle.setText(bundle.getString("title"));
        classDetailTime.setText(bundle.getString("time"));
        classDetailPrice.setText(bundle.getString("price"));
        classDetailCount.setText(bundle.getString("count"));

    }

    @Override
    public void initData() {
        fragments = new ArrayList<>();
        loadFragmentData();
        fragmentMic1 = ClassDetailFragment.newInstance(detailClassInfo);
        fragmentMic2 = ClassListFragment.newInstance(detailClasses);
        titleList.add("介绍");
        titleList.add("目录");
        fragments.add(fragmentMic1);
        fragments.add(fragmentMic2);

        detailTab.addTab(detailTab.newTab().setText(titleList.get(0)));
        detailTab.addTab(detailTab.newTab().setText(titleList.get(1)));

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titleList.get(position);
            }

        };

        detailViewPager.setAdapter(fragmentPagerAdapter);
        detailTab.setupWithViewPager(detailViewPager);
    }

    private void loadFragmentData(){
        detailClassInfo = new DetailClassInfo();
        detailClassInfo.setDescClass("村上春树如何成为爵士乐的死忠粉？为什么高档餐厅和咖啡厅都会用爵士乐作为背景音乐？我们对纷繁复杂的爵士乐知识进行梳理，深入浅出，让你轻松吸收爵士乐发展百年间的精华，成为品玩爵士乐的行家。");
        detailClassInfo.setDescAuthor("爵士乐演唱家、教育家。留美钢琴博士，爵士乐研究硕士。目前担任上海音乐学院音乐剧系教师,全国高等院校开设爵士乐演唱即兴入门课程第一人。");
        detailClassInfo.setFirstImg("http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fk2.jsqq.net%2Fuploads%2Fallimg%2F1705%2F7_170527143948_12.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D3788682560%2C2600075021%26fm%3D26%26gp%3D0.jpg");
        detailClassInfo.setFirstContent("很有意思，学到了很多知识，听到了平常很少接触到的爵士乐，只是个人希望对于以后的发展能有帮助，老师辛苦了");
        detailClassInfo.setFirstName("张老先生");
        detailClassInfo.setFirstLike("256");
        detailClassInfo.setSecondImg("http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fdiy.qqjay.com%2Fu2%2F2014%2F0908%2F17713423d9665106e82dfcea8016eb89.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2101624442%2C1726017127%26fm%3D26%26gp%3D0.jpg");
        detailClassInfo.setSecondContent("从很久以前了解到发展的历史，感谢老师");
        detailClassInfo.setSecondName("张牧之");
        detailClassInfo.setSecondLike("856");
        detailClassInfo.setThirdImg("http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fup.qqjia.com%2Fz%2F19%2Ftu21240_13.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1718977981%2C1270765561%26fm%3D27%26gp%3D0.jpg");
        detailClassInfo.setThirdContent("老师很有耐心，回答了很多问题，讲的很棒，感谢");
        detailClassInfo.setThirdName("班戈");
        detailClassInfo.setThirdLike("46");

        detailClasses = new ArrayList<>();
        for (int i = 0 ;i < 8;i ++){
            DetailClass detailClass = new DetailClass();
            detailClass.setTitle(titles[i]);
            detailClasses.add(detailClass);
        }

    }


    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public BaseModel initModel() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }
}
