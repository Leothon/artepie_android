package com.leothon.cogito.Mvp.View.Activity.IndividualActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Adapter.FollowAFansAdapter;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity.EditIndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.FollowAFansActivity.FollowAFansActivity;
import com.leothon.cogito.Mvp.View.Activity.UploadActivity.UploadActivity;
import com.leothon.cogito.Mvp.View.Activity.VSureActivity.VSureActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

public class IndividualActivity extends BaseActivity {



    @BindView(R.id.individual_name)
    TextView individualName;
    @BindView(R.id.age_individual)
    TextView individualAge;
    @BindView(R.id.sex_individual)
    ImageView individualSex;
    @BindView(R.id.individual_signal)
    TextView individualSignal;
    @BindView(R.id.individual_location)
    TextView individualLocation;
    @BindView(R.id.individual_icon)
    RoundedImageView individualIcon;
    @BindView(R.id.individual_follow_count)
    TextView individualFollow;
    @BindView(R.id.individual_fans_count)
    TextView individualFans;
    @BindView(R.id.follow_btn)
    TextView followBtn;

    @BindView(R.id.v_sure)
    TextView vSure;
    @BindView(R.id.individual_content)
    TextView individualContent;

    @BindView(R.id.make_upload_class)
    RelativeLayout makeUploadClass;
    private Bundle bundle;
    private Intent intent;

    private boolean isfollowed = false;

    @Override
    public int initLayout() {
        return R.layout.activity_individual;
    }

    @Override
    public void initview() {
        intent = getIntent();
        bundle = intent.getExtras();
        ImageLoader.loadImageViewThumbnailwitherror(this,bundle.getString("icon"),individualIcon,R.drawable.defalutimg);
        individualName.setText(bundle.getString("name"));
        individualSignal.setText(bundle.getString("desc"));
        if (bundle.getString("type").equals("other")){
            individualFollow.setVisibility(View.VISIBLE);
            setToolbarSubTitle("");
            setToolbarTitle(bundle.getString("name"));
            followBtn.setVisibility(View.VISIBLE);
            vSure.setVisibility(View.GONE);
            makeUploadClass.setVisibility(View.GONE);
            individualContent.setText("他发布的内容");

        }else {
            followBtn.setVisibility(View.GONE);
            vSure.setVisibility(View.VISIBLE);
            makeUploadClass.setVisibility(View.VISIBLE);
            setToolbarSubTitle("编辑个人资料");
            setToolbarTitle("");
            getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentUtils.getInstence().intent(IndividualActivity.this, EditIndividualActivity.class);
                    finish();
                }
            });
            individualContent.setText("我发布的内容");


        }


    }

    @OnClick(R.id.make_upload_class)
    public void makeUploadClass(View view){
        //TODO 制作上传视频
        CommonUtils.makeText(this,"您不是认证用户，请先认证。\n本平台只有认证用户方可制作上传课程");
    }

    @OnClick(R.id.follow_btn)
    public void followBtn(View view){
        if (isfollowed){
            followBtn.setBackgroundResource(R.drawable.textviewbackground);
            followBtn.setText("关注");
            followBtn.setTextColor(getResources().getColor(R.color.fontColor));
            isfollowed = false;
            //TODO 取消关注操作
        }else {
            followBtn.setBackgroundResource(R.drawable.v_ensure);
            followBtn.setText("已关注");
            followBtn.setTextColor(getResources().getColor(R.color.white));
            isfollowed = true;
            //TODO 进行关注操作
        }


    }

    @OnClick(R.id.individual_icon)
    public void individualIcon(View view){
        //TODO 大图
        CommonUtils.makeText(this,"显示大图");
    }

    @OnClick(R.id.individual_follow_count)
    public void followCount(View view){
        //TODO 关注列表
        Bundle bundleto = new Bundle();
        bundleto.putString("type","follow");
        IntentUtils.getInstence().intent(IndividualActivity.this, FollowAFansActivity.class,bundleto);
    }
    @OnClick(R.id.individual_fans_count)
    public void fansCount(View view){
        //TODO 粉丝列表
        Bundle bundleto = new Bundle();
        bundleto.putString("type","fans");
        IntentUtils.getInstence().intent(IndividualActivity.this, FollowAFansActivity.class,bundleto);
    }
    @OnClick(R.id.v_sure)
    public void vSure(View view){
        //TODO 加V认证
        //CommonUtils.makeText(this,"认证");
        IntentUtils.getInstence().intent(IndividualActivity.this, VSureActivity.class);
        finish();
    }

    @OnClick(R.id.individual_content)
    public void individualContent(View view){
        IntentUtils.getInstence().intent(IndividualActivity.this, UploadActivity.class);
    }
    @Override
    public void initdata() {

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
