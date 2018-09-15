package com.leothon.cogito.Mvp.View.Activity.ViewPagerActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Fragment.ViewPagerLeftFragment.ViewPagerLeftFragment;
import com.leothon.cogito.Mvp.View.Fragment.ViewPagerRightFragment.ViewPagerRightFragment;
import com.leothon.cogito.R;

import java.util.ArrayList;

import butterknife.BindView;
/**
 * created by leothon on 2018.8.6
 */
public class ViewPageActivity extends BaseActivity {

    private Fragment leftFragment;
    private Fragment rightFragment;

    private ArrayList<Fragment> fragments;

    private Bundle bundle;
    private Intent intent;
    private FragmentPagerAdapter fragmentPagerAdapter;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.video_free)
    TextView videoFree;
    @BindView(R.id.activity_free)
    TextView activityFree;
    @Override
    public int initLayout() {
        return R.layout.activity_view_page;
    }

    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarTitle(bundle.getString("title"));
        setToolbarSubTitle("");
        fragments = new ArrayList<>();
        leftFragment = ViewPagerLeftFragment.newInstance();
        rightFragment = ViewPagerRightFragment.newInstance();
        fragments.add(leftFragment);
        fragments.add(rightFragment);

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i == 0){
                    videoFree.setBackgroundResource(R.drawable.textviewbackgroundshow);
                    activityFree.setBackgroundResource(R.drawable.textviewbackground);
                    videoFree.setTextColor(getResources().getColor(R.color.white));
                    activityFree.setTextColor(getResources().getColor(R.color.fontColor));
                }else if (i == 1){
                    videoFree.setBackgroundResource(R.drawable.textviewbackground);
                    activityFree.setBackgroundResource(R.drawable.textviewbackgroundshow);
                    videoFree.setTextColor(getResources().getColor(R.color.fontColor));
                    activityFree.setTextColor(getResources().getColor(R.color.white));
                }else {
                    return;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        videoFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0);
            }
        });

        activityFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1);
            }
        });
    }

    @Override
    public void initData() {

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
