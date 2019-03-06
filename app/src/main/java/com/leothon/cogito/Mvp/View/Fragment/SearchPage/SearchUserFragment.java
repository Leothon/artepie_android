package com.leothon.cogito.Mvp.View.Fragment.SearchPage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.SearchUserAdapter;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;

import java.util.ArrayList;

import butterknife.BindView;


public class SearchUserFragment extends BaseFragment {


    private ArrayList<User> users;
    private SearchUserAdapter searchUserAdapter;
    @BindView(R.id.rv_search_user)
    RecyclerView rvSearchUser;
    public SearchUserFragment() {

    }


    public static SearchUserFragment newInstance(ArrayList<User> users) {
        SearchUserFragment fragment = new SearchUserFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", users);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_search_user;
    }

    @Override
    protected void initView() {


        initAdapter();
    }

    private void initAdapter(){
        searchUserAdapter = new SearchUserAdapter(getMContext(),users);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getMContext(),LinearLayoutManager.VERTICAL,false);
        rvSearchUser.setLayoutManager(linearLayoutManager);
        rvSearchUser.setAdapter(searchUserAdapter);
        searchUserAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClickListener(View v, int position) {

            }
        });

        searchUserAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View v, int position) {
                Bundle bundle = new Bundle();
                if (users.get(position).getUser_id().equals(tokenUtils.ValidToken(fragmentsharedPreferencesUtils.getParams("token","").toString()).getUid())){
                    bundle.putString("type","individual");
                    IntentUtils.getInstence().intent(getMContext(), IndividualActivity.class,bundle);
                }else {
                    bundle.putString("type","other");
                    bundle.putString("userId",users.get(position).getUser_id());
                    IntentUtils.getInstence().intent(getMContext(), IndividualActivity.class,bundle);
                }


            }
        });
    }




    @Override
    protected void initData() {
        if (getArguments() != null){
            users = (ArrayList<User>)getArguments().getSerializable("user");
        }
    }




}
