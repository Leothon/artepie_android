package com.leothon.cogito.Mvp.View.Activity.AskDetailActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Adapter.AskAdapter;
import com.leothon.cogito.Adapter.AskDetailAdapter;
import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.AskDetail;
import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.AskActivity.AskActivityPresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class AskDetailActivity extends BaseActivity implements AskDetailContract.IAskDetailView ,SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.rv_ask_detail)
    RecyclerView rvAskDetail;
    @BindView(R.id.comment_in_detail)
    CardView commentIn;
    @BindView(R.id.swp_ask_detail)
    SwipeRefreshLayout swpAskDetail;
    private QADataDetail qaDataDetail;
    private AskDetailAdapter askDetailAdapter;

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
    private ArrayList<Comment> userComments;
    private UserEntity userEntity;

    private Intent intent;
    private Bundle bundle;

    private String uuid;

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
                        CommonUtils.makeText(AskDetailActivity.this,"内容已复制");
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
                        CommonUtils.makeText(AskDetailActivity.this,"内容已复制");
                    }
                });


            }
        });
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
        CommonUtils.makeText(this,msg);
    }

    @Override
    public void getComment(CommentDetail commentDetail) {

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

                Comment comment = new Comment();
                comment.setUser_name(userEntity.getUser_name());
                comment.setUser_icon(userEntity.getUser_icon());
                comment.setComment_q_time(CommonUtils.getNowTime());
                comment.setComment_q_like("0");
                comment.setComment_q_content(editComment.getText().toString());
                qaDataDetail.getComments().add(comment);
                askDetailAdapter.notifyDataSetChanged();
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



    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public BaseModel initModel() {
        return null;
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
