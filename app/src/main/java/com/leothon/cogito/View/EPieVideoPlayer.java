package com.leothon.cogito.View;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;

import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.UploadClassActivity.UploadClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Weight.CommonDialog;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zyao89.view.zloading.ZLoadingView;
import com.zyao89.view.zloading.Z_TYPE;

public class EPieVideoPlayer extends StandardGSYVideoPlayer {

    private ZLoadingView loadingVideo;
    public EPieVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public EPieVideoPlayer(Context context) {
        super(context);
    }

    public EPieVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    protected void init(Context context) {
        super.init(context);
        loadingVideo = (ZLoadingView)findViewById(R.id.loading_video);
        loadingVideo.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);//设置类型
        // zLoadingView.setLoadingBuilder(Z_TYPE.values()[type], 0.5); //设置类型 + 动画时间百分比 - 0.5倍
        loadingVideo.setColorFilter(Color.WHITE);//设置颜色
    }

    @Override
    protected void showWifiDialog() {
        isWifiDialog();
    }

    private void isWifiDialog(){
        final CommonDialog dialog = new CommonDialog(getActivityContext());

        dialog.setMessage("您现在没有连接WIFI，播放视频会产生流量费用")
                .setTitle("提示")
                .setSingle(false)
                .setNegtive("取消")
                .setPositive("播放")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        startPlayLogic();
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                }).show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.video_layout_epie;
    }



    @Override
    protected void changeUiToNormal() {
        super.changeUiToNormal();
        mLoadingProgressBar.setVisibility(GONE);
        loadingVideo.setVisibility(GONE);
    }

    @Override
    protected void changeUiToPreparingShow() {
        super.changeUiToPreparingShow();
        mLoadingProgressBar.setVisibility(GONE);
        loadingVideo.setVisibility(VISIBLE);
    }

    @Override
    protected void changeUiToPlayingShow() {
        super.changeUiToPlayingShow();
        mLoadingProgressBar.setVisibility(GONE);
        loadingVideo.setVisibility(GONE);
    }

    @Override
    protected void changeUiToPauseShow() {
        super.changeUiToPauseShow();
        mLoadingProgressBar.setVisibility(GONE);
        loadingVideo.setVisibility(GONE);
    }

    @Override
    protected void changeUiToPlayingBufferingShow() {
        super.changeUiToPlayingBufferingShow();
        mLoadingProgressBar.setVisibility(GONE);
        loadingVideo.setVisibility(VISIBLE);
    }

    @Override
    protected void changeUiToCompleteShow() {
        super.changeUiToCompleteShow();
        mLoadingProgressBar.setVisibility(GONE);
        loadingVideo.setVisibility(GONE);
    }

    @Override
    protected void changeUiToError() {
        super.changeUiToError();
        mLoadingProgressBar.setVisibility(GONE);
        loadingVideo.setVisibility(GONE);
    }

    @Override
    protected void changeUiToPrepareingClear() {
        super.changeUiToPrepareingClear();
        mLoadingProgressBar.setVisibility(GONE);
        loadingVideo.setVisibility(GONE);
    }

    @Override
    protected void changeUiToClear() {
        super.changeUiToNormal();
        mLoadingProgressBar.setVisibility(GONE);
        loadingVideo.setVisibility(GONE);
    }



    @Override
    protected void changeUiToPlayingClear() {
        super.changeUiToPlayingShow();
        mLoadingProgressBar.setVisibility(GONE);
        loadingVideo.setVisibility(GONE);
    }

    @Override
    protected void changeUiToPauseClear() {
        super.changeUiToPauseShow();
        mLoadingProgressBar.setVisibility(GONE);
        loadingVideo.setVisibility(GONE);
    }

    @Override
    protected void changeUiToPlayingBufferingClear() {
        super.changeUiToPlayingBufferingShow();
        mLoadingProgressBar.setVisibility(GONE);
        loadingVideo.setVisibility(VISIBLE);
    }

    @Override
    protected void changeUiToCompleteClear() {
        super.changeUiToCompleteShow();
        mLoadingProgressBar.setVisibility(GONE);
        loadingVideo.setVisibility(GONE);
    }


}
