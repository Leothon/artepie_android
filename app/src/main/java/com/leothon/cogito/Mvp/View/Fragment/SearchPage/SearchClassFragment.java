package com.leothon.cogito.Mvp.View.Fragment.SearchPage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.SearchClassAdapter;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Message.EventClass;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.SearchActivity.SearchActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.MyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;


public class SearchClassFragment extends BaseFragment {


    @BindView(R.id.rv_search_class)
    RecyclerView rvSearchClass;
    private ArrayList<SelectClass> selectClasses;

    private SearchClassAdapter searchClassAdapter;

    public SearchClassFragment() {

    }


    public static SearchClassFragment newInstance(ArrayList<SelectClass> selectClasses) {
        SearchClassFragment fragment = new SearchClassFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("class", selectClasses);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected void initData() {

        if (getArguments() != null){
            selectClasses = (ArrayList<SelectClass>)getArguments().getSerializable("class");
        }
    }
    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_search_class;
    }


    private void initAdapter(){
        searchClassAdapter = new SearchClassAdapter(getMContext(),selectClasses);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getMContext(),LinearLayoutManager.VERTICAL,false);
        rvSearchClass.setLayoutManager(linearLayoutManager);
        rvSearchClass.setAdapter(searchClassAdapter);
        searchClassAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });

        searchClassAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("classId",selectClasses.get(position).getSelectId());
                IntentUtils.getInstence().intent(getMContext(),SelectClassActivity.class,bundle);
            }
        });
    }
    @Override
    protected void initView() {


        initAdapter();
    }
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void Event(EventClass selectClasses){
//        this.selectClasses = selectClasses.getSelectClasses();
//        initAdapter();
//
//    }




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
