package com.leothon.cogito.Mvp.View.Fragment.MicClassDetailFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.DetailClassAdapter;
import com.leothon.cogito.Bean.DetailClass;

import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.MicClassActivity.Mic2ClassActivity;
import com.leothon.cogito.Mvp.View.Fragment.MicClassFragment.Mic1Fragment;
import com.leothon.cogito.Mvp.View.Fragment.MicClassPage.MicClassFragment;
import com.leothon.cogito.R;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;


public class ClassListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.rv_detail_class)
    RecyclerView rvFragment;
    @BindView(R.id.swp_detail_class)
    SwipeRefreshLayout swpFragment;
    private ArrayList<DetailClass> detailClasses;
    private DetailClassAdapter detailClassAdapter;
    private static int THRESHOLD_OFFSET = 10;
    private Mic2ClassActivity mic2ClassActivity;
    public ClassListFragment() {

    }


    public static ClassListFragment newInstance(ArrayList<DetailClass> detailClasses) {
        ClassListFragment fragment = new ClassListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list",detailClasses);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_class_list;
    }

    @Override
    protected void initView() {
        detailClasses = new ArrayList<>();
        detailClasses = (ArrayList<DetailClass>)getArguments().getSerializable("list");
        mic2ClassActivity = (Mic2ClassActivity) getActivity();
        initAdapter();
    }

    private void initAdapter(){
        swpFragment.setOnRefreshListener(this);
        detailClassAdapter = new DetailClassAdapter(getMContext(),detailClasses);
        rvFragment.setLayoutManager(new LinearLayoutManager(getMContext(),LinearLayoutManager.VERTICAL,false));
        rvFragment.setAdapter(detailClassAdapter);
        detailClassAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {

            }
        });
        detailClassAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

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



    @Override
    public void onRefresh() {
        swpFragment.setRefreshing(false);
    }
}
