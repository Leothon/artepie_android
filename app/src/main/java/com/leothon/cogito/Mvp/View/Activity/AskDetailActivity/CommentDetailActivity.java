package com.leothon.cogito.Mvp.View.Activity.AskDetailActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;


import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import com.leothon.cogito.Adapter.CommentDetailAdapter;
import com.leothon.cogito.Bean.Comment;
import com.leothon.cogito.Bean.Reply;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.DTO.CommentDetail;
import com.leothon.cogito.DTO.QADataDetail;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
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

    private ArrayList<Reply> replies;
    @Override
    public int initLayout() {
        return R.layout.activity_comment_detail;
    }

    @Override
    public void initData() {
        askDetailPresenter = new AskDetailPresenter(this);
        swpCommentDetail.setProgressViewOffset (false,100,300);
        swpCommentDetail.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        uuid = tokenValid.getUid();
        replies = new ArrayList<>();
        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
    }
    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarSubTitle("去往原问题");
        setToolbarTitle("");
        swpCommentDetail.setRefreshing(true);
        askDetailPresenter.loadCommentDetail(bundle.getString("commentId"),activitysharedPreferencesUtils.getParams("token","").toString());
        initReplyPopupWindow();
        initMorePopupWindow();


    }

    private void initAdapter(){
        swpCommentDetail.setOnRefreshListener(this);
        commentDetailAdapter = new CommentDetailAdapter(commentDetail,this);
        final LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        commentDetailRv.setLayoutManager(mlinearLayoutManager);
        commentDetailRv.setAdapter(commentDetailAdapter);

        commentDetailRv.addOnScrollListener(new loadMoreDataListener(mlinearLayoutManager) {
            @Override
            public void onLoadMoreData(int currentPage) {
                swpCommentDetail.setRefreshing(true);
                askDetailPresenter.loadMoreCommentDetail(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("commentId"),currentPage * 15);

            }
        });
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
                if (isLike){
                    askDetailPresenter.removeLikeReply(activitysharedPreferencesUtils.getParams("token","").toString(),replyId);
                }else {
                    askDetailPresenter.addLikeReply(activitysharedPreferencesUtils.getParams("token","").toString(),replyId);
                }
            }
        });
        commentDetailAdapter.setSendDetailReplyOnClickListener(new CommentDetailAdapter.SendDetailReplyOnClickListener() {
            @Override
            public void sendDetailReplyClickListener(final String commentId, final String toUserId, String toUsername) {
                replyToWho.setText("回复给： " + toUsername);
                showPopWindow();
                popupInputMethod();
                sendComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askDetailPresenter.sendReply(commentId,activitysharedPreferencesUtils.getParams("token","").toString(),toUserId,editComment.getText().toString());
                        editComment.setText("");
                        Reply reply = new Reply();
                        reply.setUser_icon(userEntity.getUser_icon());
                        reply.setUser_name(userEntity.getUser_name());
                        reply.setTo_user_name(commentDetail.getComment().getUser_name());
                        reply.setReply_comment(editComment.getText().toString());
                        reply.setReply_time(CommonUtils.getNowTime());
                        reply.setReply_like("0");
                        commentDetail.getReplies().add(reply);
                        commentDetailAdapter.notifyDataSetChanged();
                        askDetailPresenter.loadCommentDetail(commentDetail.getComment().getComment_q_id(),activitysharedPreferencesUtils.getParams("token","").toString());
                        popupWindow.dismiss();
                    }
                });
            }
        });

        commentDetailAdapter.setDeleteDetailCommentOnClickListener(new CommentDetailAdapter.DeleteDetailCommentOnClickListener() {
            @Override
            public void deleteDetailCommentClickListener(final String commentId, String commentUserId, final String content) {
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
                        commentDetail.getComment().setComment_q_content("该内容已被删除");
                        commentDetailAdapter.notifyDataSetChanged();
                        morePopupWindow.dismiss();
                    }
                });

                copyComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(content);
                        MyToast.getInstance(CommentDetailActivity.this).show("内容已复制",Toast.LENGTH_SHORT);
                        morePopupWindow.dismiss();
                    }
                });
            }
        });
        commentDetailAdapter.setDeleteDetailReplyOnClickListener(new CommentDetailAdapter.DeleteDetailReplyOnClickListener() {
            @Override
            public void deleteDetailReplyClickListener(final String replyId, String replyUserId, final String content, final int replyPosition) {
                showMorePopWindow();
                if (replyUserId.equals(uuid)){
                    deleteComment.setVisibility(View.VISIBLE);
                }else {
                    deleteComment.setVisibility(View.GONE);
                }

                deleteComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        askDetailPresenter.deleteReply(replyId,activitysharedPreferencesUtils.getParams("token","").toString());
                        commentDetail.getReplies().get(replyPosition).setReply_comment("该内容已被删除");
                        commentDetailAdapter.notifyDataSetChanged();
                        morePopupWindow.dismiss();
                    }
                });

                copyComment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(content);
                        MyToast.getInstance(CommentDetailActivity.this).show("内容已复制",Toast.LENGTH_SHORT);
                        morePopupWindow.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public void onRefresh() {
        askDetailPresenter.loadCommentDetail(bundle.getString("commentId"),activitysharedPreferencesUtils.getParams("token","").toString());
    }
    @Override
    public void getComment(final CommentDetail commentDetail) {
        this.commentDetail = commentDetail;
        this.replies = commentDetail.getReplies();
        if (swpCommentDetail.isRefreshing()){
            swpCommentDetail.setRefreshing(false);
        }
        String nextId = commentDetail.getComment().getComment_q_qa_id();
        if (nextId.substring(0,2).equals("qa")){
            getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("qaId",commentDetail.getComment().getComment_q_qa_id());
                    IntentUtils.getInstence().intent(CommentDetailActivity.this,AskDetailActivity.class,bundle);
                }
            });
        }else {
            setToolbarSubTitle("");
        }
        initAdapter();

    }

    @Override
    public void getMoreComment(ArrayList<Reply> replies) {

        if (swpCommentDetail.isRefreshing()){
            swpCommentDetail.setRefreshing(false);
        }
        for (int i = 0;i < replies.size(); i++){
            this.replies.add(replies.get(i));

        }
        commentDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteSuccess(String msg) {

    }

    @OnClick(R.id.comment_in_comment_detail)
    public void CommentSendToCommentDetail(View view){

        replyToWho.setText("回复给： " + commentDetail.getComment().getUser_name());
        //TODO 发送评论
        showPopWindow();
        popupInputMethod();

        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askDetailPresenter.sendReply(commentDetail.getComment().getComment_q_id(),activitysharedPreferencesUtils.getParams("token","").toString(),commentDetail.getComment().getComment_q_user_id(),editComment.getText().toString());

                Reply reply = new Reply();
                reply.setUser_icon(userEntity.getUser_icon());
                reply.setUser_name(userEntity.getUser_name());
                reply.setTo_user_name(commentDetail.getComment().getUser_name());
                reply.setReply_comment(editComment.getText().toString());
                reply.setReply_time(CommonUtils.getNowTime());
                reply.setReply_like("0");
                commentDetail.getReplies().add(reply);
                commentDetailAdapter.notifyDataSetChanged();
                editComment.setText("");
                askDetailPresenter.loadCommentDetail(commentDetail.getComment().getComment_q_id(),activitysharedPreferencesUtils.getParams("token","").toString());
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
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_comment_detail,null);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);
    }

    public void showMorePopWindow(){
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_comment_detail,null);
        morePopupWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);
    }


    @Override
    public void loadError(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void loadDetail(QADataDetail qaDetail) {

    }

    @Override
    public void loadMoreComment(ArrayList<Comment> userComments) {

    }

    @Override
    public void showInfo(String msg) {

        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        askDetailPresenter.onDestroy();
    }
}
