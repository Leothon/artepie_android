package com.leothon.cogito.Mvp.View.Activity.SearchActivity;




import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.DTO.SearchResult;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Fragment.SearchPage.SearchArticleFragment;
import com.leothon.cogito.Mvp.View.Fragment.SearchPage.SearchClassFragment;
import com.leothon.cogito.Mvp.View.Fragment.SearchPage.SearchQAFragment;
import com.leothon.cogito.Mvp.View.Fragment.SearchPage.SearchUserFragment;
import com.leothon.cogito.R;
import com.leothon.cogito.View.MyToast;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements SearchContract.ISearchView {


    @BindView(R.id.search_viewpager)
    ViewPager searchViewPager;

    @BindView(R.id.search_content)
    MaterialEditText searchContent;

    @BindView(R.id.sear_back)
    ImageView searchBack;

    @BindView(R.id.search_tab)
    TabLayout searchTab;

    private SearchClassFragment searchClassFragment;
    private SearchQAFragment searchQAFragment;
    private SearchUserFragment searchUserFragment;
    private SearchArticleFragment searchArticleFragment;
    private ArrayList<Fragment> fragments;

    private ArrayList<String> titleList;

    private FragmentPagerAdapter fragmentPagerAdapter;

    private SearchPresenter searchPresenter;
    @Override
    public int initLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initData() {
        searchPresenter = new SearchPresenter(this);

    }
    @Override
    public void initView() {
        loadView();


        initPage();


        searchContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){

                    if (!searchContent.getText().toString().equals("")){
                        showLoadingAnim();

                        searchPresenter.sendSearchResult(searchContent.getText().toString(),activitysharedPreferencesUtils.getParams("token","").toString());
                    }else {
                        MyToast.getInstance(SearchActivity.this).show("请输入搜索的内容",Toast.LENGTH_SHORT);
                    }

                }
                return false;
            }
        });
    }
    @Override
    public void searchSuccess(SearchResult searchResult) {
        hideLoadingAnim();

        loadPage(searchResult);



    }




    @OnClick(R.id.sear_back)
    public void backToHome(View view){
        super.onBackPressed();
    }

    private void loadPage(SearchResult searchResult){
        fragments.clear();


        searchClassFragment = SearchClassFragment.newInstance(searchResult.getSelectClasses());

        searchQAFragment = SearchQAFragment.newInstance(searchResult.getQaData());

        searchUserFragment = SearchUserFragment.newInstance(searchResult.getUsers());

        searchArticleFragment = SearchArticleFragment.newInstance(searchResult.getArticles());

        searchTab.getTabAt(0).setText("课程(" + searchResult.getSelectClasses().size() + ")");
        searchTab.getTabAt(1).setText("问题(" + searchResult.getQaData().size() + ")");
        searchTab.getTabAt(2).setText("用户(" + searchResult.getUsers().size() + ")");
        searchTab.getTabAt(3).setText("文章(" + searchResult.getArticles().size() + ")");


        fragments.add(searchClassFragment);
        fragments.add(searchQAFragment);
        fragments.add(searchUserFragment);
        fragments.add(searchArticleFragment);




        fragmentPagerAdapter.notifyDataSetChanged();


    }

    private void initPage(){
        fragments = new ArrayList<>();


        searchClassFragment = SearchClassFragment.newInstance(new ArrayList<SelectClass>());

        searchQAFragment = SearchQAFragment.newInstance(new ArrayList<QAData>());

        searchUserFragment = SearchUserFragment.newInstance(new ArrayList<User>());

        searchArticleFragment = SearchArticleFragment.newInstance(new ArrayList<Article>());



        fragments.add(searchClassFragment);
        fragments.add(searchQAFragment);
        fragments.add(searchUserFragment);
        fragments.add(searchArticleFragment);


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

            @Override
            public long getItemId(int position) {
                return fragments.get(position).hashCode();
            }
            @Override
            public int getItemPosition(Object object) {
                return POSITION_NONE;
            }
        };



        searchViewPager.setAdapter(fragmentPagerAdapter);
        searchTab.setupWithViewPager(searchViewPager);
        searchTab.setTabsFromPagerAdapter(fragmentPagerAdapter);
    }

    private void loadView(){

        titleList = new ArrayList<>();

        titleList.add("课程");
        titleList.add("问题");
        titleList.add("用户");
        titleList.add("文章");


        searchTab.addTab(searchTab.newTab().setText(titleList.get(0)));
        searchTab.addTab(searchTab.newTab().setText(titleList.get(1)));
        searchTab.addTab(searchTab.newTab().setText(titleList.get(2)));
        searchTab.addTab(searchTab.newTab().setText(titleList.get(3)));


    }


    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        searchPresenter.onDestroy();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }




    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }




}
