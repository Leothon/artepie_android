package com.leothon.cogito.Mvp.View.Fragment.MicClassFragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.Mic1ClassAdapter;
import com.leothon.cogito.Adapter.ViewPagerContentAdapter;
import com.leothon.cogito.Bean.MicClass;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.MicClassActivity.Mic1ClassActivity;
import com.leothon.cogito.Mvp.View.Fragment.MicClassPage.MicClassFragment;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;

import java.util.ArrayList;

import butterknife.BindView;


public class Mic1Fragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private ArrayList<MicClass> micClasses;
    private static int THRESHOLD_OFFSET = 10;
    private String[] micImg = {"http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fpic81.huitu.com%2Fres%2F20160606%2F1023278_20160606191557276200_1.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D1113631057%2C4023783067%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fdocs.ebdoor.com%2FImage%2FInfoContentImage%2FProductDesc%2F2017%2F05%2F06%2Fda%2Fdae052d8-b3b2-4ab4-940b-a6529361dbbd.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D2281886034%2C407577424%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fpic2.cxtuku.com%2F00%2F03%2F16%2Fb4673e271bc6.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D3220011842%2C703251432%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Finfoimage.duoduoyin.com%2Fuploads%2Fnews%2F20160301%2F20160301055852567dcb22e0.jpg&thumburl=http%3A%2F%2Fimg5.imgtn.bdimg.com%2Fit%2Fu%3D3675121430%2C3678586633%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fi1.hdslb.com%2Fbfs%2Farchive%2F7a5ada7194ae2a3802ebf8de59d5fde0eb21050a.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D3089493290%2C1109433263%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fp0.meituan.net%2Fmogu%2F51888d4e941f44955d5172a32ece61e5114231.jpg&thumburl=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D3740252261%2C3000643199%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fbpic.588ku.com%2Felement_origin_min_pic%2F00%2F96%2F06%2F4156f2f5c4727fa.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D2293172972%2C2213945100%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fpic2.ooopic.com%2F11%2F48%2F04%2F06b1OOOPIC82.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D44522839%2C3991756975%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fpic41.nipic.com%2F20140505%2F18249376_125346747000_2.jpg&thumburl=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D17369009%2C1276609573%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Ftaobao.90sheji.com%2F58pic%2F15%2F25%2F04%2F93Y58PICEqT.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D402844967%2C507784682%26fm%3D26%26gp%3D0.jpg"};
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

    private void initAdapter(){
        swpFragment.setOnRefreshListener(this);
        mic1ClassAdapter = new Mic1ClassAdapter(getMContext(),micClasses);
        rvFragment.setLayoutManager(new LinearLayoutManager(getMContext(), LinearLayoutManager.VERTICAL,false));
        rvFragment.setAdapter(mic1ClassAdapter);
        mic1ClassAdapter.setOnItemClickListner(new BaseAdapter.OnItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                //TODO 跳转到相应的
                Bundle bundle = new Bundle();
                bundle.putString("title",micClasses.get(position).getTitle());
                bundle.putString("img",micClasses.get(position).getImgurl());
                bundle.putString("author",micClasses.get(position).getAuthor());
                bundle.putString("time",micClasses.get(position).getTime());
                bundle.putString("price",micClasses.get(position).getPrice());
                IntentUtils.getInstence().intent(getMContext(), Mic1ClassActivity.class,bundle);
            }
        });

        mic1ClassAdapter.setOnItemLongClickListner(new BaseAdapter.OnItemLongClickListner() {
            @Override
            public void onItemLongClickListner(View v, int position) {

            }
        });
        rvFragment.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean controlVisible = true;
            int scrollDistance = 0;

            //onScrolled 滑动停止的时候调用
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (controlVisible && scrollDistance > THRESHOLD_OFFSET){//手指上滑即Scroll向下滚动的时候，dy为正
                    animationHide();

                    controlVisible = false;
                    scrollDistance = 0;
                }else if (!controlVisible && scrollDistance < -THRESHOLD_OFFSET){//手指下滑即Scroll向上滚动的时候，dy为负
                    animationShow();
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
        ((MicClassFragment) (Mic1Fragment.this.getParentFragment())).hideTab();
    }

    private void animationShow(){
        ((MicClassFragment) (Mic1Fragment.this.getParentFragment())).showTab();
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
