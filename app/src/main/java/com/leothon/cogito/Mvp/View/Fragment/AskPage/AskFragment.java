package com.leothon.cogito.Mvp.View.Fragment.AskPage;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leothon.cogito.Adapter.AskAdapter;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Bean.Ask;
import com.leothon.cogito.Constants;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.AskActivity.AskActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.7.29
 * 问答页面的fragment
 */
public class AskFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.swp_ask)
    SwipeRefreshLayout swpAsk;
    @BindView(R.id.rv_ask)
    RecyclerView rvAsk;

    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.toolbar_subtitle)
    TextView subtitle;
    @BindView(R.id.float_btn)
    FloatingActionButton floatBtn;
    private AskAdapter askAdapter;
    private ArrayList<Ask> asks;

    public AskFragment() {}

    /**
     * 构造方法
     * @return
     */
    public static AskFragment newInstance() {
        AskFragment fragment = new AskFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }



    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_ask;
    }

    @Override
    protected void initView() {
        title.setText("问答互动");
        subtitle.setText("");
        asks = new ArrayList<>();
        loadfalsedata();
        initAdapter();

    }

    private void loadfalsedata(){

        for(int i = 0;i<20;i++){
            Ask ask = new Ask();
            ask.setUsericonurl("http://bpic.588ku.com/element_origin_min_pic/16/10/27/a83c050d95559070f6dea688be356b5c.jpg");
            ask.setUsername("张三丰");
            ask.setUserdes("我来自神之殿堂——武当山");
            ask.setContent("我叫张三丰，万万没想到，我没有学会乾坤大挪移，但我的太极闻名天下，也是蛮叼的");
            ask.setLikecount("122");
            ask.setCommentcount("56");
            ask.setVideourl("http://121.196.199.171:8080/myweb/cogito001.mp4");
            ask.setCoverurl("http://bpic.588ku.com/element_origin_min_pic/16/10/27/a83c050d95559070f6dea688be356b5c.jpg");

            asks.add(ask);
        }
    }

    public void initAdapter(){
        swpAsk.setOnRefreshListener(this);
        askAdapter = new AskAdapter(getMContext(),asks);
        final LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(getMContext(),LinearLayoutManager.VERTICAL,false);
        rvAsk.setLayoutManager(mlinearLayoutManager);
        rvAsk.setAdapter(askAdapter);
        rvAsk.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItem = mlinearLayoutManager.findLastVisibleItemPosition();
                int firstVisibleItem = mlinearLayoutManager.findFirstVisibleItemPosition();
                if (GSYVideoManager.instance().getPlayPosition() >= 0){
                    int position = GSYVideoManager.instance().getPlayPosition();
                    if (GSYVideoManager.instance().getPlayTag().equals(AskAdapter.TAG) && (position < firstVisibleItem || position > lastVisibleItem)){
                        if (GSYVideoManager.isFullState(getActivity())){
                            return;
                        }

                        GSYVideoManager.releaseAllVideos();
                        askAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }

    @OnClick(R.id.float_btn)
    public void addcontent(View view){
        //TODO 用户添加问题

        if (Constants.loginStatus == 0){
            CommonUtils.loadinglogin(getMContext());
        }else if (Constants.loginStatus == 1){
            IntentUtils.getInstence().intent(getMContext(), AskActivity.class);
        }


    }

//    public void loadMoreData(){
//        Ask ask = new Ask();
//        ask.setUsericonurl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1533725177427&di=720b0cd6306f55f54eda42a222ac9009&imgtype=0&src=http%3A%2F%2Fimg3.duitang.com%2Fuploads%2Fitem%2F201508%2F22%2F20150822124224_HQfc8.thumb.700_0.jpeg");
//        ask.setUsername("陈独秀");
//        ask.setContent("陈独秀新添加的数据");
//        ask.setUserdes("革命大师");
//        ask.setContenturl("http://bpic.588ku.com/element_origin_min_pic/16/10/27/a83c050d95559070f6dea688be356b5c.jpg");
//        ask.setLikecount("122");
//        ask.setCommentcount("56");
//        asks.add(0,ask);
//        askAdapter.notifyItemInserted(0);
//        askAdapter.notifyItemRangeChanged(0,asks.size());
//        rvAsk.scrollToPosition(0);
//    }




    @Override
    public void onRefresh() {

        swpAsk.setRefreshing(false);
    }
    @Override
    protected void initData() {

    }

    @Override
    public void hideLoading() {}

    @Override
    public void showMessage(@NonNull String message) {}

    @Override
    public void showLoading() {}


}
