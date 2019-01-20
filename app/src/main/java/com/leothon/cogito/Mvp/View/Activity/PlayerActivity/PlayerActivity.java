package com.leothon.cogito.Mvp.View.Activity.PlayerActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.Player;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.ChooseClassAdapter;
import com.leothon.cogito.Adapter.VideoCommentAdapter;
import com.leothon.cogito.Bean.ChooseClass;
import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Bean.VideoClass;
import com.leothon.cogito.DTO.VideoDetail;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Manager.ScrollSpeedLinearLayoutManager;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.PayInfoActivity.PayInfoActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.Utils.tokenUtils;
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
import butterknife.OnClick;

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
public class PlayerActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,PlayerContract.IPlayerView {


    @BindView(R.id.video_toolbar)
    Toolbar toolbar;
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

    @BindView(R.id.playButton)
    ButtonBarLayout playTitle;
    @BindView(R.id.video_danmu)
    FrameLayout videoLayout;

    @BindView(R.id.video_scroll_view)
    NestedScrollView videoScrollView;

    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.app_bar)
    AppBarLayout appBar;

    @BindView(R.id.swp_video_comment)
    SwipeRefreshLayout swpVideoComment;
    private boolean isPlay;
    private boolean isPause;

    //正常
    public static final int CURRENT_STATE_NORMAL = 0;
    //准备中
    public static final int CURRENT_STATE_PREPAREING = 1;
    //播放中
    public static final int CURRENT_STATE_PLAYING = 2;
    //开始缓冲
    public static final int CURRENT_STATE_PLAYING_BUFFERING_START = 3;
    //暂停
    public static final int CURRENT_STATE_PAUSE = 5;
    //自动播放结束
    public static final int CURRENT_STATE_AUTO_COMPLETE = 6;
    //错误状态
    public static final int CURRENT_STATE_ERROR = 7;
    private Intent intent;
    private Bundle bundle;
    private OrientationUtils orientationUtils;


    private ArrayList<Comment> userComments;
    private ArrayList<ChooseClass> chooseClasses;

    private VideoCommentAdapter videoCommentAdapter;
    private ChooseClassAdapter chooseClassAdapter;
    private ScrollSpeedLinearLayoutManager scrollSpeedLinearLayoutManager;
    private ImageView imageView;

    private static int COMPLETED = 1;
    private Bitmap bitmap;
    private int mCurrentState = -1;

    private int ChoosePosition = 0;
    private PlayerPresenter playerPresenter;

    private View popview;
    private PopupWindow popupWindow;

    private View morePopview;
    private PopupWindow morePopupWindow;

    private View moreDismiss;
    private RelativeLayout copyComment;
    private RelativeLayout deleteComment;
    private MaterialEditText editComment;
    private TextView replyToWho;
    private ImageView sendComment;
    private View dismiss;
    private String uuid;
    private UserEntity userEntity;
    private String nowClassDId;
    @Override
    public int initLayout() {
        return R.layout.activity_player;
    }
    private CollapsingToolbarLayoutState state;




    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    @Override
    public void initData() {
        playerPresenter = new PlayerPresenter(this);
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        uuid = tokenValid.getUid();
        swpVideoComment.setProgressViewOffset (false,100,300);
        swpVideoComment.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        swpVideoComment.setDistanceToTriggerSync(500);
        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
    }

    @Override
    public void initView() {
        //切换课程时候，记得这个要重新初始化，以防止数据被覆盖
        intent = getIntent();
        StatusBarUtils.transparencyBar(this);
        toolbar.setPadding(0,CommonUtils.getStatusBarHeight(this) - CommonUtils.dip2px(this,3),0,0);
        bundle = intent.getExtras();
        nowClassDId = bundle.getString("classdid");
        //count = bundle.getInt("count");
        swpVideoComment.setRefreshing(true);
        playerPresenter.getVideoDetail(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("classdid"),bundle.getString("classid"));


        appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                mCurrentState = videoPlayer.getCurrentState();
                if (mCurrentState >= 0 && mCurrentState != CURRENT_STATE_NORMAL
                        && mCurrentState != CURRENT_STATE_AUTO_COMPLETE && mCurrentState != CURRENT_STATE_ERROR && mCurrentState != CURRENT_STATE_PAUSE) {
                    videoScrollView.setNestedScrollingEnabled(false);
                } else {
                    if (!videoScrollView.isNestedScrollingEnabled()){
                        videoScrollView.setNestedScrollingEnabled(true);

                    }

                    if (verticalOffset == 0) {
                        if (state != CollapsingToolbarLayoutState.EXPANDED) {
                            state = CollapsingToolbarLayoutState.EXPANDED;
                            //修改状态标记为展开
                            //toolbarLayout.setTitle("EXPANDED");
                            //设置title为EXPANDED
                        }
                    } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                        if (state != CollapsingToolbarLayoutState.COLLAPSED) {

                            toolbarLayout.setTitle("");
                            playTitle.setVisibility(View.VISIBLE);
                            state = CollapsingToolbarLayoutState.COLLAPSED;

                        }

                    } else {
                        if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                            if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                                playTitle.setVisibility(View.GONE);
                                //由折叠变为中间状态时隐藏播放按钮
                            }
                            //toolbarLayout.setTitle("INTERNEDIATE");
                            //设置title为INTERNEDIATE
                            state = CollapsingToolbarLayoutState.INTERNEDIATE;
                            //修改状态标记为中间
                        }
                    }

                }

            }






        });
    }

    @Override
    public void onRefresh() {
        playerPresenter.getVideoDetail(activitysharedPreferencesUtils.getParams("token","").toString(),nowClassDId,bundle.getString("classid"));

    }

    @Override
    public void getVideoDetailInfo(VideoDetail videoDetail) {

        for (int i = 0;i < videoDetail.getClassd_id().size();i ++){
            if (nowClassDId.equals(videoDetail.getClassd_id().get(i))){
                ChoosePosition = i;
            }
        }

        if (swpVideoComment.isRefreshing()){
            swpVideoComment.setRefreshing(false);
        }
        loadAll(videoDetail,ChoosePosition);

    }

    @Override
    public void showInfo(String msg) {
        CommonUtils.makeText(this,msg);
    }
    @OnClick(R.id.playButton)
    public void playVideo(View v){
        appBar.setExpanded(true);
        videoPlayer.getCurrentPlayer().onVideoResume(false);

    }

    private void loadAll(final VideoDetail videoDetail,int position){
        LoadView(videoDetail);
        initMorePopupWindow();
        initReplyPopupWindow();
        if (videoDetail.getClassd_id().size() == 1){
            chooseClass.setVisibility(View.GONE);
        }else {
            chooseClass.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            rvChooseClass.setLayoutManager(linearLayoutManager);


            rvChooseClass.smoothScrollToPosition(position);

            chooseClasses = new ArrayList<>();
            for (int j = 0;j < videoDetail.getClassd_id().size();j ++){
                ChooseClass chooseClass = new ChooseClass();
                chooseClass.setPosition(position);
                chooseClass.setCount("第" + CommonUtils.toCharaNumber(j + 1) + "课");
                if (videoDetail.isFree() || videoDetail.isBuy()){
                    chooseClass.setLocked(false);
                }else {
                    if (j > 0) {
                        chooseClass.setLocked(true);
                    }else {
                        chooseClass.setLocked(false);
                    }
                }
                chooseClasses.add(chooseClass);
            }


            chooseClassAdapter = new ChooseClassAdapter(this,chooseClasses);
            rvChooseClass.setAdapter(chooseClassAdapter);
            chooseClassAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(View v, int position) {

                    if (videoDetail.isBuy() || videoDetail.isFree()){
                        intentClass(videoDetail.getClassd_id().get(position));
                        ChoosePosition = position;
                    }else {
                        if (position < 1){
                            intentClass(videoDetail.getClassd_id().get(position));
                            ChoosePosition = position;
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
                        Bundle bundleto = new Bundle();
                        bundleto.putString("classId",bundle.getString("classid"));
                        IntentUtils.getInstence().intent(PlayerActivity.this,PayInfoActivity.class,bundleto);
                        CommonUtils.makeText(PlayerActivity.this,"跳转支付");
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }

    private void intentClass(String classdId){
        swpVideoComment.setRefreshing(true);
        nowClassDId = classdId;
        playerPresenter.getVideoDetail(activitysharedPreferencesUtils.getParams("token","").toString(),classdId,bundle.getString("classid"));

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public BaseModel initModel() {
        return null;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }



    public void LoadView(final VideoDetail videoDetail) {

        videoAuthorIcon.setImageResource(R.drawable.defaulticon);
        videoAuthorName.setText("腾格尔");
        videoTitle.setText(videoDetail.getClassDetailList().getClassd_title());
        videoDescription.setText(videoDetail.getClassDetailList().getClassd_des());
        viewCount.setText(videoDetail.getClassDetailList().getClassd_view());
        favCount.setText(videoDetail.getClassDetailList().getClassd_like());
        imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        new Thread(new Runnable() {
            @Override
            public void run() {

                bitmap = CommonUtils.getVideoThumbnail(videoDetail.getClassDetailList().getClassd_video());
                Message msg = new Message();
                msg.what = COMPLETED;
                handler.sendMessage(msg);
            }
        }).start();

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
                .setUrl(videoDetail.getClassDetailList().getClassd_video())
                .setCacheWithPlay(false)
                .setVideoTitle(videoDetail.getClassDetailList().getClassd_title())
                .setVideoAllCallBack(new GSYSampleCallBack(){
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        orientationUtils.setEnable(true);
                        isPlay = true;
                        playerPresenter.addVideoView(activitysharedPreferencesUtils.getParams("token","").toString(),videoDetail.getClassDetailList().getClass_classd_id(),nowClassDId);
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

        loadComment(videoDetail.getComments());

        commentIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 弹出评论的窗口

                replyToWho.setText("评论课程 : " + videoDetail.getClassDetailList().getClassd_title());
                showPopWindow();
                popupInputMethod();
                sendComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playerPresenter.sendQaComment(videoDetail.getClassDetailList().getClassd_id(),activitysharedPreferencesUtils.getParams("token","").toString(),editComment.getText().toString());

                        Comment comment = new Comment();
                        comment.setUser_name(userEntity.getUser_name());
                        comment.setUser_icon(userEntity.getUser_icon());
                        comment.setComment_q_time(CommonUtils.getNowTime());
                        comment.setComment_q_like("0");
                        comment.setComment_q_content(editComment.getText().toString());
                        videoDetail.getComments().add(comment);
                        chooseClassAdapter.notifyDataSetChanged();
                        editComment.setText("");
                        playerPresenter.getVideoDetail(activitysharedPreferencesUtils.getParams("token","").toString(),videoDetail.getClassDetailList().getClassd_id(),videoDetail.getClassDetailList().getClass_classd_id());
                        popupWindow.dismiss();
                    }
                });
                videoPlayer.onVideoPause();



            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == COMPLETED) {
                imageView.setImageBitmap(bitmap);
            }
        }
    };

    public void initReplyPopupWindow(){
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
        replyToWho = (TextView)popview.findViewById(R.id.reply_to_who);
        sendComment = (ImageView)popview.findViewById(R.id.send_comment);
        dismiss = (View)popview.findViewById(R.id.dismiss);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

            }
        });
    }

    public void initMorePopupWindow(){
        morePopview = LayoutInflater.from(this).inflate(R.layout.popup_more_layout,null,false);
        morePopupWindow = new PopupWindow(morePopview,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        morePopupWindow.setBackgroundDrawable(new BitmapDrawable());
        morePopupWindow.setTouchable(true);
        morePopupWindow.setAnimationStyle(R.style.popupWindow_anim_style);
        morePopupWindow.setFocusable(true);
        morePopupWindow.setOutsideTouchable(true);
        //popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        morePopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        moreDismiss = (View)morePopview.findViewById(R.id.more_dismiss);
        copyComment = (RelativeLayout)morePopview.findViewById(R.id.copy_comment);
        deleteComment = (RelativeLayout)morePopview.findViewById(R.id.delete_comment);



        moreDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                morePopupWindow.dismiss();

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
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);

    }

    public void showMorePopWindow(){
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_player,null);
        morePopupWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);
    }

    public void loadComment(final ArrayList<Comment> comments){
        swpVideoComment.setOnRefreshListener(this);
        videoCommentAdapter = new VideoCommentAdapter(comments,this);

        videoCommentList.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));
        videoCommentList.setAdapter(videoCommentAdapter);


        videoCommentAdapter.setOnClickAddVideoLikeComment(new VideoCommentAdapter.AddVideoLikeCommentOnClickListener() {
            @Override
            public void addVideoLikeCommentClickListener(boolean isLike, String commentId) {
                if (isLike){
                    playerPresenter.removeLikeComment(activitysharedPreferencesUtils.getParams("token","").toString(),commentId);
                }else {
                    playerPresenter.addLikeComment(activitysharedPreferencesUtils.getParams("token","").toString(),commentId);
                }
            }
        });

        videoCommentAdapter.setVideoOnClickAddLikeReply(new VideoCommentAdapter.AddVideoLikeReplyOnClickListener() {
            @Override
            public void addVideoLikeReplyClickListener(boolean isLike, String replyId) {
                if (isLike){
                    playerPresenter.removeLikeReply(activitysharedPreferencesUtils.getParams("token","").toString(),replyId);
                }else {
                    playerPresenter.addLikeReply(activitysharedPreferencesUtils.getParams("token","").toString(),replyId);
                }
            }
        });

        videoCommentAdapter.setVideoSendReplyOnClickListener(new VideoCommentAdapter.SendVideoReplyOnClickListener() {
            @Override
            public void sendVideoReplyClickListener(final String commentId, final String toUserId , String toUsername) {
                replyToWho.setText("回复给： " + toUsername);
                showPopWindow();
                popupInputMethod();
                sendComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playerPresenter.sendReply(commentId,activitysharedPreferencesUtils.getParams("token","").toString(),toUserId,editComment.getText().toString());
                        editComment.setText("");
                        popupWindow.dismiss();
                    }
                });
            }
        });

        videoCommentAdapter.setVideoDeleteCommentOnClickListener(new VideoCommentAdapter.DeleteVideoCommentOnClickListener() {
            @Override
            public void deleteVideoCommentClickListener(final String commentId, String commentUserId, final String content, final int position) {

                showMorePopWindow();
                if (commentUserId.equals(uuid)){
                    deleteComment.setVisibility(View.VISIBLE);
                }else {
                    deleteComment.setVisibility(View.GONE);
                }

                deleteComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playerPresenter.deleteQaComment(commentId,activitysharedPreferencesUtils.getParams("token","").toString());
                        comments.get(position).setComment_q_content("该评论已被删除");
                        videoCommentAdapter.notifyDataSetChanged();
                        morePopupWindow.dismiss();
                    }
                });

                copyComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(content);
                        CommonUtils.makeText(PlayerActivity.this,"内容已复制");
                        morePopupWindow.dismiss();
                    }
                });

            }
        });

        videoCommentAdapter.setVideoDeleteReplyOnClickListener(new VideoCommentAdapter.DeleteVideoReplyOnClickListener() {
            @Override
            public void deleteVideoReplyClickListener(final String replyId, String replyUserId, final String content, final int commentPosition, final int replyPosition) {

                showMorePopWindow();
                if (replyUserId.equals(uuid)){
                    deleteComment.setVisibility(View.VISIBLE);
                }else{
                    deleteComment.setVisibility(View.GONE);
                }

                deleteComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playerPresenter.deleteReply(replyId,activitysharedPreferencesUtils.getParams("token","").toString());
                        comments.get(commentPosition).getReplies().get(replyPosition).setReply_comment("该回复已被删除");
                        videoCommentAdapter.notifyDataSetChanged();
                    }
                });


                copyComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(content);
                        CommonUtils.makeText(PlayerActivity.this,"内容已复制");
                    }
                });


            }
        });

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

        playerPresenter.onDestroy();
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
