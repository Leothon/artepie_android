package com.leothon.cogito.Mvp.View.Fragment.ViewPagerRightFragment;

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
import com.leothon.cogito.Mvp.View.Activity.ActivityActivity.ActivityActivity;
import com.leothon.cogito.Mvp.View.Activity.PlayerActivity.PlayerActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * created by leothon on 2018.8.6
 */
public class ViewPagerRightFragment extends BaseFragment {

    private ViewPagerContentAdapter viewPagerContentAdapter;
    private ArrayList<ActivityAndVideo> activityAndVideos;

    @BindView(R.id.rv_fragment)
    RecyclerView rvRight;
    public ViewPagerRightFragment() { }


    public static ViewPagerRightFragment newInstance() {
        ViewPagerRightFragment fragment = new ViewPagerRightFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }



    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_view_pager;
    }

    @Override
    protected void init() {
        activityAndVideos = new ArrayList<>();
        for (int i = 0;i<25;i ++){
            ActivityAndVideo activityAndVideo = new ActivityAndVideo();
            activityAndVideo.setAvtitle("这里是免费讲座");
            activityAndVideo.setAvmark(1);
            activityAndVideo.setAvurl("http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fphotocdn.sohu.com%2F20130502%2FImg374568060.jpg&thumburl=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D25528335%2C2202724169%26fm%3D27%26gp%3D0.jpg");
            activityAndVideos.add(activityAndVideo);
        }
    }

    @Override
    protected void initView() {
        initAdapter();
    }

    public void initAdapter(){
        viewPagerContentAdapter = new ViewPagerContentAdapter(getMContext(),activityAndVideos);
        rvRight.setLayoutManager(new GridLayoutManager(getMContext(),2,GridLayoutManager.VERTICAL,false));
        rvRight.setAdapter(viewPagerContentAdapter);
        viewPagerContentAdapter.setOnItemClickListner(new BaseAdapter.OnItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("title",activityAndVideos.get(position).getAvtitle());
                bundle.putString("url",activityAndVideos.get(position).getAvurl());
                IntentUtils.getInstence().intent(getMContext(), ActivityActivity.class,bundle);
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
