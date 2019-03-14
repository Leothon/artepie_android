package com.leothon.cogito.Mvp.View.Fragment.SearchPage;

import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.leothon.cogito.Adapter.ArticleAdapter;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.ArticleActivity.ArticleActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;

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





}
