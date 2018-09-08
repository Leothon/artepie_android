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

    private String[] icon = {"http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fimgtu.5011.net%2Fuploads%2Fcontent%2F20170209%2F4934501486627131.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D2529469200%2C1162169902%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fp1.qzone.la%2Fupload%2F20150222%2F5xpgue83.png&thumburl=http%3A%2F%2Fimg5.imgtn.bdimg.com%2Fit%2Fu%3D1736487673%2C2633075576%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Ftx.haiqq.com%2Fuploads%2Fallimg%2F170510%2F1103462438-3.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D1763953693%2C1371207734%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Ftouxiang.qqzhi.com%2Fuploads%2F2012-11%2F1111105548865.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1333603353%2C3527120685%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Ftouxiang.yeree.com%2Fpics%2Fef%2F1094311.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D726391378%2C113960242%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fimg1.touxiang.cn%2Fuploads%2F20140512%2F12-085354_583.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1742543435%2C485938443%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fp1.qqyou.com%2Ftouxiang%2Fuploadpic%2F2012-5%2F13%2F2012051318024699143.jpg&thumburl=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D925846424%2C1623763912%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fwww.qqzhi.com%2Fuploadpic%2F2014-05-02%2F001810904.jpg&thumburl=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D1073960396%2C1422270742%26fm%3D26%26gp%3D0.jpg"};
    private String[] name = {"Lanruence","张小光","风末","北冥有鱼","人生终有路","Server 吴","文德斯","马伯庸"};
    private String[] desc = {"小音乐教师","我亦飘零久","公众号yytt2018,欢迎关注","半桶纯净水，洗清心中事","唯有音乐和啤酒","Ask my piano","文艺年轻人","不进则退"};
    private String[] content = {"这周课程好多啊！购买的课程都没来得及看，自己要加油了！！",
            "音乐是反映人类现实生活情感的一种艺术（英名称：music；法文名称： musique；意大利文：musica）。\n音乐可以分为声乐和器乐两大类型，又可以粗略的分为古典音乐、民间音乐、原生态音乐、现代音乐（包括流行音乐）等。在艺术类型中，音乐具有抽象性，音乐从历史发展上可分为东方音乐和西方音乐。东方以中国汉族音乐为首的中国古代理论基础是五声音阶，即宫、商、角、徵、羽，西方是以七声音阶为主。音乐让人赏心悦目，并为大家带来听觉的享受。音乐能提高人的审美能力，净化人们的心灵，树立崇高的理想。我们通过音乐来抒发我们的情感，使我们的很多情绪得到释放。",
            "一首《天堂》送给大家，唱得不好还请见谅。。\nps:关注我的公众号，每天一段小视频",
            "在明亮的月光中徘徊，目光却看不到更远，漆黑的颜色总是阻止着心动，阻止着远行的背包。溜走的人和事可以在明天尽量不去重复遗憾，昨天还谈论的故事，今天只有在雨水密织的车窗外偶尔还能看见……",
            "迷笛！迷笛！迷笛！",
            "-When the whole world is about to rain, let’s make it clear in our heart together.\n-当全世界约好一起下雨，让我们约好一起在心里放晴。",
            "各位大佬，我想问下，谁能解决我高音的问题？\n 详情看视频",
            "见到了我的偶像，激动中"};
    private String[] videourl = {"http://121.196.199.171:8080/myweb/cogito001.mp4",
            "",
            "http://121.196.199.171:8080/myweb/cogito001.mp4",
            "http://121.196.199.171:8080/myweb/cogito001.mp4",
            "http://121.196.199.171:8080/myweb/cogito001.mp4",
            "",
            "http://121.196.199.171:8080/myweb/cogito001.mp4",
            ""};
    private String[] coverurl = {"http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fjxb.hbwgydx.com%2Fupfiles%2F20150330163612337.jpg&thumburl=http%3A%2F%2Fimg5.imgtn.bdimg.com%2Fit%2Fu%3D3027847697%2C1579758526%26fm%3D27%26gp%3D0.jpg",
            "",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Ffamehall.com%2Fnelsontao%2Fphoto%2F2009%2F20090307-nelson070.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D1758210423%2C366943980%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Ff.01ny.cn%2Fforum%2F201302%2F16%2F075211fqmmzmwyprpdww8w.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D1896258671%2C2756036358%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fimg.mp.sohu.com%2Fupload%2F20170623%2Fdd9e7bef3a564270a1da154003a123b3_th.png&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D909547022%2C674958986%26fm%3D27%26gp%3D0.jpg",
            "",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fpic.baike.soso.com%2Fp%2F20140714%2F20140714095040-38685612.jpg&thumburl=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D4270141334%2C1319034638%26fm%3D27%26gp%3D0.jpg",
            ""};
    private String[] like = {"466","456","0","846","4764","0","846","64"};
    private String[] commnent = {"21","54","864","0","658","464","54","0"};
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
        title.setText("互动论坛");
        subtitle.setText("");
        asks = new ArrayList<>();
        loadfalsedata();
        initAdapter();

    }

    private void loadfalsedata(){

        for(int i = 0;i<8;i++){
            Ask ask = new Ask();
            ask.setUsericonurl(icon[i]);
            ask.setUsername(name[i]);
            ask.setUserdes(desc[i]);
            ask.setContent(content[i]);
            ask.setLikecount(like[i]);
            ask.setCommentcount(commnent[i]);
            ask.setVideourl(videourl[i]);
            ask.setCoverurl(coverurl[i]);

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
