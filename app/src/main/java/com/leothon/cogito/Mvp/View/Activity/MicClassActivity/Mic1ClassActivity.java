package com.leothon.cogito.Mvp.View.Activity.MicClassActivity;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.ActivityActivity.ActivityActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.View.ObserveScrollView;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;

public class Mic1ClassActivity extends BaseActivity implements ObserveScrollView.ScrollListener{

    @BindView(R.id.class_detail_img_1)
    RoundedImageView classDetailImg;
    @BindView(R.id.class_detail_title_1)
    TextView classDetailTitle;
    @BindView(R.id.class_detail_level_1)
    TextView classDetailLevel;
    @BindView(R.id.class_detail_author_1)
    TextView classDetailAuthor;
    @BindView(R.id.class_detail_time_1)
    TextView classDetailTime;
    @BindView(R.id.class_detail_price_1)
    TextView classDetailPrice;
    @BindView(R.id.class_detail_desc_1)
    TextView classDetailDesc;
    @BindView(R.id.class_detail_author_desc_1)
    TextView classDetailAuthorDesc;
    @BindView(R.id.comment_first_img)
    RoundedImageView commentFistImg;
    @BindView(R.id.comment_first_name)
    TextView commentFirstName;
    @BindView(R.id.comment_first_content)
    TextView commentFirstContent;
    @BindView(R.id.comment_first_like)
    TextView commentFirstLike;
    @BindView(R.id.comment_second_img)
    RoundedImageView commentSecondImg;
    @BindView(R.id.comment_second_name)
    TextView commentSecondName;
    @BindView(R.id.comment_second_content)
    TextView commentSecondContent;
    @BindView(R.id.comment_second_like)
    TextView commentSecondLike;
    @BindView(R.id.comment_third_img)
    RoundedImageView commentThirdImg;
    @BindView(R.id.comment_third_name)
    TextView commentThirdName;
    @BindView(R.id.comment_third_content)
    TextView commentThirdContent;
    @BindView(R.id.comment_third_like)
    TextView commentThirdLike;
    @BindView(R.id.comment_more_class)
    TextView commentMore;
    @BindView(R.id.class_free_1)
    TextView classFree;
    @BindView(R.id.class_detail_buy_btn_1)
    TextView classDetailBuy;
    @BindView(R.id.scroll_mic_class)
    ObserveScrollView scrollMicClass;
    private Intent intent;
    private Bundle bundle;
    @Override
    public int initLayout() {
        return R.layout.activity_mic1_class;
    }

    @Override
    public void initView() {
        setToolbarSubTitle("");
        setToolbarTitle("");
        intent = getIntent();
        bundle = intent.getExtras();
        scrollMicClass.setScrollListener(this);
        ImageLoader.loadImageViewThumbnailwitherror(this,bundle.getString("img"),classDetailImg,R.drawable.defalutimg);
        classDetailAuthor.setText(bundle.getString("author"));
        classDetailTitle.setText(bundle.getString("title"));
        classDetailTime.setText(bundle.getString("time"));
        classDetailPrice.setText(bundle.getString("price"));
    }

    @Override
    public void scrollOrientation(int l, int t, int oldl, int oldt) {
        if (t > 400 && oldt < t){
            getToolbarTitle().setTextSize(20);
            setToolbarTitle(bundle.getString("title"));
            getToolbarSubTitle().setTextColor(getResources().getColor(R.color.orange));
            getToolbarSubTitle().setTextSize(15);
            setToolbarSubTitle(bundle.getString("price"));
        }else if (t <= 400 && oldt >= t){
            setToolbarTitle("");
            setToolbarSubTitle("");
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
