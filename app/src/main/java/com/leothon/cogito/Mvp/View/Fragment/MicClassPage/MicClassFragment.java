package com.leothon.cogito.Mvp.View.Fragment.MicClassPage;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.leothon.cogito.Adapter.ArticleAdapter;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.ArticleActivity.ArticleActivity;
import com.leothon.cogito.Mvp.View.Activity.WriteArticleActivity.WriteArticleActivity;
import com.leothon.cogito.Mvp.View.Fragment.MicClassFragment.Mic1Fragment;
import com.leothon.cogito.Mvp.View.Fragment.MicClassFragment.Mic2Fragment;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.Banner;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.7.29
 * 专栏页fragment
 */
public class MicClassFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{



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





    public MicClassFragment() { }

    /**
     * fragment的构造方法
     * @return
     */
    public static MicClassFragment newInstance() {
        MicClassFragment fragment = new MicClassFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_voice;
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
    protected void initView() {
        title.setText("");
        subtitle.setText("");
        //voiceBar.setCardElevation(3.0f);
        showAnimation = AnimationUtils.loadAnimation(getMContext(),R.anim.top_view_in);
        hideAnimation = AnimationUtils.loadAnimation(getMContext(),R.anim.top_view_out);
        initAdapter();
    }

    private void initAdapter(){

        ArrayList<String> urls = new ArrayList<>();
        urls.add("http://www.ddkjplus.com/resource/banner4.jpg");
        urls.add("http://www.ddkjplus.com/resource/banner2.jpg");
        urls.add("http://www.ddkjplus.com/resource/banner1.jpg");

        articleBanner.setImageUrl(urls);
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
                IntentUtils.getInstence().intent(getMContext(),ArticleActivity.class);
            }
        });
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
        IntentUtils.getInstence().intent(getMContext(),WriteArticleActivity.class);
    }
    @Override
    public void onRefresh() {
        swpArticle.setRefreshing(false);
    }
    @Override
    protected void initData() {

        swpArticle.setProgressViewOffset (false,100,300);
        swpArticle.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);

        articles = new ArrayList<>();
        for (int i = 0;i < 30;i ++){
            Article article = new Article();
            article.setArticleAuthorIcon("http://www.ddkjplus.com/resource/1550635845572.png");
            article.setArticleAuthorName("Leothon");
            article.setArticleContent("可不可以不换壶？可以，毕竟这个魔术表演过，且刘谦也完全能做到，但倒不出这么多饮料，达不到这么震撼的效果。猜测：刘谦原来的魔术不得已被取消了，被换成这个旧魔术，但节目组亦或是刘谦为了更好的效果不得已出此下策，毕竟春晚面对的不是舞台下的人更多是电视机前的观众。作者：QDong 链接：https://www.zhihu.com/question/312337413/answer/599132914 来源：知乎著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。");
            article.setArticleImg("http://www.ddkjplus.com/resource/home12.jpg");
            article.setArticleTime("12-23 14:28");
            article.setArticleTitle("刘谦换壶魔术揭秘");
            articles.add(article);
        }

//        fragments = new ArrayList<>();
//        fragmentMic1 = Mic1Fragment.newInstance();
//        fragmentMic2 = Mic2Fragment.newInstance();
//        titleList.add("艺条微课");
//        titleList.add("艺条课堂");
//        fragments.add(fragmentMic1);
//        fragments.add(fragmentMic2);
//
//        micClassTab.addTab(micClassTab.newTab().setText(titleList.get(0)));
//        micClassTab.addTab(micClassTab.newTab().setText(titleList.get(1)));
//
//        fragmentPagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
//            @Override
//            public Fragment getItem(int i) {
//                return fragments.get(i);
//            }
//
//            @Override
//            public int getCount() {
//                return fragments.size();
//            }
//
//            @Nullable
//            @Override
//            public CharSequence getPageTitle(int position) {
//                return titleList.get(position);
//            }
//
//        };
//
//        micClassViewPager.setAdapter(fragmentPagerAdapter);
//        micClassTab.setupWithViewPager(micClassViewPager);
        //micClassTab.setTabsFromPagerAdapter(fragmentPagerAdapter);
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
    public void hideLoading() {}

    @Override
    public void showMessage(@NonNull String message) {}

    @Override
    public void showLoading() {}



}
