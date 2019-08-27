package com.leothon.cogito.Mvp.View.Fragment.SearchPage;

import android.graphics.Rect;
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
import com.leothon.cogito.Mvp.View.Fragment.ArticleListPage.ArticleListFragment;
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

        int space = getResources().getDimensionPixelSize(R.dimen._15dp);
        int divider = getResources().getDimensionPixelOffset(R.dimen._5dp);
        rvSearchArticle.addItemDecoration(new SpaceItemDecoration(space,divider));
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
