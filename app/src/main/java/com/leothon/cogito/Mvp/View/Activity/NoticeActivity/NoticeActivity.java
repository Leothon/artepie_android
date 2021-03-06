package com.leothon.cogito.Mvp.View.Activity.NoticeActivity;

import android.graphics.Color;


import android.os.Bundle;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.NoticeAdapter;
import com.leothon.cogito.Bean.NoticeInfo;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Message.NoticeMessage;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.AskDetailActivity;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.CommentDetailActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.MyToast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * created by leothon on 2018.8.12
 */
public class NoticeActivity extends BaseActivity implements NoticeContract.INoticeView, SwipeRefreshLayout.OnRefreshListener{


    @BindView(R.id.rv_notice)
    RecyclerView rvNotice;
    @BindView(R.id.swp_notice)
    SwipeRefreshLayout swpNotice;


    private ArrayList<NoticeInfo> noticeInfos;
    private NoticeAdapter noticeAdapter;
    private NoticePresenter noticePresenter;

    @Override
    public int initLayout() {
        return R.layout.activity_notice;
    }

    @Override
    public void initData() {
        noticePresenter = new NoticePresenter(this);
        swpNotice.setProgressViewOffset (false,100,300);
        swpNotice.setColorSchemeResources(R.color.rainbow_orange,R.color.rainbow_green,R.color.rainbow_blue,R.color.rainbow_purple,R.color.rainbow_yellow,R.color.rainbow_cyanogen);

    }
    @Override
    public void initView() {
        setToolbarSubTitle("全部已阅");
        setToolbarTitle("消息通知");

        noticePresenter.getNoticeInfo(activitysharedPreferencesUtils.getParams("token","").toString());
        swpNotice.setRefreshing(true);

        getToolbarSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getToolbarSubTitle().setTextColor(Color.parseColor("#808080"));
                noticePresenter.setAllNoticeVisible(activitysharedPreferencesUtils.getParams("token","").toString());
            }
        });
    }


    @Override
    public void loadNoticeInfo(ArrayList<NoticeInfo> noticeInfos) {
        if (swpNotice.isRefreshing()){
            swpNotice.setRefreshing(false);
        }
        boolean isHasUnRead = false;
        for (int i = 0;i < noticeInfos.size();i ++){
            if (noticeInfos.get(i).getNoticeStatus() == 0){
                isHasUnRead = true;
                break;
            }
        }
        if (isHasUnRead){
            getToolbarSubTitle().setTextColor(Color.parseColor("#f26402"));
        }

        this.noticeInfos = noticeInfos;
        initAdapter();

    }

    @Override
    public void loadMoreNoticeInfo(ArrayList<NoticeInfo> noticeInfos) {

//        boolean isHasUnRead = false;
//        for (int i = 0;i < noticeInfos.size();i ++){
//            if (noticeInfos.get(i).getNoticeStatus() == 0){
//                isHasUnRead = true;
//                break;
//            }
//        }
//        if (isHasUnRead){
//            getToolbarSubTitle().setTextColor(Color.parseColor("#f26402"));
//        }
        if (swpNotice.isRefreshing()){
            swpNotice.setRefreshing(false);
        }
        for (int i = 0;i < noticeInfos.size(); i++){
            this.noticeInfos.add(noticeInfos.get(i));

        }
        noticeAdapter.notifyDataSetChanged();
    }

    @Override
    public void setNoticeVisibleSuccess(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }

    @Override
    public void setNoticeAllVisibleSuccess(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
        for (int i = 0;i < noticeInfos.size();i ++){
            if (noticeInfos.get(i).getNoticeStatus() != 1){
                noticeInfos.get(i).setNoticeStatus(1);
            }
        }
        noticeAdapter.notifyDataSetChanged();
        NoticeMessage noticeMessage = new NoticeMessage();
        noticeMessage.setMessage("hide");
        EventBus.getDefault().post(noticeMessage);
    }

    @Override
    public void onRefresh() {
        noticePresenter.getNoticeInfo(activitysharedPreferencesUtils.getParams("token","").toString());
    }

    private void initAdapter(){
        swpNotice.setOnRefreshListener(this);
        noticeAdapter = new NoticeAdapter(this,noticeInfos);
        LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvNotice.setLayoutManager(mlinearLayoutManager);
        rvNotice.setAdapter(noticeAdapter);


        rvNotice.addOnScrollListener(new loadMoreDataListener(mlinearLayoutManager) {
            @Override
            public void onLoadMoreData(int currentPage) {
                swpNotice.setRefreshing(true);
                noticePresenter.getMoreNoticeInfo(currentPage * 15,activitysharedPreferencesUtils.getParams("token","").toString());
            }
        });
        noticeAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

            }
        });

        noticeAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });

        noticeAdapter.setNoticeOnClickListener(new NoticeAdapter.NoticeOnClickListener() {
            @Override
            public void noticeClickListener(String noticeId, int position,String targetId) {
                if (noticeInfos.get(position).getNoticeStatus() == 0){
                    noticePresenter.setNoticeVisible(activitysharedPreferencesUtils.getParams("token","").toString(),noticeId);
                }
                noticeInfos.get(position).setNoticeStatus(1);
                Bundle bundle = new Bundle();
                switch (noticeInfos.get(position).getNoticeType()){
                    case "qalike":
                        bundle.putString("qaId",targetId);
                        IntentUtils.getInstence().intent(NoticeActivity.this,AskDetailActivity.class,bundle);
                        bundle.clear();
                        break;
                    case "commentlike":
                        bundle.putString("commentId",targetId);
                        IntentUtils.getInstence().intent(NoticeActivity.this,CommentDetailActivity.class,bundle);
                        bundle.clear();
                        break;
                    case "replylike":
                        bundle.putString("commentId",targetId);
                        IntentUtils.getInstence().intent(NoticeActivity.this,CommentDetailActivity.class,bundle);
                        bundle.clear();
                        break;
                    case "qacomment":
                        bundle.putString("qaId",targetId);
                        IntentUtils.getInstence().intent(NoticeActivity.this,AskDetailActivity.class,bundle);
                        bundle.clear();
                        break;
                    case "replycomment":
                        bundle.putString("commentId",targetId);
                        IntentUtils.getInstence().intent(NoticeActivity.this,CommentDetailActivity.class,bundle);
                        bundle.clear();
                        break;
                    default:
                        break;
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        boolean isHasUnRead = false;
        for (int i = 0;i < noticeInfos.size();i ++){
            if (noticeInfos.get(i).getNoticeStatus() == 0){
                isHasUnRead = true;
                break;
            }
        }
        if (!isHasUnRead){
            NoticeMessage noticeMessage = new NoticeMessage();
            noticeMessage.setMessage("hide");
            EventBus.getDefault().post(noticeMessage);
        }
        super.onBackPressed();

    }





    @Override
    public void showMsg(String msg) {
        MyToast.getInstance(this).show(msg,Toast.LENGTH_SHORT);
    }




}
