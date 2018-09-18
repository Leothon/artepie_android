package com.leothon.cogito.Mvp.View.Activity.ActivityActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.ActivityEnsure.ActivityEnsureActivity;
import com.leothon.cogito.Mvp.View.Activity.TeacherActivity.TeacherActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.View.ObserveScrollView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.8.5
 */
public class ActivityActivity extends BaseActivity implements ObserveScrollView.ScrollListener{


    @BindView(R.id.activity_back_img)
    ImageView activityBackground;
    @BindView(R.id.activity_title_list)
    TextView activityTitle;
    @BindView(R.id.activity_time)
    TextView activityTime;
    @BindView(R.id.activity_address)
    TextView activityAddress;
    @BindView(R.id.activity_phone)
    TextView activityPhone;
    @BindView(R.id.activity_content_img)
    ImageView activityContentImg;
    @BindView(R.id.activity_buy_price)
    TextView activityBuyPrice;
    @BindView(R.id.activity_buy_btn)
    Button activityBuyBtn;
    @BindView(R.id.activity_bar)
    CardView activityBar;
    @BindView(R.id.obser_Scr)
    ObserveScrollView obserScr;
    private Intent intent;
    private Bundle bundle;
    private String lastPrice;
    @Override
    public int initLayout() {
        return R.layout.activity_activity;
    }

    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();
        StatusBarUtils.transparencyBar(this);
        String url = bundle.getString("url");
        String title = bundle.getString("title");
        ImageLoader.loadImageViewwithError(this,url,activityBackground,R.drawable.defalutimg);
        activityTitle.setText(title);
        obserScr.setScrollListener(this);
        setToolbarTitle(title);
        setToolbarSubTitle("");
        activityTime.setText("时间：2018-9-15 14:00");
        activityAddress.setText("地点：北京海淀区中关村中路95号");
        activityPhone.setText("联系电话：13500713926");
        lastPrice = "656";
        activityBuyPrice.setText("￥"+lastPrice);
    }

    @OnClick(R.id.activity_buy_btn)
    public void buyClass(View view){
        //TODO 确认信息
        Bundle bundlein = new Bundle();
        bundlein.putString("imgurl",bundle.getString("url"));
        bundlein.putString("title",bundle.getString("title"));
        bundlein.putString("des","");
        bundlein.putString("price",lastPrice);
        bundlein.putString("type","activity");
        bundlein.putString("time",activityTime.getText().toString());
        bundlein.putString("address",activityAddress.getText().toString());
        bundlein.putString("count","");
        IntentUtils.getInstence().intent(ActivityActivity.this, ActivityEnsureActivity.class,bundlein);
    }
    /**
     * 滑动监听scrollview的位置变化
     * l表示滑动后x值，t表示滑动后y值
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    public void scrollOrientation(int l, int t, int oldl, int oldt) {
        if (t > 400 && oldt < t){
            activityBar.setTranslationY(CommonUtils.getStatusBarHeight(this) - 5);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                StatusBarUtils.setStatusBarColor(ActivityActivity.this,R.color.white);
            }
            activityBar.setVisibility(View.VISIBLE);
        }else if (t <= 400 && oldt >= t){
            activityBar.setVisibility(View.GONE);
            StatusBarUtils.transparencyBar(ActivityActivity.this);
        }
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
