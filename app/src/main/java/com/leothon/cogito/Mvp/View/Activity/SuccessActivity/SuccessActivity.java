package com.leothon.cogito.Mvp.View.Activity.SuccessActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.TokenValid;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.DTO.ClassDetail;
import com.leothon.cogito.GreenDao.UserEntity;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.HostActivity.HostActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;


public class SuccessActivity extends BaseActivity {



    @BindView(R.id.success_img)
    ImageView successImg;


    @BindView(R.id.success_to_class)
    TextView successToAction;

    private UserEntity userEntity;

    private String uuid;
    @Override
    public int initLayout() {
        return R.layout.activity_success;
    }

    @Override
    public void initView() {


        setToolbarSubTitle("");
        setToolbarTitle("支付成功");



        successToAction.setText("立即学习");
        successToAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 前往我的订阅
                updateUserEntity(uuid);
                //IntentUtils.getInstence().intent(SuccessActivity.this, SelectClassActivity.class);
                //finish();
            }
        });


    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }



    @Override
    public void onBackPressed() {
        //TODO eventbus实现
        updateUserEntity(uuid);
        Bundle bundleto = new Bundle();
        bundleto.putString("type","about");
        IntentUtils.getInstence().intent(SuccessActivity.this, HostActivity.class,bundleto);
        finish();
    }

    @Override
    public void initData() {
        TokenValid tokenValid = tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString());
        uuid = tokenValid.getUid();

        userEntity = getDAOSession().queryRaw(UserEntity.class,"where user_id = ?",uuid).get(0);
    }


    private  void updateUserEntity(String uuid){


        RetrofitServiceManager.getInstance().create(HttpService.class)
                .getUserInfo(activitysharedPreferencesUtils.getParams("token","").toString())
                .compose(ThreadTransformer.switchSchedulers())
                .subscribe(new BaseObserver() {
                    @Override
                    public void doOnSubscribe(Disposable d) { }
                    @Override
                    public void doOnError(String errorMsg) {
                        MyToast.getInstance(SuccessActivity.this).show("出错",Toast.LENGTH_SHORT);
                    }
                    @Override
                    public void doOnNext(BaseResponse baseResponse) {

                    }
                    @Override
                    public void doOnCompleted() {

                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                       User user = (User)baseResponse.getData();
                       userEntity.setUser_art_coin(user.getUser_art_coin());
                       getDAOSession().update(userEntity);
                    }
                });


    }


}
