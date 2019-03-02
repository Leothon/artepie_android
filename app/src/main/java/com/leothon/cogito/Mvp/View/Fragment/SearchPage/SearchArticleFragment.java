package com.leothon.cogito.Mvp.View.Fragment.SearchPage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leothon.cogito.Adapter.ArticleAdapter;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Message.EventArticle;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.ArticleActivity.ArticleActivity;
import com.leothon.cogito.Mvp.View.Activity.SearchActivity.SearchActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.MyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;


public class SearchArticleFragment extends BaseFragment {

    @BindView(R.id.rv_search_article)
    RecyclerView rvSearchArticle;
    private ArticleAdapter articleAdapter;
    private ArrayList<Article> articles;

    public SearchArticleFragment() {
    }


    public static SearchArticleFragment newInstance(ArrayList<Article> articles) {
        SearchArticleFragment fragment = new SearchArticleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("article", articles);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_search_article;
    }

    @Override
    protected void initView() {

        initAdapter();

    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void Event(EventArticle articles){
//
//        this.articles = articles.getArticles();
//        initAdapter();
//
//    }






    private void initAdapter(){
        articleAdapter = new ArticleAdapter(getMContext(),articles);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rvSearchArticle.setLayoutManager(staggeredGridLayoutManager);
        rvSearchArticle.setAdapter(articleAdapter);
        articleAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });

        articleAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("articleId",articles.get(position).getArticleId());
                IntentUtils.getInstence().intent(getMContext(),ArticleActivity.class,bundle);
            }
        });
    }
    @Override
    protected void initData() {
        if (getArguments() != null){
            articles = (ArrayList<Article>)getArguments().getSerializable("article");
        }
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
