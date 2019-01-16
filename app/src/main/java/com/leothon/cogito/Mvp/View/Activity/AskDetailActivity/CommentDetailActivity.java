package com.leothon.cogito.Mvp.View.Activity.AskDetailActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.widget.TextView;

import com.leothon.cogito.Adapter.CommentDetailAdapter;
import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class CommentDetailActivity extends BaseActivity implements AskDetailContract.IAskDetailView,SwipeRefreshLayout.OnRefreshListener {

    private AskDetailPresenter askDetailPresenter;

    @BindView(R.id.swp_comment_detail)
    SwipeRefreshLayout swpCommentDetail;

    @BindView(R.id.comment_detail_rv)
    RecyclerView commentDetailRv;

    @BindView(R.id.comment_in_comment_detail)
    CardView commentInCommentDetail;
    private Intent intent;
    private Bundle bundle;
    private CommentDetailAdapter commentDetailAdapter;

    private CommentDetail commentDetail;
    private View popview;
    private PopupWindow popupWindow;
    private MaterialEditText editComment;
    private ImageView sendComment;
    private View dismiss;

    @Override
    public int initLayout() {
        return R.layout.activity_comment_detail;
    }

    @Override
    public void initData() {
        askDetailPresenter = new AskDetailPresenter(this);
        swpCommentDetail.setProgressViewOffset (false,100,300);
        swpCommentDetail.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);

    }
    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarSubTitle("");
        setToolbarTitle("");
        swpCommentDetail.setRefreshing(true);
        askDetailPresenter.loadCommentDetail(bundle.getString("commentId"),activitysharedPreferencesUtils.getParams("token","").toString());
        initPopupWindow();
    }

    private void initAdapter(){
        swpCommentDetail.setOnRefreshListener(this);
        commentDetailAdapter = new CommentDetailAdapter(commentDetail,this);
        final LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        commentDetailRv.setLayoutManager(mlinearLayoutManager);
        commentDetailRv.setAdapter(commentDetailAdapter);
//        commentDetailRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                int lastVisibleItem = mlinearLayoutManager.findLastVisibleItemPosition();
//                int firstVisibleItem = mlinearLayoutManager.findFirstVisibleItemPosition();
//                if (GSYVideoManager.instance().getPlayPosition() >= 0){
//                    int position = GSYVideoManager.instance().getPlayPosition();
//                    if (GSYVideoManager.instance().getPlayTag().equals(AskAdapter.TAG) && (position < firstVisibleItem || position > lastVisibleItem)){
//                        if (GSYVideoManager.isFullState(AskDetailActivity.this)){
//                            return;
//                        }
//
//                        GSYVideoManager.releaseAllVideos();
//                        askDetailAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//        });

        commentDetailAdapter.setOnClickAddLikeCommentDetail(new CommentDetailAdapter.AddLikeCommentDetailOnClickListener() {
            @Override
            public void addLikeCommentDetailClickListener(boolean isLike, String commentId) {
                Log.e( "点击之后执行","评论");
                if (isLike){
                    askDetailPresenter.removeLikeComment(activitysharedPreferencesUtils.getParams("token","").toString(),commentId);
                }else {
                    askDetailPresenter.addLikeComment(activitysharedPreferencesUtils.getParams("token","").toString(),commentId);
                }
            }
        });

        commentDetailAdapter.setOnClickAddLikeCommentReply(new CommentDetailAdapter.AddLikeCommentReplyOnClickListener() {
            @Override
            public void addLikeCommentReplyClickListener(boolean isLike, String replyId) {
                Log.e( "点击之后执行","回复");
                if (isLike){
                    askDetailPresenter.removeLikeReply(activitysharedPreferencesUtils.getParams("token","").toString(),replyId);
                }else {
                    askDetailPresenter.addLikeReply(activitysharedPreferencesUtils.getParams("token","").toString(),replyId);
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        askDetailPresenter.loadCommentDetail(bundle.getString("commentId"),activitysharedPreferencesUtils.getParams("token","").toString());
    }
    @Override
    public void getComment(CommentDetail commentDetail) {
        this.commentDetail = commentDetail;
        if (swpCommentDetail.isRefreshing()){
            swpCommentDetail.setRefreshing(false);
        }
        initAdapter();

    }

    @OnClick(R.id.comment_in_comment_detail)
    public void CommentSendToCommentDetail(View view){
        //TODO 发送评论
        showPopWindow();
        popupInputMethod();
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
//                if (!editComment.getText().toString().equals("")){
//                    String comment = editComment.getText().toString();
//                    Comment userComment = new Comment();
//                    userComment.setUser_icon(userEntity.getUser_icon());
//                    userComment.setUser_name(userEntity.getUser_name());
//                    userComment.setComment_q_content(comment);
//                    userComments.add(0,userComment);
//                    qaDataDetail.setComments(userComments);
//                    askDetailAdapter.notifyItemInserted(0);
//                    askDetailAdapter.notifyItemRangeChanged(0,userComments.size());
//                    rvAskDetail.scrollToPosition(0);
//                    popupWindow.dismiss();
//                    editComment.setText("");
//                }else {
//                    CommonUtils.makeText(AskDetailActivity.this,"请输入评论内容");
//                }

            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

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
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_comment_detail,null);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);
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

    @Override
    public void loadDetail(QADataDetail qaDetail) {

    }

    @Override
    public void loadMoreComment(ArrayList<Comment> userComments) {

    }

    @Override
    public void showInfo(String msg) {

        CommonUtils.makeText(this,msg);
    }



}
