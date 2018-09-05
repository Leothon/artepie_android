package com.leothon.cogito.Mvp.View.Fragment.VoicePage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.VoiceAdapter;
import com.leothon.cogito.Bean.Voice;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.VoiceActivity.VoiceActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * created by leothon on 2018.7.29
 * 专栏页fragment
 */
public class VoiceFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{



    @BindView(R.id.voice_bar)
    CardView voiceBar;

    @BindView(R.id.toolbar_title)
    TextView title;
    @BindView(R.id.toolbar_subtitle)
    TextView subtitle;

    @BindView(R.id.rv_voice)
    RecyclerView rvVoice;

    @BindView(R.id.swp_voice)
    SwipeRefreshLayout swpVoice;
    private VoiceAdapter voiceAdapter;

    private ArrayList<Voice> voices;
    public VoiceFragment() { }

    /**
     * fragment的构造方法
     * @return
     */
    public static VoiceFragment newInstance() {
        VoiceFragment fragment = new VoiceFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_voice;
    }

    @Override
    protected void initView() {
        title.setText("艺条课堂");
        subtitle.setText("");
        loadFalseData();
        initAdapter();
    }

    public void loadFalseData(){
        voices = new ArrayList<>();
        for (int i = 0;i < 15;i++){
            Voice voice = new Voice();
            voice.setTitle("艺条资讯");
            voice.setDescription("艺条资讯显示相关资讯");
            voice.setImgurl("https://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=https%3A%2F%2Ftimgsa.baidu.com%2Ftimg%3Fimage%26quality%3D80%26size%3Db9999_10000%26sec%3D1533576327536%26di%3Def138349957d051b413c697c11bfdf1e%26imgtype%3D0%26src%3Dhttp%253A%252F%252Fpic.58pic.com%252F58pic%252F15%252F97%252F42%252F63c58PICY7B_1024.jpg&thumburl=https%3A%2F%2Fss0.bdstatic.com%2F70cFvHSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D2079313662%2C3627245998%26fm%3D27%26gp%3D0.jpg");
            voices.add(voice);
        }
    }
    public void initAdapter(){
        swpVoice.setOnRefreshListener(this);
        voiceAdapter = new VoiceAdapter(getMContext(),voices);
        rvVoice.setLayoutManager(new LinearLayoutManager(getMContext(), LinearLayoutManager.VERTICAL,false));
        rvVoice.setAdapter(voiceAdapter);
        voiceAdapter.setOnItemClickListner(new BaseAdapter.OnItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                //TODO 跳转到相应的
                Bundle bundle = new Bundle();
                bundle.putString("title",voices.get(position).getTitle());
                IntentUtils.getInstence().intent(getMContext(), VoiceActivity.class,bundle);
            }
        });

        voiceAdapter.setOnItemLongClickListner(new BaseAdapter.OnItemLongClickListner() {
            @Override
            public void onItemLongClickListner(View v, int position) {

            }
        });
    }

    @Override
    public void onRefresh() {
        swpVoice.setRefreshing(false);
    }
    @Override
    protected void initData() {

    }

    @Override
    public void hideLoading() {}

    @Override
    public void showMessage(@NonNull String message) {}

    @Override
    public void showLoading() {}


}
