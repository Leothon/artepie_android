package com.leothon.cogito.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Mvp.View.Activity.ActivityActivity.ActivityActivity;
import com.leothon.cogito.Mvp.View.Activity.ActivityListActivity.ActivityListActivity;
import com.leothon.cogito.Mvp.View.Activity.PlayerActivity.PlayerActivity;
import com.leothon.cogito.Mvp.View.Activity.TestActivity.TestActivity;
import com.leothon.cogito.Mvp.View.Activity.ViewPagerActivity.ViewPageActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.7.30
 * 专为首页复杂的布局编写的adapter
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    //首页布局总数

    //需要在首页加载的一整条数据
    private List<String> allDatas;

    //TODO 以下数据均为假数据，在实际开发中，要把上面的alldatas进行解析
    /**
     * 以下数据均为假数据，在实际开发中，要把上面的alldatas进行解析
     */
    private String[] foryouVideo = {
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fmusic.jzxy.edu.cn%2Fimages%2F16%2F04%2F22%2F1cnf70gawx%2F56pz_image001.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2534055294%2C3045001537%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fjxb.hbwgydx.com%2Fupfiles%2F20150330163612337.jpg&thumburl=http%3A%2F%2Fimg5.imgtn.bdimg.com%2Fit%2Fu%3D3027847697%2C1579758526%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fjly.xhcom.edu.cn%2Fdigidata%2F2014-4-30%2F7813363711.JPG&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D501126548%2C560633887%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Ffamehall.com%2Fnelsontao%2Fphoto%2F2009%2F20090307-nelson070.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D1758210423%2C366943980%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fcms-bucket.nosdn.127.net%2F91c1fadee3094ab0b26e6ac82567484f20170714144127.jpeg%3FimageView%26thumbnail%3D550x0&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1050740983%2C2262122875%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fwww.chaohaojian.com%2Fupload%2Ffck%2FZ_20110928-161917_CG77.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D2489780484%2C4129421496%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Ff.01ny.cn%2Fforum%2F201302%2F16%2F075211fqmmzmwyprpdww8w.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D1896258671%2C2756036358%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fimg.ph.126.net%2FXOK1RyvGcvTxUVGanI3BAA%3D%3D%2F6598157183656073074.jpg&thumburl=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D1055812407%2C2049095469%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fchina-ljsw.com%2Fupdate%2F3%2Fbb5f8d46b790e71e34.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D494318182%2C3784477290%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fwww.fhgy.cn%2FUploadFiles%2Fnews%2F2016%2F11%2F20161112173915.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D986374677%2C2301535002%26fm%3D11%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fimg.guitarchina.com%2Fimg2016%2F0727yy%2F24.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D624817842%2C1391406194%26fm%3D26%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fpic.baike.soso.com%2Fp%2F20140714%2F20140714095040-38685612.jpg&thumburl=http%3A%2F%2Fimg0.imgtn.bdimg.com%2Fit%2Fu%3D4270141334%2C1319034638%26fm%3D27%26gp%3D0.jpg"};
    private String[] foryouvideotitle = {
            "男中音的通病",
            "歌手杨杰现场示范",
            "男高音专场",
            "包楞调对唱",
            "海归歌唱家刘明",
            "民族情歌对唱",
            "亲爱的爸爸",
            "王强教授现场教学",
            "民族歌手刘硕示范",
            "歌唱气息的支点",
            "歌曲表达的意境",
            "气息的控制"};
    private String[] activitiesName = {"国际音乐节","草莓音乐节","“爱之声” 音乐会","国家大剧院专场","美声专场"};
    private String[] activitiesImg = {"http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fimage.juooo.com%2Fupload%2Fueditor%2Fimage%2F20150915%2F1442311240572722.png&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D2188087079%2C1470367263%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fphotocdn.sohu.com%2F20130502%2FImg374568060.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D25528335%2C2202724169%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fcdn.k618img.cn%2Fnews.k618.cn%2Fsociety%2F201801%2FW020180109617684454232.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D3279624311%2C3206833128%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fimage.juooo.com%2Fupload%2Fueditor%2Fimage%2F20150915%2F1442311240572722.png&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D2188087079%2C1470367263%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Ff.hiphotos.baidu.com%2Fbaike%2Fc0%253Dbaike116%252C5%252C5%252C116%252C38%2Fsign%3Decbd88aa622762d09433acedc185639f%2Fbf096b63f6246b60496285fdeaf81a4c500fa2ce.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D787689764%2C3280717493%26fm%3D27%26gp%3D0.jpg"};

    private String[] testesName = {"民族","美声","古典","戏曲","原生态","民谣","通俗","其他"};
//    private int[] testesImg = {R.drawable.meisheng,R.drawable.minzu,R.drawable.tongsu,R.drawable.yuanshengtai,R.drawable.minyao,R.drawable.shuochang,R.drawable.jueshi};

    private String[] classesName = {"爱上钢琴","葫芦丝王子","男低音歌唱家","国粹传承人","作品欣赏课"};
    private String[] classesImg = {"http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fhimg2.huanqiu.com%2Fattachment2010%2F2015%2F1030%2F11%2F43%2F20151030114302175.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D3483213455%2C1103428764%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Ff.01ny.cn%2Fforum%2F201112%2F04%2F190722f3vkvdfv3khz30mt.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D524358622%2C2173211898%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fy0.ifengimg.com%2Fcmpp%2F2014%2F12%2F15%2F13%2F262b861a-31eb-44fc-b30d-d611d2d469fb.JPG&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D1989887561%2C4126741818%26fm%3D27%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fp0.qhimgs4.com%2Ft014537d45b7b3e5bbb.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D2854303102%2C4251008079%26fm%3D11%26gp%3D0.jpg",
            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fq_70%2Cc_zoom%2Cw_640%2Fimages%2F20180621%2F01c23db09b024dfebbc0d7f3c9319203.jpeg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D4169152315%2C3808895887%26fm%3D11%26gp%3D0.jpg"};

//    private String[] videoName = {"《天堂》","蒋大为的歌曲","声音的共鸣","美声如何发音","课程集锦"};
//    private String[] videoImg = {"http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fimg.52fuqing.com%2Fupload%2Fnews%2F20180224%2F201802241130485019.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D4206511761%2C1971567880%26fm%3D27%26gp%3D0.jpg",
//            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fvpic.video.qq.com%2F3388556%2Fu0326kofujb_ori_3.jpg&thumburl=http%3A%2F%2Fimg5.imgtn.bdimg.com%2Fit%2Fu%3D33290701%2C4103959810%26fm%3D27%26gp%3D0.jpg",
//            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fwww.sycm.com.cn%2Ffile%2Ffile_images%2Fmiddle%2F20150629083343181875.JPG&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1298413096%2C1752857152%26fm%3D27%26gp%3D0.jpg",
//            "http://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=http%3A%2F%2Fwww.nnjsx.cn%2Fuploads%2Fueditor%2Fimg%2F20160822%2F1471855304880262.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D1826940172%2C343088080%26fm%3D27%26gp%3D0.jpg",
//            "http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fwww.fhgy.cn%2FUploadFiles%2FPhoto%2F2012%2F4%2F201204261058132115.jpg&thumburl=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D3691238471%2C1121997744%26fm%3D11%26gp%3D0.jpg"};
//
    private Context context;

    public HomeAdapter(List<String> allDatas,Context context){
        this.allDatas = allDatas;
        this.context = context;
    }
    private int HEAD0 = 0;
    private int HEAD1 = 1;
    private int HEAD2 = 2;
    private int HEAD3 = 3;
    private int HEAD4 = 4;
    private int HEAD5 = 5;
    private int HEAD6 = 6;
    private int HEAD7 = 7;


    private View headView0;
    private View headView1;
    private View headView2;
    private View headView3;
    private View headView4;
    private View headView5;
    private View headView6;
    private View headView7;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HEAD0) {
            return new BannerHolder(headView0);
        }else if(viewType == HEAD1){
            return new TeaHolder(headView1);
        }else if (viewType == HEAD2){
            return new testHolder(LayoutInflater.from(context).inflate(R.layout.test_home,parent,false));
        }else if(viewType == HEAD3){
            return new activityHolder(LayoutInflater.from(context).inflate(R.layout.tagcommon_home,parent,false));
        }else if(viewType == HEAD4){
            return new classHolder(LayoutInflater.from(context).inflate(R.layout.tagcommon_home,parent,false));
        }else if(viewType == HEAD5){
            return new dividerHolder(LayoutInflater.from(context).inflate(R.layout.dividerview,parent,false));
        }else if (viewType == HEAD6){
            return new foryouHolder(LayoutInflater.from(context).inflate(R.layout.videoforyou_home,parent,false));
        }else {
            return new bottomHolder(LayoutInflater.from(context).inflate(R.layout.bottom_show_empty,parent,false));
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == HEAD0 ) {
            return;
        }else if(viewType == HEAD1){
            return;
        }else if(viewType == HEAD2){
            testHolder testholder = (testHolder) holder;

            testholder.test1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",testesName[0]);
                    bundle.putString("description","这里显示各种曲目的介绍");
                    IntentUtils.getInstence().intent(context, TestActivity.class,bundle);
                }
            });
            testholder.test2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",testesName[1]);
                    bundle.putString("description","这里显示各种曲目的介绍");
                    IntentUtils.getInstence().intent(context, TestActivity.class,bundle);
                }
            });
            testholder.test3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",testesName[2]);
                    bundle.putString("description","这里显示各种曲目的介绍");
                    IntentUtils.getInstence().intent(context, TestActivity.class,bundle);
                }
            });
            testholder.test4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",testesName[3]);
                    bundle.putString("description","这里显示各种曲目的介绍");
                    IntentUtils.getInstence().intent(context, TestActivity.class,bundle);
                }
            });
            testholder.test5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",testesName[4]);
                    bundle.putString("description","这里显示各种曲目的介绍");
                    IntentUtils.getInstence().intent(context, TestActivity.class,bundle);
                }
            });
            testholder.test6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",testesName[5]);
                    bundle.putString("description","这里显示各种曲目的介绍");
                    IntentUtils.getInstence().intent(context, TestActivity.class,bundle);
                }
            });
            testholder.test7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",testesName[6]);
                    bundle.putString("description","这里显示各种曲目的介绍");
                    IntentUtils.getInstence().intent(context, TestActivity.class,bundle);
                }
            });
            testholder.test8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",testesName[7]);
                    bundle.putString("description","这里显示各种曲目的介绍");
                    IntentUtils.getInstence().intent(context, TestActivity.class,bundle);
                }
            });

            testholder.test_title1.setText(testesName[0]);
            testholder.test_title2.setText(testesName[1]);
            testholder.test_title3.setText(testesName[2]);
            testholder.test_title4.setText(testesName[3]);
            testholder.test_title5.setText(testesName[4]);
            testholder.test_title6.setText(testesName[5]);
            testholder.test_title7.setText(testesName[6]);
            testholder.test_title8.setText(testesName[7]);

        }else if (viewType == HEAD3){

            activityHolder activityholder = (activityHolder) holder;
            activityholder.tagnameInActivity.setText("演出观摩");
            activityholder.activity1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",activitiesName[0]);
                    bundle.putString("url",activitiesImg[0]);
                    IntentUtils.getInstence().intent(context, ActivityActivity.class,bundle);
                }
            });
            activityholder.activity2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",activitiesName[1]);
                    bundle.putString("url",activitiesImg[1]);
                    IntentUtils.getInstence().intent(context, ActivityActivity.class,bundle);
                }
            });
            activityholder.activity3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",activitiesName[2]);
                    bundle.putString("url",activitiesImg[2]);
                    IntentUtils.getInstence().intent(context, ActivityActivity.class,bundle);
                }
            });
            activityholder.activity4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",activitiesName[3]);
                    bundle.putString("url",activitiesImg[3]);
                    IntentUtils.getInstence().intent(context, ActivityActivity.class,bundle);
                }
            });
            activityholder.activity5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",activitiesName[4]);
                    bundle.putString("url",activitiesImg[4]);
                    IntentUtils.getInstence().intent(context, ActivityActivity.class,bundle);
                }
            });
            activityholder.activity6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /**
                     * 此处的数据直接在ActivityListActivity中请求
                     */
                    Bundle bundle = new Bundle();
                    bundle.putString("title","更多演出活动");
                    IntentUtils.getInstence().intent(context, ActivityListActivity.class,bundle);
                }
            });

            activityholder.activity1title.setText(activitiesName[0]);
            activityholder.activity2title.setText(activitiesName[1]);
            activityholder.activity3title.setText(activitiesName[2]);
            activityholder.activity4title.setText(activitiesName[3]);
            activityholder.activity5title.setText(activitiesName[4]);

            //加载图片
            ImageLoader.loadImageViewThumbnailwitherror(context,activitiesImg[0],activityholder.activity1img,R.drawable.defalutimg);
            ImageLoader.loadImageViewThumbnailwitherror(context,activitiesImg[1],activityholder.activity2img,R.drawable.defalutimg);
            ImageLoader.loadImageViewThumbnailwitherror(context,activitiesImg[2],activityholder.activity3img,R.drawable.defalutimg);
            ImageLoader.loadImageViewThumbnailwitherror(context,activitiesImg[3],activityholder.activity4img,R.drawable.defalutimg);
            ImageLoader.loadImageViewThumbnailwitherror(context,activitiesImg[4],activityholder.activity5img,R.drawable.defalutimg);

        }else if(viewType == HEAD4){
            classHolder classholder = (classHolder) holder;
            classholder.tagnameInClass.setText("公益讲堂");

             //TODO 需要判定是讲课还是活动，分别进行跳转
            classholder.class1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("imgUrls",classesImg[0]);
                    bundle.putString("imgTitle",classesName[0]);
                    bundle.putInt("count",1);
                    bundle.putInt("position",0);
                    bundle.putString("price","0");
                    IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
                }
            });
            classholder.class2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("imgUrls",classesImg[1]);
                    bundle.putString("imgTitle",classesName[1]);
                    bundle.putInt("count",1);
                    bundle.putInt("position",0);
                    bundle.putString("price","0");
                    IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
                }
            });
            classholder.class3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("imgUrls",classesImg[2]);
                    bundle.putString("imgTitle",classesName[2]);
                    bundle.putInt("count",1);
                    bundle.putInt("position",0);
                    bundle.putString("price","0");
                    IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
                }
            });
            classholder.class4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("imgUrls",classesImg[3]);
                    bundle.putString("imgTitle",classesName[3]);
                    bundle.putInt("count",1);
                    bundle.putInt("position",0);
                    bundle.putString("price","0");
                    IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
                }
            });
            classholder.class5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("imgUrls",classesImg[4]);
                    bundle.putString("imgTitle",classesName[4]);
                    bundle.putInt("count",1);
                    bundle.putInt("position",0);
                    bundle.putString("price","0");
                    IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
                }
            });
            classholder.class6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /**
                     * 内容点击进去进行请求
                     */
                    Bundle bundle = new Bundle();
                    bundle.putString("title","更多公益内容");
                    IntentUtils.getInstence().intent(context, ViewPageActivity.class,bundle);
                }
            });

            classholder.class1title.setText(classesName[0]);
            classholder.class2title.setText(classesName[1]);
            classholder.class3title.setText(classesName[2]);
            classholder.class4title.setText(classesName[3]);
            classholder.class5title.setText(classesName[4]);

            //加载图片
            ImageLoader.loadImageViewThumbnailwitherror(context,classesImg[0],classholder.class1img,R.drawable.defalutimg);
            ImageLoader.loadImageViewThumbnailwitherror(context,classesImg[1],classholder.class2img,R.drawable.defalutimg);
            ImageLoader.loadImageViewThumbnailwitherror(context,classesImg[2],classholder.class3img,R.drawable.defalutimg);
            ImageLoader.loadImageViewThumbnailwitherror(context,classesImg[3],classholder.class4img,R.drawable.defalutimg);
            ImageLoader.loadImageViewThumbnailwitherror(context,classesImg[4],classholder.class5img,R.drawable.defalutimg);

        }else if (viewType == HEAD5){
            return;
        }else if (viewType == HEAD6){
            //TODO 增加更多的数据显示
            foryouHolder foryouholder = (foryouHolder) holder;
            final int videoPosition = position - 6;
            foryouholder.foryouIV.setImageResource(R.drawable.defalutimg);
            //foryouholder.foryouIV.setTag(videoPosition);
            foryouholder.foryouTV.setText(foryouvideotitle[videoPosition]);
            foryouholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = videoPosition;
                    //以下的context来自于home Fragment调用构造方法时传入，fragment中context来自于basefragment
                    Bundle bundle = new Bundle();
                    bundle.putString("imgUrls",foryouVideo[videoPosition]);
                    bundle.putString("imgTitle",foryouvideotitle[videoPosition]);
                    bundle.putInt("count",1);
                    IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
                }
            });
            ImageLoader.loadImageViewThumbnailwitherror(context,foryouVideo[videoPosition],foryouholder.foryouIV,R.drawable.defalutimg);
        }else {
            return;
        }


    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager){
            final GridLayoutManager gridLayoutManager = ((GridLayoutManager)manager);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    if (type == HEAD6){
                        return 1;
                    }else {
                        return 2;
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && headView0 != null) {
            return HEAD0;
        }else if (position == 1 && headView1 != null){
            return HEAD1;
        }else if (position == 2 && headView2 != null){
            return HEAD2;
        }else if (position == 3 && headView3 != null){
            return HEAD3;
        }else if (position == 4 && headView4 != null){
            return HEAD4;
        }else if (position == 5 && headView5 != null){
            return HEAD5;
        }else if (position <= (foryouVideo.length + 5) && position != 0){
            return HEAD6;
        }else {
            return HEAD7;
        }

    }

    @Override
    public int getItemCount() {
        return 7 + foryouvideotitle.length;
    }

    public void addHeadView0(View view){
        this.headView0 = view;
    }
    public void addHeadView1(View view){
        this.headView1 = view;
    }
    public void addHeadView2(View view){
        this.headView2 = view;
    }
    public void addHeadView3(View view){
        this.headView3 = view;
    }
    public void addHeadView4(View view){
        this.headView4 = view;
    }
//    public void addHeadView5(View view){
//        this.headView5 = view;
//    }
    public void addHeadView5(View view){
        this.headView5 = view;
    }
    @Override
    public void onClick(View view) {

    }

    /**
     * bannerholder
     */
    class BannerHolder extends RecyclerView.ViewHolder{

        public BannerHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 名师专栏holder
     */
    class TeaHolder extends RecyclerView.ViewHolder{

        public TeaHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class activityHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tag_name)
        TextView tagnameInActivity;
        @BindView(R.id.home1)
        FrameLayout activity1;
        @BindView(R.id.home2)
        FrameLayout activity2;
        @BindView(R.id.home3)
        FrameLayout activity3;
        @BindView(R.id.home4)
        FrameLayout activity4;
        @BindView(R.id.home5)
        FrameLayout activity5;
        @BindView(R.id.home6)
        FrameLayout activity6;

        @BindView(R.id.home_title1)
        TextView activity1title;
        @BindView(R.id.home_title2)
        TextView activity2title;
        @BindView(R.id.home_title3)
        TextView activity3title;
        @BindView(R.id.home_title4)
        TextView activity4title;
        @BindView(R.id.home_title5)
        TextView activity5title;

        @BindView(R.id.home_img1)
        RoundedImageView activity1img;
        @BindView(R.id.home_img2)
        RoundedImageView activity2img;
        @BindView(R.id.home_img3)
        RoundedImageView activity3img;
        @BindView(R.id.home_img4)
        RoundedImageView activity4img;
        @BindView(R.id.home_img5)
        RoundedImageView activity5img;

        public activityHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class testHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.test1)
        RelativeLayout test1;
        @BindView(R.id.test2)
        RelativeLayout test2;
        @BindView(R.id.test3)
        RelativeLayout test3;
        @BindView(R.id.test4)
        RelativeLayout test4;
        @BindView(R.id.test5)
        RelativeLayout test5;
        @BindView(R.id.test6)
        RelativeLayout test6;
        @BindView(R.id.test7)
        RelativeLayout test7;
        @BindView(R.id.test8)
        RelativeLayout test8;

        @BindView(R.id.test_title1)
        TextView test_title1;
        @BindView(R.id.test_title2)
        TextView test_title2;
        @BindView(R.id.test_title3)
        TextView test_title3;
        @BindView(R.id.test_title4)
        TextView test_title4;
        @BindView(R.id.test_title5)
        TextView test_title5;
        @BindView(R.id.test_title6)
        TextView test_title6;
        @BindView(R.id.test_title7)
        TextView test_title7;
        @BindView(R.id.test_title8)
        TextView test_title8;



        public testHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class classHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tag_name)
        TextView tagnameInClass;
        @BindView(R.id.home1)
        FrameLayout class1;
        @BindView(R.id.home2)
        FrameLayout class2;
        @BindView(R.id.home3)
        FrameLayout class3;
        @BindView(R.id.home4)
        FrameLayout class4;
        @BindView(R.id.home5)
        FrameLayout class5;
        @BindView(R.id.home6)
        FrameLayout class6;

        @BindView(R.id.home_title1)
        TextView class1title;
        @BindView(R.id.home_title2)
        TextView class2title;
        @BindView(R.id.home_title3)
        TextView class3title;
        @BindView(R.id.home_title4)
        TextView class4title;
        @BindView(R.id.home_title5)
        TextView class5title;

        @BindView(R.id.home_img1)
        RoundedImageView class1img;
        @BindView(R.id.home_img2)
        RoundedImageView class2img;
        @BindView(R.id.home_img3)
        RoundedImageView class3img;
        @BindView(R.id.home_img4)
        RoundedImageView class4img;
        @BindView(R.id.home_img5)
        RoundedImageView class5img;
        public classHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    class dividerHolder extends RecyclerView.ViewHolder{

        public dividerHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class foryouHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.foryou_iv)
        ImageView foryouIV;
        @BindView(R.id.foryou_tv)
        TextView foryouTV;
        public foryouHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class bottomHolder extends RecyclerView.ViewHolder{

        public bottomHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View v,int postion);
        void onItemLongClick(View v,int postion);
    }
    /**自定义条目点击监听*/
    private OnItemClickListener mOnItemClickLitener;

    public void setmOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
