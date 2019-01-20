package com.leothon.cogito.Mvp.View.Fragment.HomePage;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leothon.cogito.Adapter.HomeAdapter;
import com.leothon.cogito.Adapter.TeacherAdapter;
import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.Teacher;
import com.leothon.cogito.DTO.HomeData;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.BannerActivity.BannerActivity;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.Mvp.View.Activity.SearchActivity.SearchActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.Mvp.View.Activity.TeacherActivity.TeacherActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.View.Banner;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.7.29
 * 首页的fragment
 */
public class HomeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,HomeAdapter.OnItemClickListener,View.OnClickListener,HomeFragmentContract.IHomeView{

    private List<String> bigPics;


    @BindView(R.id.search_top)
    CardView searchTop;

    @BindView(R.id.position_bar_host)
    LinearLayout positionBar;

    @BindView(R.id.search)
    RelativeLayout search;

    @BindView(R.id.swp)
    SwipeRefreshLayout swp;

    @BindView(R.id.rv_home)
    RecyclerView rvHome;

    private Handler mHandler;

    private HomeAdapter homeAdapter;

    private LinearLayoutManager linearLayoutManager;

    private HomePresenter homePresenter;
    private HomeData homeData;
    private ArrayList<SelectClass> selectClasses;


    private ArrayList<com.leothon.cogito.Bean.Banner> banners;
    public HomeFragment() {
    }


    /**
     * fragment的构造方法
     *
     * @return
     */
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        swp.setProgressViewOffset (false,100,300);
        swp.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        homeData = new HomeData();
        selectClasses = new ArrayList<>();
        homePresenter = new HomePresenter(this);
        homePresenter.loadHomeData(fragmentsharedPreferencesUtils.getParams("token","").toString());
        swp.setRefreshing(true);
    }
    @Override
    protected void initView() {
        mHandler = new Handler();
        ViewGroup.LayoutParams layoutParams = positionBar.getLayoutParams();
        layoutParams.height = CommonUtils.getStatusBarHeight(getMContext()) - CommonUtils.dip2px(getMContext(),3);
        positionBar.setLayoutParams(layoutParams);
        positionBar.setVisibility(View.INVISIBLE);
        searchTop.setBackgroundColor(getResources().getColor(R.color.alpha));

    }

    public void initBanner(ArrayList<com.leothon.cogito.Bean.Banner> banners) {
        bigPics = new ArrayList<>();
        this.banners = banners;
        for (int i = 0 ; i < banners.size() ; i++){
            bigPics.add(banners.get(i).getBanner_img());
        }
    }

    @Override
    public void loadData(HomeData homeData) {
        if (swp.isRefreshing()){
            swp.setRefreshing(false);
        }
        this.homeData = homeData;

        selectClasses = homeData.getSelectClasses();
        ArrayList<com.leothon.cogito.Bean.Banner> banners = homeData.getBanners();
        initBanner(banners);
        swp.setRefreshing(false);
        initAdapter();

    }

    @Override
    public void loadMoreData(ArrayList<SelectClass> selectClassS) {
        for (int i = 0;i < selectClassS.size(); i++){
            selectClasses.add(selectClassS.get(i));
            homeAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showInfo(String msg) {
        CommonUtils.makeText(getMContext(),msg);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){

        }
    }
    public void initAdapter() {
        swp.setOnRefreshListener(this);

        homeAdapter = new HomeAdapter(homeData, selectClasses,getMContext());
        homeAdapter.setmOnItemClickLitener(this);
        initBanner(homeAdapter);
        initTea(homeAdapter);
        initTest(homeAdapter);
        initdivider(homeAdapter);
        linearLayoutManager = new LinearLayoutManager(getMContext());
        rvHome.setLayoutManager(linearLayoutManager);
        rvHome.setAdapter(homeAdapter);
        rvHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (linearLayoutManager != null){
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    if (position > 0){
                        searchTop.setBackgroundColor(getResources().getColor(R.color.white));
                        int bgID = getResources().getIdentifier("search_background", "drawable", "com.leothon.cogito");
                        Drawable bg = getResources().getDrawable(bgID);
                        search.setBackground(bg);
                        //search.setBackgroundColor(getResources().getColor(R.color.dividerColor));
                        getActivity().getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        StatusBarUtils.setStatusBarColor(getActivity(),R.color.white);
                    }else {
                        searchTop.setBackgroundColor(getResources().getColor(R.color.alpha));
                        int bgID = getResources().getIdentifier("search_background_alpha", "drawable", "com.leothon.cogito");
                        Drawable bg = getResources().getDrawable(bgID);
                        search.setBackground(bg);
                        //search.setBackgroundColor(getResources().getColor(R.color.whitealpha));
                        StatusBarUtils.transparencyBar(getActivity());
                    }
                }
            }
        });
        rvHome.addOnScrollListener(new loadMoreDataListener(linearLayoutManager) {
            @Override
            public void onLoadMoreData(int currentPage) {
                homePresenter.loadMoreClassData(currentPage * 10,fragmentsharedPreferencesUtils.getParams("token","").toString());
                Log.e( "onLoadMoreData: " ,"页数" + currentPage * 10);
            }
        });
    }


    private void initBanner(HomeAdapter homeAdapter) {
        View bannerview = View.inflate(getMContext(), R.layout.banner_item, null);
        final Banner banner = (Banner) bannerview.findViewById(R.id.banner_home);
        homeAdapter.addHeadView0(bannerview);
        banner.setImageUrl(bigPics);
        banner.setOnItemClickListener(new Banner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                /**
                 * 使用position找到数组中图片的地址并进入相应的Activity
                 */
                //TODO 跳转到对应的显示banner广告的网页

                if (banners.get(position).getBanner_type().equals("img")){
                    Bundle bundle = new Bundle();
                    bundle.putString("urls", banners.get(position).getBanner_url());
                    bundle.putString("title", "海报");
                    IntentUtils.getInstence().intent(getMContext(), BannerActivity.class, bundle);
                }else {
                    Bundle bundle = new Bundle();
                    bundle.putString("classId", banners.get(position).getBanner_to_class_id());
                    IntentUtils.getInstence().intent(getMContext(), SelectClassActivity.class, bundle);
                }
            }
        });


    }

    private void initTea(HomeAdapter homeAdapter) {

        View teacherView = View.inflate(getMContext(), R.layout.tea_layout, null);
        RecyclerView teaRV = teacherView.findViewById(R.id.teacher_rv);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getMContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        teaRV.setLayoutManager(linearLayoutManager);

        TeacherAdapter teacherAdapter = new TeacherAdapter(getMContext(),homeData.getTeachers());
        teaRV.setAdapter(teacherAdapter);
        teacherAdapter.setOnItemClickListener(new TeacherAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

                Bundle bundle = new Bundle();
                bundle.putString("teacherId",homeData.getTeachers().get(position).getUser_id());
                IntentUtils.getInstence().intent(getMContext(), TeacherActivity.class, bundle);

            }
        });

        teacherAdapter.setOnItemLongClickListener(new TeacherAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });
        homeAdapter.addHeadView1(teacherView);
    }


    private void initTest(HomeAdapter homeAdapter) {
        View testView = View.inflate(getMContext(), R.layout.test_home, null);
        homeAdapter.addHeadView2(testView);
    }

    private void initActivity(HomeAdapter homeAdapter) {
        View activityView = View.inflate(getMContext(), R.layout.tagcommon_home, null);
        homeAdapter.addHeadView3(activityView);
    }

    public void initclass(HomeAdapter homeAdapter) {
        View classView = View.inflate(getMContext(), R.layout.tagcommon_home, null);
        homeAdapter.addHeadView4(classView);
    }


//    public void initvideoclass(HomeAdapter homeAdapter) {
//        View videoclassView = View.inflate(getMContext(), R.layout.tagcommon_home, null);
//        homeAdapter.addHeadView5(videoclassView);
//    }

    public void initdivider(HomeAdapter homeAdapter) {
        View dividerView = View.inflate(getMContext(), R.layout.dividerview, null);
        homeAdapter.addHeadView5(dividerView);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onRefresh() {
        homePresenter.loadHomeData(fragmentsharedPreferencesUtils.getParams("token","").toString());
    }



    /**
     * 跳转到搜索页面
     *
     * @param v
     */
    @OnClick(R.id.search)
    public void toSearch(View v) {
        //TODO 跳转到搜索页面
        IntentUtils.getInstence().intent(getMContext(), SearchActivity.class);
    }

    @Override
    public void hideLoading() {
    }

    @Override
    public void showMessage(@NonNull String message) {
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void onItemClick(View v, int postion) {

    }

    @Override
    public void onItemLongClick(View v, int postion) {


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homePresenter.onDestroy();
    }
}
