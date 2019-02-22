package com.leothon.cogito.Mvp.View.Fragment.MicClassFragment;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leothon.cogito.Adapter.Mic1ClassAdapter;
import com.leothon.cogito.Bean.MicClass;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Fragment.ArticleListPage.ArticleListFragment;
import com.leothon.cogito.R;

import java.util.ArrayList;

import butterknife.BindView;


public class Mic1Fragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private ArrayList<MicClass> micClasses;
    private static int THRESHOLD_OFFSET = 10;
    private String[] micImg =
            {"http://www.ddkjplus.com/resource/art1.jpg",
            "http://www.ddkjplus.com/resource/art2.jpg",
            "http://www.ddkjplus.com/resource/art3.jpg",
            "http://www.ddkjplus.com/resource/art4.jpg",
            "http://www.ddkjplus.com/resource/art5.jpg",
            "http://www.ddkjplus.com/resource/art6.jpg",
            "http://www.ddkjplus.com/resource/art7.jpg",
            "http://www.ddkjplus.com/resource/art8.jpg",
            "http://www.ddkjplus.com/resource/art9.jpg",
            "http://www.ddkjplus.com/resource/art10.jpg"};
    private String[] micTitle = {"如何确定自己发声位置？",
            "在歌唱中咬字问题的解释",
            "你的歌唱状态对了吗？",
            "《父亲的草原母亲的河》详谈",
            "不会假声？进来看看",
            "高音，低音的解释",
            "音域的高低大小",
            "咽喉部位的肌肉训练",
            "十分钟学会唱高音",
            "ae训练方法详解"};
    private String[] micAuthor = {"叶可法",
            "lecture",
            "宋自在",
            "青柳安",
            "张千年",
            "LeonAu",
            "ailxx",
            "声学院",
            "蒙言",
            "刘郑均"};
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
    private int firstItemPosition = 0;
    private Mic1ClassAdapter mic1ClassAdapter;
    public Mic1Fragment() {

    }


    public static Mic1Fragment newInstance() {
        Mic1Fragment fragment = new Mic1Fragment();
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_view_pager;
    }

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
        mic1ClassAdapter = new Mic1ClassAdapter(micClasses,getMContext());
        rvFragment.setLayoutManager(new LinearLayoutManager(getMContext(), LinearLayoutManager.VERTICAL,false));
        rvFragment.setAdapter(mic1ClassAdapter);


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

                //当scrollDistance累计到隐藏（显示)ToolBar之后，如果Scroll向下（向上）滚动，则停止对scrollDistance的累加
                //直到Scroll开始往反方向滚动，再次启动scrollDistance的累加
                if ((controlVisible && dy > 0) || (!controlVisible && dy < 0)){
                    scrollDistance += dy;
                }
            }
        });
        RecyclerView.LayoutManager layoutManager = rvFragment.getLayoutManager();
        //判断是当前layoutManager是否为LinearLayoutManager
        // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
        if (layoutManager instanceof LinearLayoutManager) {
            LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
            //获取最后一个可见view的位置
            //int lastItemPosition = linearManager.findLastVisibleItemPosition();
            //获取第一个可见view的位置
            firstItemPosition = linearManager.findFirstVisibleItemPosition();
        }
    }

    private void animationHide(){
        ((ArticleListFragment) (Mic1Fragment.this.getParentFragment())).hideTab();
    }

    private void animationShow(){
        ((ArticleListFragment) (Mic1Fragment.this.getParentFragment())).showTab();
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
            micClasses.add(micClass);
        }
    }

    private void backARefresh(){
        if (firstItemPosition == 0){
            swpFragment.setOnRefreshListener(this);
        }else {
            rvFragment.scrollToPosition(0);
            firstItemPosition = 0;
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
