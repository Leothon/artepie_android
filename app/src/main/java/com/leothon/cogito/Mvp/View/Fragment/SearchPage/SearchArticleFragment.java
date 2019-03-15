package com.leothon.cogito.Mvp.View.Fragment.SearchPage;

import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.leothon.cogito.Adapter.ArticleAdapter;
import com.leothon.cogito.Adapter.ArticleHisAdapter;
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
    private ArticleHisAdapter articleHisAdapter;
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
        articleHisAdapter = new ArticleHisAdapter(getMContext(),articles);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getMContext(),2, LinearLayout.VERTICAL,false);
        rvSearchArticle.setLayoutManager(gridLayoutManager);
        rvSearchArticle.setAdapter(articleHisAdapter);
        articleHisAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });

        articleHisAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
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
