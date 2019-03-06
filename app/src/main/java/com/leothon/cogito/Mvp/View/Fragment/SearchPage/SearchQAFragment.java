package com.leothon.cogito.Mvp.View.Fragment.SearchPage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.SearchQAAdapter;
import com.leothon.cogito.DTO.QAData;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.AskDetailActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.MyToast;

import java.util.ArrayList;

import butterknife.BindView;


public class SearchQAFragment extends BaseFragment {


    @BindView(R.id.rv_search_qa)
    RecyclerView rvSearchQA;
    private ArrayList<QAData> qaData;

    private SearchQAAdapter searchQAAdapter;
    public SearchQAFragment() {
    }

    public static SearchQAFragment newInstance(ArrayList<QAData> qaData) {
        SearchQAFragment fragment = new SearchQAFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("qadata", qaData);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_search_qa;
    }

    @Override
    protected void initView() {
        initAdapter();
    }





    private void initAdapter(){
        searchQAAdapter = new SearchQAAdapter(getMContext(),qaData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getMContext(),LinearLayoutManager.VERTICAL,false);
        rvSearchQA.setLayoutManager(linearLayoutManager);
        rvSearchQA.setAdapter(searchQAAdapter);
        searchQAAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });

        searchQAAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                if (qaData.get(position).getQaData() == null){
                    Bundle bundle = new Bundle();
                    bundle.putString("qaId",qaData.get(position).getQa_id());
                    IntentUtils.getInstence().intent(getMContext(),AskDetailActivity.class,bundle);
                }else {
                    if (qaData.get(position).getQaData().getQa_user_id() == null){
                        MyToast.getInstance(getMContext()).show("该内容已被删除",Toast.LENGTH_SHORT);
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString("qaId",qaData.get(position).getQa_id());
                        IntentUtils.getInstence().intent(getMContext(),AskDetailActivity.class,bundle);
                    }
                }


            }
        });
    }

    @Override
    protected void initData() {
        if (getArguments() != null){
            qaData = (ArrayList<QAData>)getArguments().getSerializable("qadata");
        }
    }




}
