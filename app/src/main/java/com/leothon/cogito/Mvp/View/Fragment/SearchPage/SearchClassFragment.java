package com.leothon.cogito.Mvp.View.Fragment.SearchPage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.SearchClassAdapter;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;

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








}
