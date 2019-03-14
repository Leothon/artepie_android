package com.leothon.cogito.Listener;


import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

public abstract class loadMoreDataArticleListener extends RecyclerView.OnScrollListener{
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private int totalItemcount;//已经加载出来的item数量
    private int previousTotal = 0;//储存上一个totalItemCount
    private int visibleItemCount;//屏幕可见item数量
    private int[] firstVisibleItem;//屏幕上可见的item数量
    private boolean loading = true;//判断是否上拉
    private int currentPage = 0;

    public loadMoreDataArticleListener(StaggeredGridLayoutManager staggeredGridLayoutManager){
        this.staggeredGridLayoutManager = staggeredGridLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount = recyclerView.getChildCount();
        totalItemcount = staggeredGridLayoutManager.getItemCount();

        firstVisibleItem = staggeredGridLayoutManager.findFirstVisibleItemPositions(null);
        if(loading){
            if(totalItemcount > previousTotal){
                loading = false;
                previousTotal = totalItemcount;
            }
        }
        if (firstVisibleItem != null && firstVisibleItem.length > 0){
            for (int position : firstVisibleItem) {

                if(!loading && totalItemcount - visibleItemCount <= position){
                    currentPage ++;
                    onLoadMoreArticleData(currentPage);
                    loading = true;
                }
//                if (position >= mDatas.size() - staggeredGridLayoutManager.getSpanCount() || !rv.canScrollVertically(1)) {//滑到底部了
//                    if (canLoadMore) {
//                        XLog.tag("bqt_red").i("加载更多 "+position+" "+rv.canScrollVertically(1));
//                        canLoadMore = false;
//                        doRequest();
//                    }
//                    break;
//                }
            }
        }

    }

    public abstract void onLoadMoreArticleData(int currentPage);
}
