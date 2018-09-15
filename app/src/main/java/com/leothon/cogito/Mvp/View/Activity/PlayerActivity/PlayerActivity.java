package com.leothon.cogito.Mvp.View.Activity.PlayerActivity;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.ChooseClassAdapter;
import com.leothon.cogito.Adapter.VideoCommentAdapter;
import com.leothon.cogito.Bean.ChooseClass;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.UserComment;
import com.leothon.cogito.Bean.VideoClass;
import com.leothon.cogito.Constants;
import com.leothon.cogito.Manager.ScrollSpeedLinearLayoutManager;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.PayInfoActivity.PayInfoActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.View.EmptyRecyclerView;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;

import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * created by leothon on 2018.8.2
 * 如果选择的是课程，则在进入时传入视频链接和视频标题的数组，在下拉菜单选择时，进行重新替换
 *
 * 选择是单个视频，则传入数据为
 * 1、单个视频标记
 * 2、视频链接
 * 3、进入时封面图片
 * 4、作者名称
 * 5、作者图标
 * 6、标题名字
 * 7、视频描述
 * 8、评论内容（数组）
 *      （1）评论用户名字
 *      （2）评论用户图标
 *      （3）评论用户评论
 * 9、观看数
 * 10、收藏数
 *
 *
 * 选择的是课程视频，则传入数据为
 * 1、课程视频标记
 * 2、所有课程下视频的内容（数组）
 *      （1）视频链接
 *      （2）视频封面
 *      （3）作者名字
 *      （4）作者图标
 *      （5）标题名字
 *      （6）视频描述
 *      （7）评论内容（数组）
 *              1）评论用户名字
 *              2）评论用户图标
 *              3）评论用户评论
 *      （8）观看数
 *      （9）收藏数
 */
public class PlayerActivity extends BaseActivity {


    //测试视频链接
    //private String url = "http://www.w3school.com.cn/example/html5/mov_bbb.mp4";
    private String url = "http://121.196.199.171:8080/myweb/cogito001.mp4";
    private String userIcon[] = {"","","","",""};
    private String username[] = {"赵一","钱二","孙三","李四","周五","吴六","郑七","王八","冯九","陈十","褚十一","卫十二","蒋十三","沈十四","韩十五","杨十六"};

    @BindView(R.id.video_player)
    StandardGSYVideoPlayer videoPlayer;

    @BindView(R.id.video_comment_list)
    RecyclerView videoCommentList;

    @BindView(R.id.video_author_icon)
    RoundedImageView videoAuthorIcon;
    @BindView(R.id.video_author_name)
    TextView videoAuthorName;
    @BindView(R.id.video_title)
    TextView videoTitle;
    @BindView(R.id.video_description)
    TextView videoDescription;
    @BindView(R.id.view_count)
    TextView viewCount;
    @BindView(R.id.fav_count)
    TextView favCount;

    @BindView(R.id.comment_in)
    CardView commentIn;

    @BindView(R.id.content_video)
    LinearLayout contentVideo;
    @BindView(R.id.video_bar)
    CardView videoBar;
    @BindView(R.id.choose_class)
    RelativeLayout chooseClass;
    @BindView(R.id.rv_choose_class)
    RecyclerView rvChooseClass;

    private boolean isPlay;
    private boolean isPause;


    private View empty;
    private VideoClass videoClass;
    private Intent intent;
    private Bundle bundle;
    private OrientationUtils orientationUtils;

    private View popview;
    private PopupWindow popupWindow;

    private View dismiss;
    private MaterialEditText editComment;
    private ImageView sendComment;

    private ArrayList<UserComment> userComments;
    private ArrayList<ChooseClass> chooseClasses;

    private VideoCommentAdapter videoCommentAdapter;
    private ChooseClassAdapter chooseClassAdapter;
    private int count = 0;
    private ScrollSpeedLinearLayoutManager scrollSpeedLinearLayoutManager;
    @Override
    public int initLayout() {
        return R.layout.activity_player;
    }

    @Override
    public void initView() {
        //切换课程时候，记得这个要重新初始化，以防止数据被覆盖
        intent = getIntent();
        bundle = intent.getExtras();
        count = bundle.getInt("count");

        loadAll(bundle.getString("imgTitle"),bundle.getString("imgUrls"),bundle.getInt("count"),bundle.getInt("position"),bundle.getString("price"));


    }

    private void loadAll(String title, String img, int count, int position, final String price){
        videoClass = new VideoClass();
        loadCommentData(position,price);
        Loadview(title,img);
        initPopupWindow();
        if (count == 1){
            chooseClass.setVisibility(View.GONE);
        }else {
            chooseClass.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvChooseClass.setLayoutManager(linearLayoutManager);


            rvChooseClass.smoothScrollToPosition(position);
            chooseClassAdapter = new ChooseClassAdapter(this,chooseClasses);
            rvChooseClass.setAdapter(chooseClassAdapter);
            chooseClassAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(View v, int position) {

                    if (price.equals("0")){
                        intentClass(position);
                    }else {
                        if (position < 3){
                            intentClass(position);
                        }else {
                            loadDialog();
                        }

                    }


                }
            });

            chooseClassAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
                @Override
                public void onItemLongClickListener(View v, int position) {

                }
            });



        }
    }

    private void loadDialog(){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setMessage("是否购买整套课程？")
                .setTitle("提醒")
                .setSingle(false)
                .setPositive("购买")
                .setNegtive("放弃")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        CommonUtils.makeText(PlayerActivity.this,"跳转支付");
                    }

                    @Override
                    public void onNegtiveClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }

    private void intentClass(int position){

        loadAll(bundle.getString("imgTitle"),bundle.getString("imgUrls"),bundle.getInt("count"),position,bundle.getString("price"));

    }
    @Override
    public void initData() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public BaseModel initModel() {
        return null;
    }

    /**
     * 伪数据
     */
    public void loadCommentData(int position,String price){


        userComments = new ArrayList<>();
        for (int i = 0;i < username.length;i++){
            UserComment userComment = new UserComment();
            userComment.setUserIcon(R.drawable.defalutimg);
            userComment.setUsername(username[i]);
            userComment.setUsercomment("这个视频真精彩");
            userComments.add(userComment);
        }
        videoClass.setUserComments(userComments);

        chooseClasses = new ArrayList<>();
        for (int j = 0;j < count;j++){
            ChooseClass chooseClass = new ChooseClass();
            chooseClass.setPosition(position);
            chooseClass.setCount("第" + CommonUtils.toCharaNumber(j + 1) + "课");
            if (j >= 3 && !price.equals("0")){
                chooseClass.setLocked(true);
            }else {
                chooseClass.setLocked(false);
            }
            chooseClasses.add(chooseClass);
        }

    }

    public void loadVideoData(){
        //将得到的数据加载到videoClass中再进行数值加载
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }



    public void Loadview(String title,String img) {


        videoTitle.setText(title);
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        ImageLoader.loadImageViewwithError(this,img,imageView,R.drawable.defalutimg);

        videoPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        orientationUtils = new OrientationUtils(this,videoPlayer);
        orientationUtils.setEnable(false);

        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(true)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(url)
                .setCacheWithPlay(false)
                .setVideoTitle(title)
                .setVideoAllCallBack(new GSYSampleCallBack(){
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (orientationUtils != null){
                            orientationUtils.backToProtVideo();
                        }
                    }
                }).setLockClickListener(new LockClickListener() {
                        @Override
                        public void onClick(View view, boolean lock) {
                            if (orientationUtils != null){
                                orientationUtils.setEnable(!lock);
                            }
                        }
                }).build(videoPlayer);


        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orientationUtils.resolveByClick();
                /**
                 *  bug描述：在本页中，不显示状态栏，但是全屏后，再返回会出现状态栏，根据本方法可知，传入两个参数，是否有状态栏和标题栏
                 *  默认传入两者都有，则程序执行时，会再退出全屏后重新生成状态栏，将此处两者设为没有（false)，则不会重新生成状态栏
                 */

                videoPlayer.startWindowFullscreen(PlayerActivity.this,false,false);
            }
        });

        loadComment();

        commentIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 弹出评论的窗口

                showPopWindow();
                popupInputMethod();
                videoPlayer.onVideoPause();



            }
        });
    }



    public void initPopupWindow(){
        popview = LayoutInflater.from(this).inflate(R.layout.poupwindowlayout,null,false);
        popupWindow = new PopupWindow(popview,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.popupWindow_anim_style);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        //popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        editComment = (MaterialEditText)popview.findViewById(R.id.edit_comment);

        sendComment = (ImageView)popview.findViewById(R.id.send_comment);
        dismiss = (View)popview.findViewById(R.id.dismiss);
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 发送信息给评论
                if (!editComment.getText().toString().equals("")){
                    String comment = editComment.getText().toString();
                    UserComment userComment = new UserComment();
                    userComment.setUserIcon(R.drawable.defalutimg);
                    userComment.setUsername("Leothon");
                    userComment.setUsercomment(comment);
                    userComments.add(0,userComment);
                    videoClass.setUserComments(userComments);
                    videoCommentAdapter.notifyItemInserted(0);
                    videoCommentAdapter.notifyItemRangeChanged(0,userComments.size());
                    videoCommentList.scrollToPosition(0);
                    popupWindow.dismiss();
                    videoPlayer.onVideoResume(true);
                    editComment.setText("");
                }else {
                    CommonUtils.makeText(PlayerActivity.this,"请输入评论内容");
                }

            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
                videoPlayer.onVideoResume(true);
            }
        });
    }

    private void popupInputMethod(){

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager=(InputMethodManager) editComment.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.showSoftInput(editComment, 0);
            }
        }, 0);
    }

    public void showPopWindow(){
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_player,null);
        popupWindow.showAtLocation(rootView,Gravity.BOTTOM,0,0);
    }

    public void loadComment(){
        videoCommentAdapter = new VideoCommentAdapter(this,videoClass.getUserComments());
        videoCommentAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                //TODO 未来进行回复评论的操作
                CommonUtils.makeText(PlayerActivity.this,"回复评论功能暂未开放");
            }
        });
        videoCommentAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });
        videoCommentList.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        videoCommentList.setAdapter(videoCommentAdapter);


    }


    @Override
    public void onBackPressed() {
        if (orientationUtils != null){
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)){
            return;
        }

        if (popupWindow.isShowing()){
            popupWindow.dismiss();
            videoPlayer.onVideoResume(true);
            return;
        }
        super.onBackPressed();

    }

    @Override
    protected void onPause() {
        videoPlayer.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        videoPlayer.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay){
            videoPlayer.getCurrentPlayer().release();
        }

        if (orientationUtils != null){
            orientationUtils.releaseListener();
        }
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isPlay && !isPause){
            videoPlayer.onConfigurationChanged(this,newConfig,orientationUtils,true,true);
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
