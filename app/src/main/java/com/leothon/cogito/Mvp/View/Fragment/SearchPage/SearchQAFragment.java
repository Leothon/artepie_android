package com.leothon.cogito.Mvp.View.Fragment.SearchPage;

import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.SearchQAAdapter;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.AskDetailActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.MyToast;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;

import butterknife.BindView;


public class SearchQAFragment extends BaseFragment {


    @BindView(R.id.rv_search_qa)
    RecyclerView rvSearchQA;
    private ArrayList<QAData> qaData;

    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static int THRESHOLD_OFFSET = 10;
    private SearchQAAdapter searchQAAdapter;
    public SearchQAFragment() {
    }

    public static SearchQAFragment newInstance(ArrayList<QAData> qaData) {
        SearchQAFragment fragment = new SearchQAFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("qadata", qaData);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_search_qa;
    }

    @Override
    protected void initView() {
        initAdapter();
    }





    private void initAdapter(){
        searchQAAdapter = new SearchQAAdapter(getMContext(),qaData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getMContext(),LinearLayoutManager.VERTICAL,false);
        rvSearchQA.setLayoutManager(linearLayoutManager);
        rvSearchQA.setAdapter(searchQAAdapter);
        searchQAAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });

        rvSearchQA.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean controlVisible = true;
            int scrollDistance = 0;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState) {
                    case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
                        //当屏幕停止滚动，加载图片
                        try {
                            if (getMContext() != null) Glide.with(getMContext()).resumeRequests();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
                        //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                        try {
                            if (getMContext() != null) Glide.with(getMContext()).pauseRequests();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under outside control.
                        //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                        try {
                            if (getMContext() != null) Glide.with(getMContext()).pauseRequests();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                if (GSYVideoManager.instance().getPlayPosition() >= 0){
                    int position = GSYVideoManager.instance().getPlayPosition();
                    if ( position < firstVisibleItem || position > lastVisibleItem){
                        if (GSYVideoManager.isFullState(getActivity())){
                            return;
                        }
                        GSYVideoManager.releaseAllVideos();
                        searchQAAdapter.notifyDataSetChanged();
                    }
                }

                if (controlVisible && scrollDistance > THRESHOLD_OFFSET){//手指上滑即Scroll向下滚动的时候，dy为正

                    controlVisible = false;
                    scrollDistance = 0;
                }else if (!controlVisible && scrollDistance < -THRESHOLD_OFFSET){//手指下滑即Scroll向上滚动的时候，dy为负

                    controlVisible = true;
                    scrollDistance = 0;
                }

                //当scrollDistance累计到隐藏（显示)ToolBar之后，如果Scroll向下（向上）滚动，则停止对scrollDistance的累加
                //直到Scroll开始往反方向滚动，再次启动scrollDistance的累加
                if ((controlVisible && dy > 0) || (!controlVisible && dy < 0)){
                    scrollDistance += dy;
                }

            }
        });

        searchQAAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                if (qaData.get(position).getQaData() == null){
                    Bundle bundle = new Bundle();
                    bundle.putString("qaId",qaData.get(position).getQa_id());
                    IntentUtils.getInstence().intent(getMContext(),AskDetailActivity.class,bundle);
                }else {
                    if (qaData.get(position).getQaData().getQa_user_id() == null){
                        MyToast.getInstance(getMContext()).show("该内容已被删除",Toast.LENGTH_SHORT);
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString("qaId",qaData.get(position).getQa_id());
                        IntentUtils.getInstence().intent(getMContext(),AskDetailActivity.class,bundle);
                    }
                }


            }
        });

    }

    @Override
    protected void initData() {
        if (getArguments() != null){
            qaData = (ArrayList<QAData>)getArguments().getSerializable("qadata");
        }
    }




}
