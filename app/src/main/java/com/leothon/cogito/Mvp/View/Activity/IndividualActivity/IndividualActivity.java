package com.leothon.cogito.Mvp.View.Activity.IndividualActivity;
import	java.util.function.Predicate;

import android.content.Intent;


import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Constants;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.ContractActivity;
import com.leothon.cogito.Mvp.View.Activity.EditClassActivity.EditClassActivity;
import com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity.EditIndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.FollowAFansActivity.FollowAFansActivity;
import com.leothon.cogito.Mvp.View.Activity.IMActivity.IMActivity;
import com.leothon.cogito.Mvp.View.Activity.QAHisActivity.QAHisActivity;
import com.leothon.cogito.Mvp.View.Activity.UploadClassActivity.UploadClassActivity;
import com.leothon.cogito.Mvp.View.Activity.VSureActivity.VSureActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharePreferenceManager;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;
import com.luck.picture.lib.tools.Constant;
import com.makeramen.roundedimageview.RoundedImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.text.ParseException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.android.api.options.RegisterOptionalUserInfo;
import cn.jpush.im.api.BasicCallback;
import io.reactivex.disposables.Disposable;

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

    @BindView(R.id.individual_auth)
    TextView authInfo;
    @BindView(R.id.v_sure)
    TextView vSure;
    @BindView(R.id.individual_content)
    TextView individualContent;

    @BindView(R.id.visit_other_user_qa)
    RelativeLayout visitOtherQa;

    @BindView(R.id.message_to)
    RelativeLayout messageTo;

    private Bundle bundle;
    private Intent intent;

    private boolean isfollowed = false;
    private UserEntity userEntity;
    private String uuid;

    private User otherUser;


    @Override
    public int initLayout() {
        return R.layout.activity_individual;
    }
    @Override
    public void initData() {
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        uuid = tokenValid.getUid();

        if ((boolean)activitysharedPreferencesUtils.getParams("login",false)){
            userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
        }
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
            //makeUploadClass.setVisibility(View.GONE);
            messageTo.setVisibility(View.VISIBLE);
            visitOtherQa.setVisibility(View.VISIBLE);
            showLoadingAnim();
            RetrofitServiceManager.getInstance().create(HttpService.class)
                    .getUserInfoById(bundle.getString("userId"))
                    .compose(ThreadTransformer.switchSchedulers())
                    .subscribe(new BaseObserver() {
                        @Override
                        public void doOnSubscribe(Disposable d) { }
                        @Override
                        public void doOnError(String errorMsg) {
                            MyToast.getInstance(IndividualActivity.this).show(errorMsg,Toast.LENGTH_SHORT);

                        }
                        @Override
                        public void doOnNext(BaseResponse baseResponse) {

                        }
                        @Override
                        public void doOnCompleted() {

                        }

                        @Override
                        public void onNext(BaseResponse baseResponse) {
                            hideLoadingAnim();
                            otherUser = (User)baseResponse.getData();
                            individualName.setText(otherUser.getUser_name());
                            ImageLoader.loadImageViewThumbnailwitherror(IndividualActivity.this, otherUser.getUser_icon(),individualIcon,R.drawable.defaulticon);
                            try{
                                int age = CommonUtils.getAge(otherUser.getUser_birth());
                                individualAge.setText(age + "岁");
                            }catch (ParseException e){
                                e.printStackTrace();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            if (otherUser.getUser_sex() == 1){
                                individualSex.setImageResource(R.drawable.male);
                                individualContent.setText("他制作的课程");
                            }else if (otherUser.getUser_sex() == 2){
                                individualSex.setImageResource(R.drawable.female);
                                individualContent.setText("她制作的课程");
                            }else {
                                individualSex.setImageResource(R.drawable.defaultsex);
                                individualContent.setText("该用户制作的课程");
                            }

                            int role = CommonUtils.isVIP(otherUser.getUser_role());
                            if (role != 2){
                                authInfo.setVisibility(View.VISIBLE);
                                authInfo.setText("认证：" + otherUser.getUser_role().substring(1));
                                individualSignal.setText(otherUser.getUser_signal());

                            }else {
                                authInfo.setVisibility(View.GONE);
                                individualSignal.setText(otherUser.getUser_signal());
                            }

                            individualLocation.setText(otherUser.getUser_address());

                        }
                    });

        }else {
            visitOtherQa.setVisibility(View.GONE);
            followBtn.setVisibility(View.GONE);
            vSure.setVisibility(View.VISIBLE);
            messageTo.setVisibility(View.GONE);
            //makeUploadClass.setVisibility(View.VISIBLE);
            setToolbarSubTitle("编辑个人资料");
            setToolbarTitle("");
            getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentUtils.getInstence().intent(IndividualActivity.this, EditIndividualActivity.class);

                }
            });
            individualContent.setText("我制作的课程(在此编辑、修改或者上传内容)");
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

            int role = CommonUtils.isVIP(userEntity.getUser_role());
            if (role != 2){
                authInfo.setVisibility(View.VISIBLE);
                authInfo.setText("认证：" + userEntity.getUser_role().substring(1));
                individualSignal.setText(userEntity.getUser_signal());

            }else {
                authInfo.setVisibility(View.GONE);
                individualSignal.setText(userEntity.getUser_signal());
            }

            individualLocation.setText(userEntity.getUser_address());


        }
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }


    }

//    @OnClick(R.id.make_upload_class)
//    public void makeUploadClass(View view){
//        if (userEntity.getUser_role().substring(0,1).equals("1")){
//
//
//            createDialog();
//
//        }else {
//            MyToast.getInstance(this).show("您尚未认证讲师，请先认证。\n本平台只有认证成为讲师方可制作上传课程",Toast.LENGTH_LONG);
//        }
//
//
//    }



    @OnClick(R.id.message_to)
    public void messageTo(View v) {


        messageToDialog();
    }



    @OnClick(R.id.follow_btn)
    public void followBtn(View view){
        if (isfollowed){
            followBtn.setBackgroundResource(R.drawable.textviewbackground);
            followBtn.setText("关注");
            followBtn.setTextColor(getResources().getColor(R.color.fontColor));
            isfollowed = false;
        }else {
            followBtn.setBackgroundResource(R.drawable.v_ensure);
            followBtn.setText("已关注");
            followBtn.setTextColor(getResources().getColor(R.color.white));
            isfollowed = true;
        }


    }


    @OnClick(R.id.visit_other_user_qa)
    public void toVisitQA(View view){
        Bundle bundleto = new Bundle();
        bundleto.putString("userId",bundle.getString("userId"));
        IntentUtils.getInstence().intent(this, QAHisActivity.class,bundleto);
    }

//    @OnClick(R.id.individual_icon)
//    public void individualIcon(View view){
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View imgEntryView = inflater.inflate(R.layout.image, null); // 加载自定义的布局文件
//        final AlertDialog dialog = new AlertDialog.Builder(this).create();
//        ImageView img = (ImageView)imgEntryView.findViewById(R.id.image_big);
//
//
//        //img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        final WeakReference<ImageView> imageViewWeakReference = new WeakReference<>(img);
//        ImageView target = imageViewWeakReference.get();
//        if (target != null) {
//            if (!bundle.getString("type").equals("other")){
//                ImageLoader.loadImageViewThumbnailwitherror(this,userEntity.getUser_icon(),img,R.drawable.defaulticon);
//            }else {
//                ImageLoader.loadImageViewThumbnailwitherror(this,otherUser.getUser_icon(),img,R.drawable.defaulticon);
//            }
//        }
//
//
//
//        dialog.setView(imgEntryView); // 自定义dialog
//        dialog.show();
//        WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
//        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        dialog.getWindow().setAttributes(layoutParams);
//        // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
//        imgEntryView.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View paramView) {
//                dialog.cancel();
//            }
//        });
//    }

    @OnClick(R.id.individual_follow_count)
    public void followCount(View view){
        Bundle bundleto = new Bundle();
        bundleto.putString("type","follow");
        IntentUtils.getInstence().intent(IndividualActivity.this, FollowAFansActivity.class,bundleto);
    }
    @OnClick(R.id.individual_fans_count)
    public void fansCount(View view){
        Bundle bundleto = new Bundle();
        bundleto.putString("type","fans");
        IntentUtils.getInstence().intent(IndividualActivity.this, FollowAFansActivity.class,bundleto);
    }
    @OnClick(R.id.v_sure)
    public void vSure(View view){


        if (userEntity.getUser_phone() != null){
            if (!userEntity.getUser_phone().equals("") && CommonUtils.isPhoneNumber(userEntity.getUser_phone())){
                VsureDialog();
            }else {
                bindPhoneDialog();
            }
        }else {
            bindPhoneDialog();
        }


    }

    @OnClick(R.id.individual_content)
    public void individualContent(View view){
        Bundle bundleto = new Bundle();
        if (bundle.get("type").equals("other")){

            if (otherUser.getUser_role().substring(0,1).equals("1")){
                bundleto.putString("type","other");
                bundleto.putString("userId",otherUser.getUser_id());
                IntentUtils.getInstence().intent(IndividualActivity.this,EditClassActivity.class,bundleto);
            }else {
                MyToast.getInstance(IndividualActivity.this).show("该用户非认证的讲师",Toast.LENGTH_LONG);
            }

        }else {
            if (userEntity.getUser_role().substring(0,1).equals("1")){
                bundleto.putString("type","individual");
                bundleto.putString("userId",userEntity.getUser_id());
                IntentUtils.getInstence().intent(IndividualActivity.this,EditClassActivity.class,bundleto);
            }else {
                MyToast.getInstance(IndividualActivity.this).show("您尚未认证成为讲师",Toast.LENGTH_LONG);
            }
        }



    }



        private void messageToDialog(){
        final CommonDialog dialog = new CommonDialog(this);



        dialog.setMessage("私信目前为实验性功能,若出现问题,请重新登录或者清除APP所有数据并登录")
                .setTitle("提示")
                .setSingle(false)
                .setNegtive("取消")
                .setPositive("仍然进入")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        Intent intent = new Intent(IndividualActivity.this, IMActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("targetAppKey", JMessageClient.getMyInfo().getAppKey());
                        intent.putExtra("targetId", otherUser.getUser_id());
                        IndividualActivity.this.startActivity(intent);
                        dialog.dismiss();
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                })

                .show();
    }

//    private void createDialog(){
//        final CommonDialog dialog = new CommonDialog(this);
//
//
//
//        dialog.setMessage("该选择会创建新的课程\n若您已创建成功课程，请在我制作的课程页面上传内容或者编辑")
//                .setTitle("提示")
//                .setSingle(false)
//                .setNegtive("取消")
//                .setPositive("我知道，继续创建")
//                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
//                    @Override
//                    public void onPositiveClick() {
//                        dialog.dismiss();
//                        Bundle bundleto = new Bundle();
//                        bundleto.putString("type","create");
//                        IntentUtils.getInstence().intent(IndividualActivity.this, UploadClassActivity.class,bundleto);
//
//                    }
//
//                    @Override
//                    public void onNegativeClick() {
//                        dialog.dismiss();
//
//                    }
//
//                })
//
//                .show();
//    }


    private void VsureDialog(){
        final CommonDialog dialog = new CommonDialog(this);



        dialog.setMessage("您将认证个人身份\n请阅读相关用户协议")
                .setTitle("提示")
                .setSingle(false)
                .setNegtive("阅读用户协议")
                .setPositive("我已阅读，继续认证")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        IntentUtils.getInstence().intent(IndividualActivity.this, VSureActivity.class);

                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();
                        IntentUtils.getInstence().intent(IndividualActivity.this, ContractActivity.class);
                    }

                })

                .show();
    }
    private void bindPhoneDialog(){
        final CommonDialog dialog = new CommonDialog(this);



        dialog.setMessage("您尚未绑定手机号码或者绑定有误\n请重新绑定")
                .setTitle("提示")
                .setSingle(false)
                .setNegtive("取消")
                .setPositive("前往绑定")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        IntentUtils.getInstence().intent(IndividualActivity.this, EditIndividualActivity.class);

                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                })

                .show();
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

        authInfo.setText(userEntityRe.getUser_role().substring(1));
        individualSignal.setText(userEntityRe.getUser_signal());

        individualLocation.setText(userEntityRe.getUser_address());

    }





}
