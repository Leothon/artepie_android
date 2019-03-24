package com.leothon.cogito.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.UploadClassActivity.UploadClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Weight.CommonDialog;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYBaseVideoPlayer;
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

    private RelativeLayout noticeView;
    private TextView playContinue;

    @Override
    protected void init(Context context) {
        super.init(context);
        loadingVideo = (ZLoadingView)findViewById(R.id.loading_video);
        noticeView = (RelativeLayout)findViewById(R.id.notice_view_playing);
        playContinue = (TextView)findViewById(R.id.play_while_notice);
        noticeView.setVisibility(GONE);
        loadingVideo.setLoadingBuilder(Z_TYPE.DOUBLE_CIRCLE);//设置类型
        // zLoadingView.setLoadingBuilder(Z_TYPE.values()[type], 0.5); //设置类型 + 动画时间百分比 - 0.5倍
        loadingVideo.setColorFilter(Color.WHITE);//设置颜色
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mProgressBar.setThumb(context.getResources().getDrawable(R.drawable.video_thumb));
        }else {
            mProgressBar.setThumb(context.getResources().getDrawable(R.drawable.normal_thumb));
        }

    }

    @Override
    protected void showWifiDialog() {
        noticeView.setVisibility(VISIBLE);

        playContinue.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startPlayLogic();
                noticeView.setVisibility(GONE);
            }
        });
    }



    @Override
    public GSYBaseVideoPlayer startWindowFullscreen(Context context, boolean actionBar, boolean statusBar) {
        GSYBaseVideoPlayer gsyBaseVideoPlayer = super.startWindowFullscreen(context, actionBar, statusBar);
        if (gsyBaseVideoPlayer.getBackButton() != null) {
            gsyBaseVideoPlayer.getBackButton().setVisibility(VISIBLE);
            gsyBaseVideoPlayer.getBackButton().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mBackFromFullScreenListener == null) {
                        clearFullscreenLayout();
                        GSYVideoManager.instance().setNeedMute(true);
                    } else {
                        mBackFromFullScreenListener.onClick(v);
                    }
                }
            });
        }
        return gsyBaseVideoPlayer;
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
