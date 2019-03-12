package com.leothon.cogito.Mvp.View.Activity.ArticleHisActivity;

import android.content.Intent;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.leothon.cogito.Adapter.ArticleAdapter;
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

public class ArticleHisActivity extends BaseActivity implements ArticleHisContract.IArticleHisView,SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv_article_his)
    RecyclerView rvArticleHis;
    @BindView(R.id.swp_article_his)
    SwipeRefreshLayout swpArticleHis;
    private ArticleAdapter articleAdapter;
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
        articleAdapter = new ArticleAdapter(this,articles);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvArticleHis.setLayoutManager(staggeredGridLayoutManager);
        rvArticleHis.setAdapter(articleAdapter);



        rvArticleHis.addOnScrollListener(new loadMoreDataArticleListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMoreArticleData(int currentPage) {
                swpArticleHis.setRefreshing(true);
                articleHisPresenter.getArticleHisMoreData(currentPage * 15,bundle.getString("userId"));
            }
        });

        articleAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("articleId",articles.get(position).getArticleId());
                IntentUtils.getInstence().intent(ArticleHisActivity.this,ArticleActivity.class,bundle);
            }
        });

        articleAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
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
        articleAdapter.notifyDataSetChanged();
    }

    @Override
    public void showInfo(String msg) {

    }

    @Override
    public void onRefresh() {
        articleHisPresenter.getArticleHisData(bundle.getString("userId"));
    }
}
