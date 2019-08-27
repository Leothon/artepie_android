package com.leothon.cogito.Mvp.View.Activity.ArticleHisActivity;

import android.content.Intent;


import android.graphics.Rect;
import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.leothon.cogito.Adapter.ArticleAdapter;
import com.leothon.cogito.Adapter.ArticleHisAdapter;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Listener.loadMoreDataArticleListener;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.ArticleActivity.ArticleActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class ArticleHisActivity extends BaseActivity implements ArticleHisContract.IArticleHisView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_article_his)
    RecyclerView rvArticleHis;
    @BindView(R.id.swp_article_his)
    SwipeRefreshLayout swpArticleHis;
    private ArticleHisAdapter articleHisAdapter;
    private ArrayList<Article> articles;

    private UserEntity userEntity;

    private Intent intent;
    private Bundle bundle;

    private ArticleHisPresenter articleHisPresenter;

    @Override
    public int initLayout() {
        return R.layout.activity_article_his;
    }

    @Override
    public void initView() {
        setToolbarSubTitle("");
        if (bundle.get("userId").equals(userEntity.getUser_id())){
            setToolbarTitle("我发布的文章");


        }else {
            setToolbarTitle("他发布的文章");
        }

        swpArticleHis.setRefreshing(true);
        articleHisPresenter.getArticleHisData(bundle.getString("userId"));



    }

    @Override
    public void initData() {
        articleHisPresenter = new ArticleHisPresenter(this);
        swpArticleHis.setProgressViewOffset (false,100,300);
        swpArticleHis.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        String uuid = tokenValid.getUid();
        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
        intent = getIntent();
        bundle = intent.getExtras();
    }

    private void initAdapter(){
        swpArticleHis.setOnRefreshListener(this);
        articleHisAdapter = new ArticleHisAdapter(this,articles);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2, LinearLayout.VERTICAL,false);
        rvArticleHis.setLayoutManager(gridLayoutManager);
        rvArticleHis.setAdapter(articleHisAdapter);
        int space = getResources().getDimensionPixelSize(R.dimen._15dp);
        int divider = getResources().getDimensionPixelOffset(R.dimen._5dp);
        rvArticleHis.addItemDecoration(new SpaceItemDecoration(space,divider));
        rvArticleHis.addOnScrollListener(new loadMoreDataArticleListener(gridLayoutManager) {
            @Override
            public void onLoadMoreArticleData(int currentPage) {
                swpArticleHis.setRefreshing(true);
                articleHisPresenter.getArticleHisMoreData(currentPage * 15,bundle.getString("userId"));
            }
        });

        articleHisAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("articleId",articles.get(position).getArticleId());
                IntentUtils.getInstence().intent(ArticleHisActivity.this,ArticleActivity.class,bundle);
            }
        });

        articleHisAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        articleHisPresenter.onDestroy();
    }


    @Override
    public void loadArticleHisData(ArrayList<Article> articles) {
        if (swpArticleHis.isRefreshing()){
            swpArticleHis.setRefreshing(false);
        }
        this.articles = articles;
        initAdapter();
    }

    @Override
    public void loadArticleHisMoreData(ArrayList<Article> articles) {
        if (swpArticleHis.isRefreshing()){
            swpArticleHis.setRefreshing(false);
        }
        for (int i = 0;i < articles.size();i ++){
            this.articles.add(articles.get(i));
        }
        articleHisAdapter.notifyDataSetChanged();
    }

    @Override
    public void showInfo(String msg) {

    }

    @Override
    public void onRefresh() {
        articleHisPresenter.getArticleHisData(bundle.getString("userId"));
    }

    class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;
        private int divider;

        public SpaceItemDecoration(int space,int divider) {
            this.space = space;
            this.divider = divider;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % 2 != 0) {
                outRect.left = divider;
                outRect.right = space;
            }else {
                outRect.left = space;
                outRect.right = divider;
            }
        }


    }
}
