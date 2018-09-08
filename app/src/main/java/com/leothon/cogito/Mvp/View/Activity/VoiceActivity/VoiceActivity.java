package com.leothon.cogito.Mvp.View.Activity.VoiceActivity;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.SeekBar;
import android.widget.TextView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;

import com.leothon.cogito.Weight.PlayPauseView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
/**
 * created by leothon on 2018.8.11
 */
public class VoiceActivity extends BaseActivity {


    @BindView(R.id.play_pause)
    PlayPauseView playPause;
    @BindView(R.id.seek_bar)
    SeekBar seekBar;
    @BindView(R.id.current_time)
    TextView currentTime;
    @BindView(R.id.time_sum)
    TextView timeSum;


    private MediaPlayer mediaPlayer ;
    private AudioManager audioManager;
    private Timer timer;
    private boolean isPause = false;

    int maxVolume,CurrentVolume;
    private boolean isSeekBarChanging;
    private int currentPosition;
    SimpleDateFormat format;

    private Animation operatingAnim;
    private LinearInterpolator linearInterpolator;
    private Intent intent;
    private Bundle bundle;
    @Override
    public int initLayout() {
        return R.layout.activity_voice;
    }

    @Override
    public void initview() {
        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarTitle(bundle.getString("title"));
        setToolbarSubTitle("");
        loadAnim();
        
        mediaPlayer = new MediaPlayer();
        audioManager = (AudioManager)getSystemService(Service.AUDIO_SERVICE);
        format = new SimpleDateFormat("mm:ss");
        seekBar.setOnSeekBarChangeListener(new MyseekBar());
        initMediaPlayer();
        playPause.setPlayPauseListener(new PlayPauseView.PlayPauseListener() {
            @Override
            public void play() {
                //TODO 播放音乐

                if (!mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    mediaPlayer.seekTo(currentPosition);
                    timer = new Timer();
                    timer.schedule(new TimerTask() {

                        Runnable updateUI = new Runnable() {
                            @Override
                            public void run() {
                                if (mediaPlayer == null){
                                    currentTime.setText("");
                                }else {
                                    currentTime.setText(format.format(mediaPlayer.getCurrentPosition()) + "");
                                }

                            }
                        };
                        @Override
                        public void run() {
                            if (!isSeekBarChanging){
                                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                                runOnUiThread(updateUI);
                            }
                        }
                    },0,50);
                }


            }

            @Override
            public void pause() {
                //TODO 暂停音乐
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    isPause = true;
                    currentPosition = mediaPlayer.getCurrentPosition();

                }

            }
        });
    }

    public void loadAnim(){
        operatingAnim = AnimationUtils.loadAnimation(VoiceActivity.this,R.anim.remote_img);
        operatingAnim.setDuration(2000);
        linearInterpolator = new LinearInterpolator();
        operatingAnim.setInterpolator(linearInterpolator);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isSeekBarChanging = true;
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 获取资源注意
     */
    private void initMediaPlayer() {
        try {
            //mediaPlayer.setDataSource("/sdcard/netease/cloudmusic/Music/Ridan - Ulysse.mp3");//指定音频文件的路径
            //mediaPlayer.prepare();//让mediaplayer进入准备状态
            mediaPlayer.setDataSource("http://www.170mv.com/kw/other.web.rh01.sycdn.kuwo.cn/resource/n3/21/19/3413654131.mp3");
            mediaPlayer.prepareAsync();
            mediaPlayer.setLooping(true);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    seekBar.setMax(mediaPlayer.getDuration());
                    timeSum.setText(format.format(mediaPlayer.getDuration())+"");
                    currentTime.setText("00:00");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public class MyseekBar implements SeekBar.OnSeekBarChangeListener{

        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            isSeekBarChanging = false;
            mediaPlayer.seekTo(seekBar.getProgress());
            currentPosition = seekBar.getProgress();
        }
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
