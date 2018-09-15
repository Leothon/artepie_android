package com.leothon.cogito.Mvp.View.Activity.NoticeActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.NoticeAdapter;
import com.leothon.cogito.Bean.Notice;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * created by leothon on 2018.8.12
 */
public class NoticeActivity extends BaseActivity {


    @BindView(R.id.rv_notice)
    RecyclerView rvNotice;

    private ArrayList<Notice> notices;
    private NoticeAdapter noticeAdapter;

    @Override
    public int initLayout() {
        return R.layout.activity_notice;
    }

    @Override
    public void initView() {
        setToolbarSubTitle("");
        setToolbarTitle("消息通知");
        loadFalseData();
        initAdapter();
    }

    private void loadFalseData(){
        notices = new ArrayList<>();
        for (int i = 0;i < 20;i++){
            Notice notice = new Notice();
            notice.setUsericon("http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fa3.topitme.com%2F7%2F76%2Fcb%2F11181331220f6cb767o.jpg&thumburl=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D3194329332%2C1721487187%26fm%3D27%26gp%3D0.jpg");
            notice.setNoticeuser("刘三刀");
            notice.setNoticecontent("你的声音发音不标准");
            notices.add(notice);
        }
    }
    private void initAdapter(){
        noticeAdapter = new NoticeAdapter(this,notices);
        rvNotice.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvNotice.setAdapter(noticeAdapter);
        noticeAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                //TODO 点击进入评论详情
            }
        });

        noticeAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });
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
