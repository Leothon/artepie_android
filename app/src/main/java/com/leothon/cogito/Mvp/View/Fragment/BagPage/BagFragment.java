package com.leothon.cogito.Mvp.View.Fragment.BagPage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leothon.cogito.Adapter.BagAdapter;
import com.leothon.cogito.Bean.BagBuy;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.R;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * created by leothon on 2018.7.29
 */
public class BagFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.toolbar_subtitle)
    TextView subtitle;
    @BindView(R.id.toolbar_title)
    TextView title;

    @BindView(R.id.swp_bag)
    SwipeRefreshLayout swpBag;
    @BindView(R.id.rv_bag)
    RecyclerView rvBag;

    private String[] cltitle = {"王宏伟教学精讲","声乐形体语言","蒋大为精讲","孟教授讲声音穿透","气息的位置"};
    private String[] clurl = {"http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fwww.chaohaojian.com%2Fupload%2Ffck%2FZ_20110907-165714_CG88.jpg&thumburl=http%3A%2F%2Fimg5.imgtn.bdimg.com%2Fit%2Fu%3D491319393%2C103263368%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fimg1.ph.126.net%2FNjwPu0BPJZw7nTmsOIFeQg%3D%3D%2F1122522207139939000.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1441393053%2C3218515343%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fvpic.video.qq.com%2F3388556%2Fu0326kofujb_ori_3.jpg&thumburl=http%3A%2F%2Fimg5.imgtn.bdimg.com%2Fit%2Fu%3D33290701%2C4103959810%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fimg.blog.voc.com.cn%2Fjpg%2F201104%2F03%2F15086_4ff6f01a446026e.jpg&thumburl=http%3A%2F%2Fimg5.imgtn.bdimg.com%2Fit%2Fu%3D3700842993%2C1086961686%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fwww.fhgy.cn%2FUploadFiles%2Fnews%2F2016%2F11%2F20161115161914.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D954581106%2C1477969549%26fm%3D27%26gp%3D0.jpg"};
    private String[] cldes = {"声带小结","舞台形体表现","民族腔体的共鸣腔体","美声腔体支点","解决气息忽高忽低"};
    private ArrayList<BagBuy> bagBuys;
    private ArrayList<BagBuy> recomments;

    private BagAdapter bagAdapter;
    public BagFragment() {}

    /**
     * 构造方法
     * @return
     */
    public static BagFragment newInstance() {
        BagFragment fragment = new BagFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_bag;
    }

    @Override
    protected void initView() {
        title.setText("小书包");
        subtitle.setText("");
        bagBuys = new ArrayList<>();
        recomments = new ArrayList<>();
        loadFalseData();
        initAdapter();

    }

    public void initAdapter(){
        swpBag.setOnRefreshListener(this);
        bagAdapter = new BagAdapter(bagBuys,recomments,getMContext());

        rvBag.setLayoutManager(new GridLayoutManager(getMContext(),2,GridLayoutManager.VERTICAL,false));
        rvBag.setAdapter(bagAdapter);

    }

    @Override
    public void onRefresh() {
        swpBag.setRefreshing(false);
    }

    public void loadFalseData(){

        for (int i = 0;i < 5;i++){
            BagBuy bagBuy = new BagBuy();
            bagBuy.setImgurl(clurl[i]);
            bagBuy.setTitle(cltitle[i]);
            bagBuy.setDescription(cldes[i]);
            bagBuy.setAuthor("戴雨农");
            bagBuys.add(bagBuy);
        }

        for (int j = 0;j < 10;j++){
            BagBuy bagBuy = new BagBuy();
            bagBuy.setImgurl("http://bpic.588ku.com/element_origin_min_pic/16/10/27/a83c050d95559070f6dea688be356b5c.jpg");
            bagBuy.setTitle("为您推荐的课程标题");
            bagBuy.setAuthor("毛人凤");
            recomments.add(bagBuy);
        }
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
