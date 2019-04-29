package com.leothon.cogito.Mvp.View.Activity.QAHisActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Adapter.AskAdapter;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.ArticleHisActivity.ArticleHisActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;

import butterknife.BindView;

public class QAHisActivity extends BaseActivity implements QAHisContract.IQAHisView, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rv_upload)
    RecyclerView rvUpload;
    @BindView(R.id.swp_upload)
    SwipeRefreshLayout swpUpload;
    private AskAdapter uploadAdapter;
    private ArrayList<QAData> askArrayList;

    private UserEntity userEntity;

    private Intent intent;
    private Bundle bundle;

    private TextView deleteContent;
    private TextView copyContent;
    private RelativeLayout dismiss;
    private View popview;
    private PopupWindow popupWindow;

    private QAHisPresenter qaHisPresenter;

    @Override
    public int initLayout() {
        return R.layout.activity_upload;
    }
    @Override
    public void initData() {
        swpUpload.setProgressViewOffset (false,100,300);
        swpUpload.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        String uuid = tokenValid.getUid();
        qaHisPresenter = new QAHisPresenter(this);
        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
        intent = getIntent();
        bundle = intent.getExtras();
        initMorePopupWindow();
    }
    @Override
    public void initView() {
        getToolbarSubTitle().setTextColor(Color.parseColor("#f26402"));
        if (bundle.get("userId").equals(userEntity.getUser_id())){
            setToolbarTitle("我发布的内容");
            setToolbarSubTitle("我的文章");

        }else {
            setToolbarTitle("他发布的内容");
            setToolbarSubTitle("他的文章");
        }

        swpUpload.setRefreshing(true);
        qaHisPresenter.getAskData(bundle.getString("userId"));


        getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundleto = new Bundle();
                bundleto.putString("userId",bundle.getString("userId"));
                IntentUtils.getInstence().intent(QAHisActivity.this,ArticleHisActivity.class,bundleto);
            }
        });
    }

    @Override
    public void loadAskData(ArrayList<QAData> qaData) {
        askArrayList = qaData;
        if (swpUpload.isRefreshing()){
            swpUpload.setRefreshing(false);
        }

        initAdapter();
    }

    @Override
    public void onRefresh() {
        qaHisPresenter.getAskData(bundle.getString("userId"));
    }

    @Override
    public void loadAskMoreData(ArrayList<QAData> qaData) {
        if (swpUpload.isRefreshing()){
            swpUpload.setRefreshing(false);
        }
        for (int i = 0;i < qaData.size(); i++){
            askArrayList.add(qaData.get(i));

        }
        uploadAdapter.notifyDataSetChanged();
    }

    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void deleteSuccess(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    private void initAdapter(){
        swpUpload.setOnRefreshListener(this);
        uploadAdapter = new AskAdapter(this,askArrayList,"通知",true);
        final LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvUpload.setLayoutManager(mlinearLayoutManager);
        rvUpload.setAdapter(uploadAdapter);
        rvUpload.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int lastVisibleItem = mlinearLayoutManager.findLastVisibleItemPosition();
                int firstVisibleItem = mlinearLayoutManager.findFirstVisibleItemPosition();
                if (GSYVideoManager.instance().getPlayPosition() >= 0){
                    int position = GSYVideoManager.instance().getPlayPosition();
                    if (GSYVideoManager.instance().getPlayTag().equals(AskAdapter.TAG) && (position < firstVisibleItem || position > lastVisibleItem)){
                        if (GSYVideoManager.isFullState(QAHisActivity.this)){
                            return;
                        }

                        GSYVideoManager.releaseAllVideos();
                        uploadAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        uploadAdapter.setOnClickaddLike(new AskAdapter.addLikeOnClickListener() {
            @Override
            public void addLikeClickListener(boolean isLike,String qaId) {
                if (isLike){
                    qaHisPresenter.removeLiked(activitysharedPreferencesUtils.getParams("token","").toString(),qaId);
                }else {
                    qaHisPresenter.addLiked(activitysharedPreferencesUtils.getParams("token","").toString(),qaId);
                }
            }
        });
        uploadAdapter.setDeleteQaOnClickListener(new AskAdapter.DeleteQaOnClickListener() {
            @Override
            public void deleteQaClickListener(String qaId, String qaUserId, String content, int position) {

                showPopWindow();
                deleteContent.setVisibility(View.VISIBLE);
                copyContent.setVisibility(View.VISIBLE);
                deleteContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qaHisPresenter.deleteQa(activitysharedPreferencesUtils.getParams("token","").toString(),qaId);
                        askArrayList.remove(position);
                        uploadAdapter.notifyDataSetChanged();
                        popupWindow.dismiss();
                    }
                });

                copyContent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        cm.setText(content);
                        MyToast.getInstance(QAHisActivity.this).show("内容已复制",Toast.LENGTH_SHORT);
                        popupWindow.dismiss();
                    }
                });
            }
        });

        rvUpload.addOnScrollListener(new loadMoreDataListener(mlinearLayoutManager) {
            @Override
            public void onLoadMoreData(int currentPage) {
                swpUpload.setRefreshing(true);
                qaHisPresenter.getAskMoreData(currentPage * 15,bundle.getString("userId"));
            }
        });
    }

    public void initMorePopupWindow(){
        popview = LayoutInflater.from(this).inflate(R.layout.qa_more_pop,null,false);
        popupWindow = new PopupWindow(popview, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchable(true);
        popupWindow.setAnimationStyle(R.style.popupQAWindow_anim_style);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dismiss = (RelativeLayout) popview.findViewById(R.id.miss_pop_more);
        deleteContent = (TextView)popview.findViewById(R.id.delete_content_this);
        copyContent = (TextView)popview.findViewById(R.id.copy_content_this);

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

            }
        });
    }
    public void showPopWindow(){
        View rootView = LayoutInflater.from(this).inflate(R.layout.fragment_ask,null);
        popupWindow.showAtLocation(rootView, Gravity.CENTER,0,0);

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
        qaHisPresenter.onDestroy();
        GSYVideoManager.releaseAllVideos();

    }
}
