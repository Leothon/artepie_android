package com.leothon.cogito.Mvp.View.Activity.SelectClassActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;


import android.os.Bundle;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Adapter.SelectClassAdapter;
import com.leothon.cogito.Bean.ClassDetailList;
import com.leothon.cogito.Constants;
import com.leothon.cogito.DTO.ClassDetail;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.UploadClassActivity.UploadClassActivity;
import com.leothon.cogito.Mvp.View.Activity.UploadDetailClassActivity.UploadClassDetailActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;
import com.leothon.cogito.wxapi.WXEntryActivity;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.8.4
 */
public class SelectClassActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener,SelectClassContract.ISelectClassView {


    @BindView(R.id.swp_select_class)
    SwipeRefreshLayout swpSelect;
    @BindView(R.id.rv_select_class)
    RecyclerView rvSelect;

    @BindView(R.id.select_bar)
    CardView selectBar;


    @BindView(R.id.edit_class_btn)
    CardView editClassBtn;
    @BindView(R.id.edit_my_class)
    TextView editMyClass;
    @BindView(R.id.upload_my_class)
    TextView uploadMyClass;

    private ClassDetail classDetail;
    private LinearLayoutManager linearLayoutManager;
    private Intent intent;
    private Bundle bundle;
    private SelectClassAdapter selectClassAdapter;
    private SelectClassPresenter selectClassPresenter;
    private static int THRESHOLD_OFFSET = 10;
    private String userId;

    private ArrayList<ClassDetailList> classDetailLists;
    private QQShareListener qqShareListener;
    private Tencent mTencent;

    private View dismissShare;
    private RelativeLayout shareToQQ;
    private RelativeLayout shareToFriendCircle;
    private RelativeLayout shareToWeChat;
    private RelativeLayout shareToMore;

    private PopupWindow sharePopup;
    private ClassDetail shareClassDetail;

    private View popUPView;

    @Override
    public int initLayout() {
        return R.layout.activity_select_class;
    }

    @Override
    public void initData() {
        mTencent = Tencent.createInstance(Constants.APP_ID,SelectClassActivity.this.getApplicationContext());
        qqShareListener = new QQShareListener();
        selectClassPresenter = new SelectClassPresenter(this);
        swpSelect.setProgressViewOffset (false,100,300);
        swpSelect.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);
        userId = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString()).getUid();
        //initSharePopupWindow();
        classDetailLists = new ArrayList<>();
        showShareDialog();
    }
    @Override
    public void initView() {
        StatusBarUtils.transparencyBar(this);
        intent = getIntent();
        bundle = intent.getExtras();
        selectClassPresenter.getClassDetail(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("classId"));
        swpSelect.setRefreshing(true);

        setToolbarSubTitle("");

    }

    @Override
    public void getClassDetailInfo(ClassDetail classDetail) {
        if (swpSelect.isRefreshing()){
            swpSelect.setRefreshing(false);
        }
        if (classDetail.getTeaClasss().getSelectauthorid().equals(userId)){

            editClassBtn.setVisibility(View.VISIBLE);
        }
        setToolbarTitle(classDetail.getTeaClasss().getSelectlisttitle());
        this.classDetail = classDetail;
        this.classDetailLists = classDetail.getClassDetailLists();
        initAdapter();
    }

    @Override
    public void getMoreClassDetailInfo(ArrayList<ClassDetailList> classDetailLists) {

        if (swpSelect.isRefreshing()){
            swpSelect.setRefreshing(false);
        }
        for (int i = 0;i < classDetailLists.size(); i++){
            this.classDetailLists.add(classDetailLists.get(i));

        }
        selectClassAdapter.notifyDataSetChanged();
    }


    @OnClick(R.id.edit_my_class)
    public void editMyClass(View view){
        Bundle bundleto = new Bundle();
        bundleto.putString("type","edit");
        bundleto.putString("classId",classDetail.getTeaClasss().getSelectId());
        IntentUtils.getInstence().intent(SelectClassActivity.this, UploadClassActivity.class,bundleto);
    }

    @OnClick(R.id.upload_my_class)
    public void uploadMyClass(View view){
        Bundle bundleto = new Bundle();
        bundleto.putString("classId",classDetail.getTeaClasss().getSelectId());
        bundleto.putString("title",classDetail.getTeaClasss().getSelectlisttitle());
        IntentUtils.getInstence().intent(SelectClassActivity.this,UploadClassDetailActivity.class,bundleto);
    }

    @Override
    public void showInfo(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void deleteSuccess(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
        selectClassPresenter.getClassDetail(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("classId"));
    }

    public void initAdapter(){
        swpSelect.setOnRefreshListener(this);
        selectClassAdapter = new SelectClassAdapter(classDetail,this);
        initHeadView(selectClassAdapter);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayout.VERTICAL,false);
        rvSelect.setLayoutManager(linearLayoutManager);
        rvSelect.setAdapter(selectClassAdapter);
        rvSelect.addOnScrollListener(new loadMoreDataListener(linearLayoutManager) {
            @Override
            public void onLoadMoreData(int currentPage) {
                swpSelect.setRefreshing(true);
                selectClassPresenter.loadMoreClassDetail(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("classId"),currentPage * 20);
            }
        });
        rvSelect.addOnScrollListener(new RecyclerView.OnScrollListener() {


            boolean controlVisible = true;
            int scrollDistance = 0;
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                if (controlVisible && scrollDistance > THRESHOLD_OFFSET){//手指上滑即Scroll向下滚动的时候，dy为正
                    selectBar.setVisibility(View.VISIBLE);
                    selectBar.setTranslationY(CommonUtils.getStatusBarHeight(SelectClassActivity.this) - CommonUtils.dip2px(SelectClassActivity.this,3));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                        StatusBarUtils.setStatusBarColor(SelectClassActivity.this,R.color.white);
                    }
                    selectBar.setVisibility(View.VISIBLE);
                    controlVisible = false;
                    scrollDistance = 0;
                }else if (!controlVisible && scrollDistance < -THRESHOLD_OFFSET){//手指下滑即Scroll向上滚动的时候，dy为负
                    selectBar.setVisibility(View.GONE);
                    StatusBarUtils.transparencyBar(SelectClassActivity.this);
                    controlVisible = true;
                    scrollDistance = 0;
                }

                //当scrollDistance累计到隐藏（显示)ToolBar之后，如果Scroll向下（向上）滚动，则停止对scrollDistance的累加
                //直到Scroll开始往反方向滚动，再次启动scrollDistance的累加
                if ((controlVisible && dy > 0) || (!controlVisible && dy < 0)){
                    scrollDistance += dy;
                }

            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

        });
        selectClassAdapter.setFavOnClickListener(new SelectClassAdapter.favOnClickListener() {
            @Override
            public void favClickListener(boolean isFaved,String classId) {
                if (isFaved){
                    selectClassPresenter.setunFavClass(activitysharedPreferencesUtils.getParams("token","").toString(),classId);
                }else {
                    selectClassPresenter.setfavClass(activitysharedPreferencesUtils.getParams("token","").toString(),classId);
                }
            }
        });


        selectClassAdapter.setOnDeleteClickListener(new SelectClassAdapter.OnDeleteClickListener() {
            @Override
            public void deleteClickListener(String classdId) {
                loadDeleteDialog(classdId);
            }
        });
        selectClassAdapter.setQquiListener(new SelectClassAdapter.QQUIListener() {
            @Override
            public void setQQUIListener(ClassDetail classDetail) {
                //showShareWindow();
                shareDialog.show();
                shareClassDetail = classDetail;
                //shareToQQClass(classDetail);
            }
        });
    }

    public void initHeadView(SelectClassAdapter selectClassAdapter){
        View headView = View.inflate(this,R.layout.select_class_background,null);
        selectClassAdapter.addHeadView(headView);
    }

    private void loadDeleteDialog(final String classdId){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setMessage("是否删除本节课程，删除后不可恢复")
                .setTitle("提醒")
                .setSingle(false)
                .setPositive("取消")
                .setNegtive("删除")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                        selectClassPresenter.deleteClassDetail(classdId);

                    }

                })
                .show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        selectClassPresenter.onDestroy();
    }

    @Override
    public void onRefresh() {
        selectClassPresenter.getClassDetail(activitysharedPreferencesUtils.getParams("token","").toString(),bundle.getString("classId"));
    }

    private void shareToQQClass(ClassDetail classDetail)
    {
        Bundle bundle = new Bundle();
        //这条分享消息被好友点击后的跳转URL。
        bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.artepie.cn");
        //分享的标题。注：PARAM_TITLE、PARAM_IMAGE_URL、PARAM_SUMMARY不能全为空，最少必须有一个是有值的。
        bundle.putString(QQShare.SHARE_TO_QQ_TITLE, classDetail.getTeaClasss().getSelectlisttitle());
        //分享的图片URL
        bundle.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, classDetail.getTeaClasss().getSelectbackimg());
        //分享的消息摘要，最长50个字
        bundle.putString(QQShare.SHARE_TO_QQ_SUMMARY, classDetail.getTeaClasss().getSelectdesc());
        //手Q客户端顶部，替换“返回”按钮文字，如果为空，用返回代替
        //bundle.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "??我在测试");
        //标识该消息的来源应用，值为应用名称+AppId。
        bundle.putString(QQShare.SHARE_TO_QQ_APP_NAME, "艺派" + Constants.APP_ID);
        mTencent.shareToQQ(this, bundle , qqShareListener);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);

        if (null != mTencent)
            mTencent.onActivityResult(requestCode,resultCode,data);
    }


    private class QQShareListener implements IUiListener {
        @Override
        public void onCancel() {

        }

        @Override public void onError(UiError uiError) {
        }

        @Override public void onComplete(Object o) {

            MyToast.getInstance(SelectClassActivity.this).show("分享成功！",Toast.LENGTH_SHORT);
        }
    }


//    private void initSharePopupWindow(){
//        popUPView = LayoutInflater.from(this).inflate(R.layout.popup_share,null,false);
//        sharePopup = new PopupWindow(popUPView, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
//        sharePopup.setBackgroundDrawable(new BitmapDrawable());
//        sharePopup.setTouchable(true);
//        sharePopup.setAnimationStyle(R.style.popupWindow_anim_style);
//        sharePopup.setFocusable(true);
//        sharePopup.setOutsideTouchable(true);
//        sharePopup.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//
//        dismissShare = (View) popUPView.findViewById(R.id.dismiss_share);
//        shareToQQ = (RelativeLayout)popUPView.findViewById(R.id.share_class_to_qq);
//        shareToFriendCircle = (RelativeLayout)popUPView.findViewById(R.id.share_class_to_circle);
//        shareToWeChat = (RelativeLayout)popUPView.findViewById(R.id.share_class_to_wechat);
//        shareToMore = (RelativeLayout)popUPView.findViewById(R.id.share_class_to_more);
//
//        dismissShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sharePopup.dismiss();
//                dismissShare.setBackgroundColor(Color.parseColor("#00b3b3b3"));
//            }
//        });
//
//        shareToQQ.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shareToQQClass(shareClassDetail);
//                sharePopup.dismiss();
//                dismissShare.setBackgroundColor(Color.parseColor("#00b3b3b3"));
//            }
//        });
//        shareToFriendCircle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bundle bundleto = new Bundle();
//                bundleto.putString("flag","1");
//                bundleto.putSerializable("data",shareClassDetail);
//                IntentUtils.getInstence().intent(SelectClassActivity.this, WXEntryActivity.class,bundleto);
//                sharePopup.dismiss();
//                dismissShare.setBackgroundColor(Color.parseColor("#00b3b3b3"));
//            }
//        });
//        shareToWeChat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bundle bundleto = new Bundle();
//                bundleto.putString("flag","2");
//                bundleto.putSerializable("data",shareClassDetail);
//                IntentUtils.getInstence().intent(SelectClassActivity.this, WXEntryActivity.class,bundleto);
//                sharePopup.dismiss();
//                dismissShare.setBackgroundColor(Color.parseColor("#00b3b3b3"));
//            }
//        });
//        shareToMore.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                shareToMoreInfo(shareClassDetail);
//                sharePopup.dismiss();
//                dismissShare.setBackgroundColor(Color.parseColor("#00b3b3b3"));
//            }
//        });
//    }
    private BottomSheetDialog shareDialog;
    private BottomSheetBehavior shareDialogBehavior;

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
            @Override
            public void onClick(View view) {
                shareToQQClass(shareClassDetail);
                shareDialog.dismiss();

            }
        });
        shareToFriendCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleto = new Bundle();
                bundleto.putString("flag","1");
                bundleto.putSerializable("data",shareClassDetail);
                IntentUtils.getInstence().intent(SelectClassActivity.this, WXEntryActivity.class,bundleto);
                CommonUtils.addCoinAndUpdateInfo("3",activitysharedPreferencesUtils.getParams("token","").toString(),getDAOSession());
                MyToast.getInstance(SelectClassActivity.this).show("已获得3个艺币",Toast.LENGTH_SHORT);
                shareDialog.dismiss();

            }
        });
        shareToWeChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleto = new Bundle();
                bundleto.putString("flag","2");
                bundleto.putSerializable("data",shareClassDetail);
                IntentUtils.getInstence().intent(SelectClassActivity.this, WXEntryActivity.class,bundleto);
                CommonUtils.addCoinAndUpdateInfo("3",activitysharedPreferencesUtils.getParams("token","").toString(),getDAOSession());
                MyToast.getInstance(SelectClassActivity.this).show("已获得3个艺币",Toast.LENGTH_SHORT);
                shareDialog.dismiss();

            }
        });
        shareToMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareToMoreInfo(shareClassDetail);
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
//    public void showShareWindow(){
//        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_select_class,null);
//        sharePopup.showAtLocation(rootView, Gravity.BOTTOM,0,0);
//        dismissShare.setBackgroundColor(Color.parseColor("#20b3b3b3"));
//    }

    private void shareToMoreInfo(ClassDetail classDetail) {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);
        share_intent.setType("text/plain");
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "艺派");
        share_intent.putExtra(Intent.EXTRA_TEXT, "我正在艺派APP学习课程" + classDetail.getTeaClasss().getSelectlisttitle() + "\n戳我查看：http://www.artepie.cn");
        share_intent = Intent.createChooser(share_intent, "分享");
        startActivity(share_intent);
    }



}
