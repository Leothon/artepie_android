package com.leothon.cogito.Mvp.View.Fragment.MicClassPage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Fragment.MicClassFragment.Mic1Fragment;
import com.leothon.cogito.Mvp.View.Fragment.MicClassFragment.Mic2Fragment;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.7.29
 * 专栏页fragment
 */
public class MicClassFragment extends BaseFragment {



    @BindView(R.id.voice_bar_layout)
    AppBarLayout voiceBarLayout;
    @BindView(R.id.voice_bar)
    CardView voiceBar;

    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.toolbar_subtitle)
    TextView subtitle;

    @BindView(R.id.mic_class_tab)
    TabLayout micClassTab;

    @BindView(R.id.mic_class_viewpager)
    ViewPager micClassViewPager;

    private LayoutInflater layoutInflater;

    private List<String> titleList = new ArrayList<>();
    private Mic1Fragment fragmentMic1;
    private Mic2Fragment fragmentMic2;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private ArrayList<Fragment> fragments;

    private Animation showAnimation;
    private Animation hideAnimation;





    public MicClassFragment() { }

    /**
     * fragment的构造方法
     * @return
     */
    public static MicClassFragment newInstance() {
        MicClassFragment fragment = new MicClassFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_voice;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){

        }
    }

    @Override
    protected void initView() {
        title.setText("学院");
        voiceBarLayout.setPadding(0,CommonUtils.getStatusBarHeight(getMContext()),0,0);
        subtitle.setText("");
        voiceBar.setCardElevation(0.0f);
        showAnimation = AnimationUtils.loadAnimation(getMContext(),R.anim.top_view_in);
        hideAnimation = AnimationUtils.loadAnimation(getMContext(),R.anim.top_view_out);
    }



    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        fragmentMic1 = Mic1Fragment.newInstance();
        fragmentMic2 = Mic2Fragment.newInstance();
        titleList.add("艺条微课");
        titleList.add("艺条课堂");
        fragments.add(fragmentMic1);
        fragments.add(fragmentMic2);

        micClassTab.addTab(micClassTab.newTab().setText(titleList.get(0)));
        micClassTab.addTab(micClassTab.newTab().setText(titleList.get(1)));

        fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
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

        micClassViewPager.setAdapter(fragmentPagerAdapter);
        micClassTab.setupWithViewPager(micClassViewPager);
        //micClassTab.setTabsFromPagerAdapter(fragmentPagerAdapter);
    }


    public void showTab(){
        voiceBar.setVisibility(View.VISIBLE);
        voiceBar.startAnimation(showAnimation);
    }

    public void hideTab(){
        voiceBar.setVisibility(View.GONE);
        voiceBar.startAnimation(hideAnimation);
    }

    @Override
    public void hideLoading() {}

    @Override
    public void showMessage(@NonNull String message) {}

    @Override
    public void showLoading() {}


}
