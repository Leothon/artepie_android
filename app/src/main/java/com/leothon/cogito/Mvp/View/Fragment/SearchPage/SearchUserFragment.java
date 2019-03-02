package com.leothon.cogito.Mvp.View.Fragment.SearchPage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.SearchUserAdapter;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.Message.EventUser;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.IndividualActivity.IndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.SearchActivity.SearchActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void Event(EventUser users){
//        this.users = users.getUsers();
//
//        initAdapter();
//    }
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
