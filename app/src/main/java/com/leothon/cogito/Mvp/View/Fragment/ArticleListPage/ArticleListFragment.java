package com.leothon.cogito.Mvp.View.Fragment.ArticleListPage;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Adapter.ArticleAdapter;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.DTO.ArticleData;
import com.leothon.cogito.Listener.loadMoreDataArticleListener;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.ArticleActivity.ArticleActivity;
import com.leothon.cogito.Mvp.View.Activity.WriteArticleActivity.WriteArticleActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.Banner;
import com.leothon.cogito.View.MyToast;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.leothon.cogito.Base.BaseApplication.getApplication;

/**
 * created by leothon on 2018.7.29
 * 专栏页fragment
 */
public class ArticleListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,ArticleListContract.IArticleListView {



    @BindView(R.id.voice_bar_layout)
    AppBarLayout voiceBarLayout;
    @BindView(R.id.voice_bar)
    CardView voiceBar;

    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.toolbar_subtitle)
    TextView subtitle;

    @BindView(R.id.send_icon)
    RoundedImageView sendIcon;

//    @BindView(R.id.mic_class_tab)
//    TabLayout micClassTab;
//
//    @BindView(R.id.mic_class_viewpager)
//    ViewPager micClassViewPager;

    @BindView(R.id.article_banner)
    Banner articleBanner;

    @BindView(R.id.article_rv)
    RecyclerView articleRv;

    @BindView(R.id.swp_article)
    SwipeRefreshLayout swpArticle;

    @BindView(R.id.banner_title_article)
    TextView bannerTitle;
    private ArticleData articleData;
    private ArrayList<Article> articles;
    //private List<String> titleList = new ArrayList<>();
//    private Mic1Fragment fragmentMic1;
//    private Mic2Fragment fragmentMic2;
    //private FragmentPagerAdapter fragmentPagerAdapter;

    private ArticleAdapter articleAdapter;
//    private ArrayList<Fragment> fragments;

    private Animation showAnimation;
    private Animation hideAnimation;
    private static int THRESHOLD_OFFSET = 10;

    private BaseApplication baseApplication;
    private ArticleListPresenter articleListPresenter;






    public ArticleListFragment() { }

    /**
     * fragment的构造方法
     * @return
     */
    public static ArticleListFragment newInstance() {
        ArticleListFragment fragment = new ArticleListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_article_list;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            //Fragment隐藏时调用
        }else {
            //Fragment显示时调用
        }

    }

    @Override
    protected void initData() {
        if (baseApplication == null){
            baseApplication = (BaseApplication)getApplication();
        }
        swpArticle.setProgressViewOffset (false,100,300);
        swpArticle.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        EventBus.getDefault().register(this);
        articleListPresenter = new ArticleListPresenter(this);
    }

    @Override
    protected void initView() {
        title.setText("");
        subtitle.setText("");
        showAnimation = AnimationUtils.loadAnimation(getMContext(),R.anim.top_view_in);
        hideAnimation = AnimationUtils.loadAnimation(getMContext(),R.anim.top_view_out);
        articleListPresenter.loadArticleData(fragmentsharedPreferencesUtils.getParams("token","").toString());
        swpArticle.setRefreshing(true);
    }

    private void initAdapter(){


        swpArticle.setOnRefreshListener(this);
        articleAdapter = new ArticleAdapter(getMContext(),articles);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        articleRv.setLayoutManager(staggeredGridLayoutManager);
        articleRv.setAdapter(articleAdapter);
        articleAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });

        articleAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("articleId",articleData.getArticles().get(position).getArticleId());
                IntentUtils.getInstence().intent(getMContext(),ArticleActivity.class,bundle);
            }
        });
        articleRv.addOnScrollListener(new loadMoreDataArticleListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMoreArticleData(int currentPage) {
                swpArticle.setRefreshing(true);
                articleListPresenter.loadArticleData(fragmentsharedPreferencesUtils.getParams("token","").toString(),currentPage * 15);
            }
        });
    }

    @Override
    public void loadArticlePageData(final ArticleData articleData) {
        this.articleData = articleData;
        if (swpArticle.isRefreshing()){
            swpArticle.setRefreshing(false);
        }
        articles = articleData.getArticles();
        ArrayList<String> urls = new ArrayList<>();
        for (int i = 0;i < articleData.getBanners().size();i ++){
            urls.add(articleData.getBanners().get(i).getBanner_img());
        }

        articleBanner.setImageUrl(urls);
        articleBanner.setPointPosition(2);//位置点为右边
        initAdapter();
        articleBanner.setOnItemClickListener(new Banner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle bundle = new Bundle();
                bundle.putString("articleId",articleData.getBanners().get(position).getBanner_id());
                IntentUtils.getInstence().intent(getMContext(),ArticleActivity.class,bundle);
            }
        });
        articleBanner.setOnPositionListener(new Banner.OnPositionListener() {
            @Override
            public void onPositionChange(int position) {
                bannerTitle.setText("" + articleData.getBanners().get(position).getBanner_url());
            }
        });
    }

    @Override
    public void loadMoreArticlePageData(ArrayList<Article> articles) {
        if (swpArticle.isRefreshing()){
            swpArticle.setRefreshing(false);
        }
        for (int i = 0;i < articles.size();i ++){
            this.articles.add(articles.get(i));
        }
        articleAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(Article article) {
        articleListPresenter.loadArticleData(fragmentsharedPreferencesUtils.getParams("token","").toString());
    }
    @OnClick(R.id.write_article)
    public void writeArticleByText(View view){
        toWriteArticle();
    }
    @OnClick(R.id.write_img)
    public void writeArticleByImg(View view){
        toWriteArticle();
    }

    private void toWriteArticle(){
        if ((boolean)fragmentsharedPreferencesUtils.getParams("login",false)){
            IntentUtils.getInstence().intent(getMContext(),WriteArticleActivity.class);
        }else {
            CommonUtils.loadinglogin(getMContext());
        }

    }
    @Override
    public void onRefresh() {
        articleListPresenter.loadArticleData(fragmentsharedPreferencesUtils.getParams("token","").toString());
    }


    public void showTab(){
        voiceBar.setVisibility(View.VISIBLE);
        voiceBar.startAnimation(showAnimation);
    }

    public void hideTab(){
        voiceBar.setVisibility(View.GONE);
        voiceBar.startAnimation(hideAnimation);
    }


    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(getMContext()).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        baseApplication = null;
        articleListPresenter.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
