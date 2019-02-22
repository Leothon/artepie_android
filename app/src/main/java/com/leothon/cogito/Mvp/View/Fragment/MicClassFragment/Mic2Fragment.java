package com.leothon.cogito.Mvp.View.Fragment.MicClassFragment;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leothon.cogito.Adapter.Mic2ClassAdapter;
import com.leothon.cogito.Bean.MicClass;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Fragment.ArticleListPage.ArticleListFragment;
import com.leothon.cogito.R;

import java.util.ArrayList;

import butterknife.BindView;

public class Mic2Fragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public Mic2Fragment() {

    }


    public static Mic2Fragment newInstance() {
        Mic2Fragment fragment = new Mic2Fragment();
        return fragment;
    }
    private static int THRESHOLD_OFFSET = 10;
    private ArrayList<MicClass> micClasses;

    private String[] micImg =
            {"http://www.ddkjplus.com/resource/art11.jpg",
            "http://www.ddkjplus.com/resource/art12.jpg",
            "http://www.ddkjplus.com/resource/art13.jpg",
            "http://www.ddkjplus.com/resource/art14.jpg",
            "http://www.ddkjplus.com/resource/art15.jpg",
            "http://www.ddkjplus.com/resource/art16.jpg",
            "http://www.ddkjplus.com/resource/art17.jpg",
            "http://www.ddkjplus.com/resource/art18.jpg",
            "http://www.ddkjplus.com/resource/art19.jpg",
            "http://www.ddkjplus.com/resource/art20.jpg"};
    private String[] micTitle = {"如何确定自己发声位置？",
            "十分钟学会唱高音",
            "你的歌唱状态对了吗？",
            "咽喉部位的肌肉训练",
            "《父亲的草原母亲的河》详谈",
            "在歌唱中咬字问题的解释",
            "不会假声？进来看看",
            "音域的高低大小",
            "高音，低音的解释",
            "ae训练方法详解"};
    private String[] micAuthor = {"叶可法",
            "宋自在",
            "LeonAu",
            "ailxx",
            "张千年",
            "声学院",
            "青柳安",
            "lecture",
            "蒙言",
            "刘郑均"};
    private String[] micCount = {"12讲",
            "45讲",
            "57讲",
            "22讲",
            "39讲",
            "45讲",
            "74讲",
            "58讲",
            "8讲",
            "8讲"};
    private String[] micTime = {"15分钟",
            "56分钟",
            "144分钟",
            "80分钟",
            "8分钟",
            "13分钟",
            "8分钟",
            "55分钟",
            "10分钟",
            "50分钟"};
    private String[] micPrice = {"￥9.99",
            "￥19.99",
            "￥67.99",
            "￥99.99",
            "￥0.99",
            "￥10.99",
            "￥9.99",
            "￥24.99",
            "￥2.99",
            "￥14.99"};

    @BindView(R.id.rv_fragment)
    RecyclerView rvFragment;
    @BindView(R.id.swp_fragment)
    SwipeRefreshLayout swpFragment;

    private Mic2ClassAdapter mic2ClassAdapter;
    @Override
    protected void initView() {
        loadFalseData();
        initAdapter();
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){

        }
    }
    private void initAdapter(){
        swpFragment.setOnRefreshListener(this);
        mic2ClassAdapter = new Mic2ClassAdapter(micClasses,getMContext());
        rvFragment.setLayoutManager(new LinearLayoutManager(getMContext(), LinearLayoutManager.VERTICAL,false));
        rvFragment.setAdapter(mic2ClassAdapter);

        rvFragment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean controlVisible = true;
            int scrollDistance = 0;

            //onScrolled 滑动停止的时候调用
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (controlVisible && scrollDistance > THRESHOLD_OFFSET){//手指上滑即Scroll向下滚动的时候，dy为正
                    //animationHide();

                    controlVisible = false;
                    scrollDistance = 0;
                }else if (!controlVisible && scrollDistance < -THRESHOLD_OFFSET){//手指下滑即Scroll向上滚动的时候，dy为负
                    //animationShow();
                    controlVisible = true;
                    scrollDistance = 0;
                }

                if ((controlVisible && dy > 0) || (!controlVisible && dy < 0)){
                    scrollDistance += dy;
                }
            }
        });
    }
    private void animationHide(){
        ((ArticleListFragment) (Mic2Fragment.this.getParentFragment())).hideTab();
    }

    private void animationShow(){
        ((ArticleListFragment) (Mic2Fragment.this.getParentFragment())).showTab();
    }

    @Override
    public void onRefresh() {
        swpFragment.setRefreshing(false);
    }
    private void loadFalseData(){
        micClasses = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            MicClass micClass = new MicClass();
            micClass.setTitle(micTitle[i]);
            micClass.setAuthor(micAuthor[i]);
            micClass.setImgurl(micImg[i]);
            micClass.setPrice(micPrice[i]);
            micClass.setTime(micTime[i]);
            micClass.setClassCount(micCount[i]);
            micClasses.add(micClass);
        }
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_view_pager;
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
