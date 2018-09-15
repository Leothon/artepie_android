package com.leothon.cogito.Mvp.View.Activity.AskDetailActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import com.leothon.cogito.Adapter.AskAdapter;
import com.leothon.cogito.Adapter.AskDetailAdapter;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.VideoCommentAdapter;
import com.leothon.cogito.Bean.AskDetail;
import com.leothon.cogito.Bean.UserComment;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class AskDetailActivity extends BaseActivity {


    @BindView(R.id.rv_ask_detail)
    RecyclerView rvAskDetail;
    @BindView(R.id.comment_in_detail)
    CardView commentIn;
    private AskDetail askDetail;
    private AskDetailAdapter askDetailAdapter;

    private View popview;
    private PopupWindow popupWindow;

    private MaterialEditText editComment;
    private ImageView sendComment;
    private View dismiss;
    private ArrayList<UserComment> userComments;

    private Intent intent;
    private Bundle bundle;
    private String[] name = {"TomCruise","Leo张","樱君","爱笑的包子","沐锦曦","黑木桃","苏墨_Echo","良诚恐moon","蝶馨向梦","凌申洲_023"};
    private String[] comment = {"Hello！",
            "前排",
            "你的声音真好听",
            "赞，能不能教我唱歌",
            "@IJU_22 听歌来",
            "点赞",
            "已截图",
            "直奔评论区",
            "谁有阎维文老师的课程？",
            "人美歌甜"};
    @Override
    public int initLayout() {
        return R.layout.activity_ask_detail;
    }

    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarSubTitle("");
        setToolbarTitle("");
        loadFalseData();
        initAdapter();
        initPopupWindow();
    }

    private void loadFalseData(){
        askDetail = new AskDetail();
        askDetail.setUsericon(bundle.getString("icon"));
        askDetail.setUsername(bundle.getString("name"));
        askDetail.setUserdes(bundle.getString("desc"));
        askDetail.setContent(bundle.getString("content"));
        askDetail.setCoverurl(bundle.getString("cover"));
        askDetail.setVideourl(bundle.getString("video"));
        askDetail.setLike(bundle.getString("like"));
        askDetail.setComment(bundle.getString("comment"));
        userComments = new ArrayList<>();
        for (int i = 0;i < 10;i++){
            UserComment userComment = new UserComment();
            userComment.setUsername(name[i]);
            userComment.setUsericon("https://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=https%3A%2F%2Ftimgsa.baidu.com%2Ftimg%3Fimage%26quality%3D80%26size%3Db9999_10000%26sec%3D1534355965654%26di%3D80b2e428590ce5cef81a1060a5ae48d4%26imgtype%3D0%26src%3Dhttp%253A%252F%252Fimgsa.baidu.com%252Fbaike%252Fpic%252Fitem%252Fc2fdfc039245d6889a29f356a8c27d1ed21b241c.jpg&thumburl=https%3A%2F%2Fss0.bdstatic.com%2F70cFvHSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D1522657165%2C2769090755%26fm%3D11%26gp%3D0.jpg");
            userComment.setUsercomment(comment[i]);
            userComments.add(userComment);
        }
        askDetail.setUserComments(userComments);
    }

    private void initAdapter(){
        askDetailAdapter = new AskDetailAdapter(askDetail,this);
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
    }

    @OnClick(R.id.comment_in_detail)
    public void commentTo(View view){
        showPopWindow();
        popupInputMethod();
    }
    @Override
    public void initData() {

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
                    askDetail.setUserComments(userComments);
                    askDetailAdapter.notifyItemInserted(0);
                    askDetailAdapter.notifyItemRangeChanged(0,userComments.size());
                    rvAskDetail.scrollToPosition(0);
                    popupWindow.dismiss();
                    editComment.setText("");
                }else {
                    CommonUtils.makeText(AskDetailActivity.this,"请输入评论内容");
                }

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
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_player,null);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM,0,0);
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
