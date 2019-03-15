package com.leothon.cogito.Listener;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class loadMoreDataArticleListener extends RecyclerView.OnScrollListener{
    private GridLayoutManager gridLayoutManager;
    private int totalItemcount;//已经加载出来的item数量
    private int previousTotal = 0;//储存上一个totalItemCount
    private int visibleItemCount;//屏幕可见item数量
    private int firstVisibleItem;//屏幕上可见的item数量
    private boolean loading = true;//判断是否上拉
    private int currentPage = 0;

    public loadMoreDataArticleListener(GridLayoutManager gridLayoutManager){
        this.gridLayoutManager = gridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemcount = gridLayoutManager.getItemCount();

        firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
        if(loading){
            if(totalItemcount > previousTotal){
                loading = false;
                previousTotal = totalItemcount;
            }
        }

        if(!loading && totalItemcount - visibleItemCount <= firstVisibleItem){
            currentPage ++;
            onLoadMoreArticleData(currentPage);
            loading = true;
        }

    }

    public abstract void onLoadMoreArticleData(int currentPage);
}
