package com.leothon.cogito.Mvp.View.Activity.AskDetailActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.leothon.cogito.Adapter.AskAdapter;
import com.leothon.cogito.Adapter.AskDetailAdapter;
import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.wxapi.WXEntryActivity;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.tencent.tauth.Tencent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class AskDetailActivity extends BaseActivity implements AskDetailContract.IAskDetailView , SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.rv_ask_detail)
    RecyclerView rvAskDetail;
    @BindView(R.id.comment_in_detail)
    CardView commentIn;
    @BindView(R.id.swp_ask_detail)
    SwipeRefreshLayout swpAskDetail;


    private QADataDetail qaDataDetail;
    private AskDetailAdapter askDetailAdapter;

    private BottomSheetDialog shareDialog;
    private BottomSheetBehavior shareDialogBehavior;
    private RelativeLayout shareToQQ;
    private RelativeLayout shareToFriendCircle;
    private RelativeLayout shareToWeChat;
    private RelativeLayout shareToMore;
    private Tencent mTencent;

    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_SETTLING = 2;
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
    private UserEntity userEntity;

    private Intent intent;
    private Bundle bundle;

    private String uuid;

    private TextView deleteContent;
    private TextView copyContent;
    private RelativeLayout dismissQa;
    private View popviewQa;
    private PopupWindow popupWindowQa;

    private AskDetailPresenter askDetailPresenter;
    @Override
    public int initLayout() {
        return R.layout.activity_ask_detail;
    }
    @Override
    public void initData() {
        askDetailPresenter = new AskDetailPresenter(this);
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        uuid = tokenValid.getUid();

        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
        swpAskDetail.setProgressViewOffset (false,100,300);
        swpAskDetail.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
    }
    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarSubTitle("");
        setToolbarTitle("");
        swpAskDetail.setRefreshing(true);
        askDetailPresenter.getQADetailData(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("qaId"));

        initReplyPopupWindow();
        initMorePopupWindow();
        initMoreQAPopupWindow();
        showShareDialog();
    }



    @Override
    public void onRefresh() {
        askDetailPresenter.getQADetailData(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("qaId"));
    }
    private void initAdapter(){
        swpAskDetail.setOnRefreshListener(this);
        askDetailAdapter = new AskDetailAdapter(qaDataDetail,this);
        final LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        rvAskDetail.setLayoutManager(mlinearLayoutManager);
        rvAskDetail.setAdapter(askDetailAdapter);
        rvAskDetail.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItem = mlinearLayoutManager.findLastVisibleItemPosition();
                int firstVisibleItem = mlinearLayoutManager.findFirstVisibleItemPosition();
                if (GSYVideoManager.instance().getPlayPosition() >= 0){
                    int position = GSYVideoManager.instance().getPlayPosition();
                    if (GSYVideoManager.instance().getPlayTag().equals(AskAdapter.TAG) && (position < firstVisibleItem || position > lastVisibleItem)){
                        if (GSYVideoManager.isFullState(AskDetailActivity.this)){
                            return;
                        }

                        GSYVideoManager.releaseAllVideos();
                        askDetailAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                switch (newState){
                    case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
                        //当屏幕停止滚动，加载图片
                        try {
                            if(AskDetailActivity.this != null) Glide.with(AskDetailActivity.this).resumeRequests();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
                        //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                        try {
                            if(AskDetailActivity.this != null) Glide.with(AskDetailActivity.this).pauseRequests();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under outside control.
                        //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                        try {
                            if(AskDetailActivity.this != null) Glide.with(AskDetailActivity.this).pauseRequests();
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                }

            }
        });

        askDetailAdapter.setOnClickAddLikeDetail(new AskDetailAdapter.AddLikeDetailOnClickListener() {
            @Override
            public void addLikeDetailClickListener(boolean isLike, String qaId) {
                if (isLike){
                    askDetailPresenter.removeLikeDetail(activitysharedPreferencesUtils.getParams("token","").toString(),qaId);
                }else {
                    askDetailPresenter.addLikeDetail(activitysharedPreferencesUtils.getParams("token","").toString(),qaId);
                }
            }
        });

        askDetailAdapter.setOnClickAddLikeComment(new AskDetailAdapter.AddLikeCommentOnClickListener() {
            @Override
            public void addLikeCommentClickListener(boolean isLike, String commentId) {
                if (isLike){
                    askDetailPresenter.removeLikeComment(activitysharedPreferencesUtils.getParams("token","").toString(),commentId);
                }else {
                    askDetailPresenter.addLikeComment(activitysharedPreferencesUtils.getParams("token","").toString(),commentId);
                }
            }
        });

        askDetailAdapter.setOnClickAddLikeReply(new AskDetailAdapter.AddLikeReplyOnClickListener() {
            @Override
            public void addLikeReplyClickListener(boolean isLike, String replyId) {
                if (isLike){
                    askDetailPresenter.removeLikeReply(activitysharedPreferencesUtils.getParams("token","").toString(),replyId);
                }else {
                    askDetailPresenter.addLikeReply(activitysharedPreferencesUtils.getParams("token","").toString(),replyId);
                }
            }
        });

        askDetailAdapter.setSendReplyOnClickListener(new AskDetailAdapter.SendReplyOnClickListener() {
            @Override
            public void sendReplyClickListener(final String commentId, final String toUserId , String toUsername) {
                replyToWho.setText("回复给： " + toUsername);
                showPopWindow();
                popupInputMethod();
                sendComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        askDetailPresenter.sendReply(commentId,activitysharedPreferencesUtils.getParams("token","").toString(),toUserId,editComment.getText().toString());
                        editComment.setText("");
                        popupWindow.dismiss();
                    }
                });
            }
        });

        askDetailAdapter.setDeleteCommentOnClickListener(new AskDetailAdapter.DeleteCommentOnClickListener() {
            @Override
            public void deleteCommentClickListener(final String commentId, String commentUserId, final String content, final int position) {

                showMorePopWindow();
                if (commentUserId.equals(uuid)){
                    deleteComment.setVisibility(View.VISIBLE);
                }else {
                    deleteComment.setVisibility(View.GONE);
                }

                deleteComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askDetailPresenter.deleteQaComment(commentId,activitysharedPreferencesUtils.getParams("token","").toString());
                        qaDataDetail.getComments().get(position).setComment_q_content("该评论已被删除");
                        askDetailAdapter.notifyDataSetChanged();
                        morePopupWindow.dismiss();
                    }
                });

                copyComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(content);
                        MyToast.getInstance(AskDetailActivity.this).show("内容已复制",Toast.LENGTH_SHORT);
                        morePopupWindow.dismiss();
                    }
                });

            }
        });

        askDetailAdapter.setDeleteReplyOnClickListener(new AskDetailAdapter.DeleteReplyOnClickListener() {
            @Override
            public void deleteReplyClickListener(final String replyId, String replyUserId, final String content, final int commentPosition, final int replyPosition) {

                showMorePopWindow();
                if (replyUserId.equals(uuid)){
                    deleteComment.setVisibility(View.VISIBLE);
                }else{
                    deleteComment.setVisibility(View.GONE);
                }

                deleteComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askDetailPresenter.deleteReply(replyId,activitysharedPreferencesUtils.getParams("token","").toString());
                        qaDataDetail.getComments().get(commentPosition).getReplies().get(replyPosition).setReply_comment("该回复已被删除");
                        askDetailAdapter.notifyDataSetChanged();
                    }
                });


                copyComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(content);
                        MyToast.getInstance(AskDetailActivity.this).show("内容已复制",Toast.LENGTH_SHORT);
                    }
                });


            }
        });

        askDetailAdapter.setDeleteQaDetailOnClickListener(new AskDetailAdapter.DeleteQaDetailOnClickListener() {
            @Override
            public void deleteQaDetailClickListener(final String qaId, String qaUserId, final String content) {
                showQAPopWindow();
                if (qaUserId.equals(uuid)){
                    deleteContent.setVisibility(View.VISIBLE);
                }else{
                    deleteContent.setVisibility(View.GONE);
                }

                deleteContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askDetailPresenter.deleteQa(activitysharedPreferencesUtils.getParams("token","").toString(),qaId);
                        EventBus.getDefault().post("删除成功");
                        AskDetailActivity.super.onBackPressed();
                    }
                });


                copyComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(content);
                        MyToast.getInstance(AskDetailActivity.this).show("内容已复制",Toast.LENGTH_SHORT);
                    }
                });
            }
        });

        askDetailAdapter.setOnShareListener(new AskDetailAdapter.addShareDetailOnClickListener() {
            @Override
            public void addShareDetailClickListener() {
                shareDialog.show();
            }
        });


    }


    private void showShareDialog() {

        View view = View.inflate(this, R.layout.popup_share, null);
        shareToQQ = (RelativeLayout)view.findViewById(R.id.share_class_to_qq);
        shareToFriendCircle = (RelativeLayout)view.findViewById(R.id.share_class_to_circle);
        shareToWeChat = (RelativeLayout)view.findViewById(R.id.share_class_to_wechat);
        shareToMore = (RelativeLayout)view.findViewById(R.id.share_class_to_more);
        shareDialog = new BottomSheetDialog(this, R.style.dialog);
        shareDialog.setContentView(view);
        shareDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
        shareDialogBehavior.setPeekHeight(getWindowHeight());
        shareDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    shareDialog.dismiss();
                    shareDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        shareToQQ.setOnClickListener(new View.OnClickListener() {
            /**
             * @param view
             */
            @Override
            public void onClick(View view) {
                MyToast.getInstance(AskDetailActivity.this).show("暂不支持QQ分享",Toast.LENGTH_SHORT);
                //shareToQQClass(asks.get(position));
                shareDialog.dismiss();

            }
        });
        shareToFriendCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qaDataDetail.getQaData().getQa_video() == null){

                    MyToast.getInstance(AskDetailActivity.this).show("非视频不能分享到朋友圈",Toast.LENGTH_SHORT);
                    return;
                }
                Bundle bundleto = new Bundle();
                bundleto.putString("share","3");
                bundleto.putSerializable("qad",qaDataDetail);
                IntentUtils.getInstence().intent(AskDetailActivity.this, WXEntryActivity.class,bundleto);
                CommonUtils.addCoinAndUpdateInfo("1",activitysharedPreferencesUtils.getParams("token","").toString(),getDAOSession());
                shareDialog.dismiss();

            }
        });
        shareToWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleto = new Bundle();
                bundleto.putString("share","4");
                bundleto.putSerializable("qad",qaDataDetail);
                IntentUtils.getInstence().intent(AskDetailActivity.this, WXEntryActivity.class,bundleto);
                CommonUtils.addCoinAndUpdateInfo("1",activitysharedPreferencesUtils.getParams("token","").toString(),getDAOSession());
                shareDialog.dismiss();

            }
        });
        shareToMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyToast.getInstance(AskDetailActivity.this).show("暂不支持分享更多",Toast.LENGTH_SHORT);
                //shareToMoreInfo(asks.get(position));
                shareDialog.dismiss();

            }
        });


        Window window = shareDialog.getWindow();
        window.setWindowAnimations(R.style.ActionSheetDialogAnimation);
    }

    private int getWindowHeight() {
        Resources res = this.getResources();
        DisplayMetrics displayMetrics = res.getDisplayMetrics();
        return displayMetrics.heightPixels;
    }



    @Override
    public void loadError(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void loadDetail(QADataDetail qaDataDetail) {

        if (swpAskDetail.isRefreshing()){
            swpAskDetail.setRefreshing(false);
        }
        this.qaDataDetail = qaDataDetail;
        initAdapter();
    }

    @Override
    public void loadMoreComment(ArrayList<Comment> userComments) {

    }

    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void getComment(CommentDetail commentDetail) {

    }

    @Override
    public void deleteSuccess(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @OnClick(R.id.comment_in_detail)
    public void commentTo(View view){
        replyToWho.setText("回复给： " + qaDataDetail.getQaData().getUser_name());
        showPopWindow();
        popupInputMethod();
        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askDetailPresenter.sendQaComment(qaDataDetail.getQaData().getQa_id(),activitysharedPreferencesUtils.getParams("token","").toString(),editComment.getText().toString());
                editComment.setText("");
                askDetailPresenter.getQADetailData(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("qaId"));
                popupWindow.dismiss();
            }
        });
    }


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

    public void initMoreQAPopupWindow(){
        popviewQa = LayoutInflater.from(this).inflate(R.layout.qa_more_pop,null,false);
        popupWindowQa = new PopupWindow(popviewQa,LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindowQa.setBackgroundDrawable(new BitmapDrawable());
        popupWindowQa.setTouchable(true);
        popupWindowQa.setAnimationStyle(R.style.popupQAWindow_anim_style);
        popupWindowQa.setFocusable(true);
        popupWindowQa.setOutsideTouchable(true);
        popupWindowQa.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dismissQa = (RelativeLayout) popviewQa.findViewById(R.id.miss_pop_more);
        deleteContent = (TextView)popviewQa.findViewById(R.id.delete_content_this);
        copyContent = (TextView)popviewQa.findViewById(R.id.copy_content_this);

        dismissQa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindowQa.dismiss();

            }
        });
    }
    public void showQAPopWindow(){
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_ask_detail,null);
        popupWindowQa.showAtLocation(rootView, Gravity.CENTER,0,0);

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
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_ask_detail,null);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);

    }

    public void showMorePopWindow(){
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_ask_detail,null);
        morePopupWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            GSYVideoManager.instance().setNeedMute(true);
            return;
        }
        super.onBackPressed();

    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        askDetailPresenter.onDestroy();
        GSYVideoManager.releaseAllVideos();

    }





}
