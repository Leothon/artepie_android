package com.leothon.cogito.Mvp.View.Activity.IndividualActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Adapter.FollowAFansAdapter;
import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Constants;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity.EditIndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.FollowAFansActivity.FollowAFansActivity;
import com.leothon.cogito.Mvp.View.Activity.UploadActivity.UploadActivity;
import com.leothon.cogito.Mvp.View.Activity.UploadClassActivity.UploadClassActivity;
import com.leothon.cogito.Mvp.View.Activity.VSureActivity.VSureActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;

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
    private UserEntity userEntity;
    private String uuid;

    @Override
    public int initLayout() {
        return R.layout.activity_individual;
    }
    @Override
    public void initData() {
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        uuid = tokenValid.getUid();

        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
    }
    @Override
    public void initView() {
        intent = getIntent();
        bundle = intent.getExtras();



        if (bundle.getString("type").equals("other")){
            individualFollow.setVisibility(View.VISIBLE);
            setToolbarSubTitle("");
            setToolbarTitle("");
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

                }
            });
            individualContent.setText("我发布的内容");
            individualName.setText(userEntity.getUser_name());
            ImageLoader.loadImageViewThumbnailwitherror(this, userEntity.getUser_icon(),individualIcon,R.drawable.defaulticon);
            try{
                int age = CommonUtils.getAge(userEntity.getUser_birth());
                individualAge.setText(age + "岁");
            }catch (ParseException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
            if (userEntity.getUser_sex() == 1){
                individualSex.setImageResource(R.drawable.male);
            }else if (userEntity.getUser_sex() == 2){
                individualSex.setImageResource(R.drawable.female);
            }else {
                individualSex.setImageResource(R.drawable.defaultsex);
            }

            individualSignal.setText(userEntity.getUser_signal());

            individualLocation.setText(userEntity.getUser_address());


        }
        EventBus.getDefault().register(this);

    }

    @OnClick(R.id.make_upload_class)
    public void makeUploadClass(View view){
        //TODO 制作上传视频
        IntentUtils.getInstence().intent(IndividualActivity.this, UploadClassActivity.class);
        //CommonUtils.makeText(this,"您不是认证用户，请先认证。\n本平台只有认证用户方可制作上传课程");
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
        LayoutInflater inflater = LayoutInflater.from(this);
        View imgEntryView = inflater.inflate(R.layout.image, null); // 加载自定义的布局文件
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        ImageView img = (ImageView)imgEntryView.findViewById(R.id.image_big);
        ImageLoader.loadImageViewThumbnailwitherror(this,userEntity.getUser_icon(),img,R.drawable.defaulticon);
        dialog.setView(imgEntryView); // 自定义dialog
        dialog.show();
        // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
        imgEntryView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                dialog.cancel();
            }
        });
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
    }

    @OnClick(R.id.individual_content)
    public void individualContent(View view){
        IntentUtils.getInstence().intent(IndividualActivity.this, UploadActivity.class);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(User user){
        UserEntity userEntityRe = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
        individualName.setText(userEntityRe.getUser_name());

        try{
            int age = CommonUtils.getAge(userEntityRe.getUser_birth());
            individualAge.setText(age + "岁");
        }catch (ParseException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        if (userEntityRe.getUser_sex() == 1){
            individualSex.setImageResource(R.drawable.male);
        }else if (userEntityRe.getUser_sex() == 2){
            individualSex.setImageResource(R.drawable.female);
        }else {
            individualSex.setImageResource(R.drawable.defaultsex);
        }


        ImageLoader.loadImageViewThumbnailwitherror(this,userEntityRe.getUser_icon(),individualIcon,R.drawable.defaulticon);

        individualSignal.setText(userEntityRe.getUser_signal());

        individualLocation.setText(userEntityRe.getUser_address());

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
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
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
