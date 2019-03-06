package com.leothon.cogito.Mvp.View.Activity.SettingsActivity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.leothon.cogito.Bean.FeedbackInfo;
import com.leothon.cogito.Http.BaseObserver;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Http.HttpService;
import com.leothon.cogito.Http.RetrofitServiceManager;
import com.leothon.cogito.Http.ThreadTransformer;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

/**
 * created by leothon on  2018.8.9
 */
public class AdviceActivity extends BaseActivity {

    @BindView(R.id.edit_advice)
    MaterialEditText editAdvice;

    private FeedbackInfo feedbackInfo;
    @Override
    public int initLayout() {
        return R.layout.activity_advice;
    }

    @Override
    public void initView() {
        setToolbarSubTitle("");
        setToolbarTitle("反馈意见");
    }

    @OnClick(R.id.submit_advice)
    public void submitAdvice(View view){
        String advice = editAdvice.getText().toString();


        if (!advice.equals("")){
            showLoadingAnim();
            feedbackInfo.setFeedbackContent(advice);
            RetrofitServiceManager.getInstance().create(HttpService.class)
                    .sendFeedback(feedbackInfo)
                    .compose(ThreadTransformer.switchSchedulers())
                    .subscribe(new BaseObserver() {
                        @Override
                        public void doOnSubscribe(Disposable d) { }
                        @Override
                        public void doOnError(String errorMsg) {
                            Log.e(TAG, errorMsg);
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
                            MyToast.getInstance(AdviceActivity.this).show(baseResponse.getError(),Toast.LENGTH_SHORT);
                        }
                    });
        }else {
            MyToast.getInstance(this).show("请输入反馈的问题",Toast.LENGTH_SHORT);
        }
        editAdvice.setText("");
    }

    @Override
    public void initData() {

        feedbackInfo = new FeedbackInfo();
        feedbackInfo.setUserId(tokenUtils.ValidToken(activitysharedPreferencesUtils.getParams("token","").toString()).getUid());
        feedbackInfo.setAndroidVersion(CommonUtils.getBuildVersion());
        feedbackInfo.setBuildApi(String.valueOf(CommonUtils.getBuildLevel()));
        feedbackInfo.setDeviceBrand(CommonUtils.getPhoneBrand());
        feedbackInfo.setDeviceModel(CommonUtils.getPhoneModel());
        feedbackInfo.setVersionCode(CommonUtils.getVersionCode(this));

    }



}
