package com.leothon.cogito.Mvp.View.Fragment.ViewPagerLeftFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.ViewPagerContentAdapter;
import com.leothon.cogito.Bean.ActivityAndVideo;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.PlayerActivity.PlayerActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * created by leothon on 2018.8.6
 */
public class ViewPagerLeftFragment extends BaseFragment{


    private ViewPagerContentAdapter viewPagerContentAdapter;
    private ArrayList<ActivityAndVideo> activityAndVideos;

    @BindView(R.id.rv_leftfragment)
    RecyclerView rvLeft;
    public ViewPagerLeftFragment() { }


    public static ViewPagerLeftFragment newInstance() {
        ViewPagerLeftFragment fragment = new ViewPagerLeftFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_view_pager_left;
    }

    @Override
    protected void init() {
        activityAndVideos = new ArrayList<>();
        for (int i = 0;i<25;i ++){
            ActivityAndVideo activityAndVideo = new ActivityAndVideo();
            activityAndVideo.setAvtitle("这里是免费视频");
            activityAndVideo.setAvmark(0);
            activityAndVideo.setAvurl("http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Ff.01ny.cn%2Fforum%2F201112%2F04%2F190722f3vkvdfv3khz30mt.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D524358622%2C2173211898%26fm%3D27%26gp%3D0.jpg");
            activityAndVideos.add(activityAndVideo);
        }
    }

    @Override
    protected void initView() {
        initAdapter();
    }

    public void initAdapter(){
        viewPagerContentAdapter = new ViewPagerContentAdapter(getMContext(),activityAndVideos);
        rvLeft.setLayoutManager(new GridLayoutManager(getMContext(),2,GridLayoutManager.VERTICAL,false));
        rvLeft.setAdapter(viewPagerContentAdapter);
        viewPagerContentAdapter.setOnItemClickListner(new BaseAdapter.OnItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("imgTitle",activityAndVideos.get(position).getAvtitle());
                bundle.putString("imgUrls",activityAndVideos.get(position).getAvurl());
                IntentUtils.getInstence().intent(getMContext(), PlayerActivity.class,bundle);
            }
        });

        viewPagerContentAdapter.setOnItemLongClickListner(new BaseAdapter.OnItemLongClickListner() {
            @Override
            public void onItemLongClickListner(View v, int position) {

            }
        });
    }
    @Override
    protected void initData() {

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
