package com.leothon.cogito.Mvp.View.Activity.SearchActivity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Fragment.SearchPage.SearchArticleFragment;
import com.leothon.cogito.Mvp.View.Fragment.SearchPage.SearchClassFragment;
import com.leothon.cogito.Mvp.View.Fragment.SearchPage.SearchQAFragment;
import com.leothon.cogito.Mvp.View.Fragment.SearchPage.SearchUserFragment;
import com.leothon.cogito.R;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import butterknife.BindView;

public class SearchActivity extends BaseActivity {


    @BindView(R.id.search_viewpager)
    ViewPager searchViewPager;

    @BindView(R.id.search_content)
    MaterialEditText searchContent;


    @BindView(R.id.search_tab)
    TabLayout searchTab;

    private SearchClassFragment searchClassFragment;
    private SearchQAFragment searchQAFragment;
    private SearchUserFragment searchUserFragment;
    private SearchArticleFragment searchArticleFragment;
    private ArrayList<Fragment> fragments;

    private ArrayList<String> titleList;

    private FragmentPagerAdapter fragmentPagerAdapter;
    @Override
    public int initLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        fragments = new ArrayList<>();
        titleList = new ArrayList<>();
        searchClassFragment = SearchClassFragment.newInstance();
        searchQAFragment = SearchQAFragment.newInstance();
        searchUserFragment = SearchUserFragment.newInstance();
        searchArticleFragment = SearchArticleFragment.newInstance();
        titleList.add("课程");
        titleList.add("问题");
        titleList.add("用户");
        titleList.add("文章");
        fragments.add(searchClassFragment);
        fragments.add(searchQAFragment);
        fragments.add(searchUserFragment);
        fragments.add(searchArticleFragment);

        searchTab.addTab(searchTab.newTab().setText(titleList.get(0)));
        searchTab.addTab(searchTab.newTab().setText(titleList.get(1)));
        searchTab.addTab(searchTab.newTab().setText(titleList.get(2)));
        searchTab.addTab(searchTab.newTab().setText(titleList.get(3)));

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

        searchViewPager.setAdapter(fragmentPagerAdapter);
        searchTab.setupWithViewPager(searchViewPager);
        searchTab.setTabsFromPagerAdapter(fragmentPagerAdapter);

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
