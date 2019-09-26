package com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Bean.CustomMood;
import com.leothon.cogito.Bean.Tag;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.View.MyToast;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.BindView;

public class Custom3Fragment extends BaseFragment {
    @BindView(R.id.mood_choose)
    TagFlowLayout idFlowlayout;
    TagAdapter mAdapter;

    private ArrayList<Tag> lists;
    private CustomMood customMood;
    private String result = "";
    public static Custom3Fragment newInstance() {
        Custom3Fragment fragment = new Custom3Fragment();
        return fragment;
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_custom3;
    }

    @Override
    protected void initView() {

        customMood = new CustomMood();
        initAdapter();
    }

    @Override
    protected void initData() {

    }


    private void initAdapter() {
        final LayoutInflater mInflater = LayoutInflater.from(getMContext());

        lists = new ArrayList<>();
        lists.add(new Tag("慷慨激昂"));
        lists.add(new Tag("热血"));
        lists.add(new Tag("幽默"));
        lists.add(new Tag("小清新"));
        lists.add(new Tag("可爱"));
        lists.add(new Tag("喜庆"));
        lists.add(new Tag("温馨"));
        lists.add(new Tag("欢乐"));
        lists.add(new Tag("希望"));
        lists.add(new Tag("轻快"));
        lists.add(new Tag("舒缓"));
        lists.add(new Tag("轻松"));
        lists.add(new Tag("萌"));
        lists.add(new Tag("浪漫"));
        lists.add(new Tag("感动"));
        lists.add(new Tag("励志"));
        lists.add(new Tag("梦幻"));
        lists.add(new Tag("治愈"));
        lists.add(new Tag("自信"));
        lists.add(new Tag("活力"));
        lists.add(new Tag("青春"));
        lists.add(new Tag("激励"));


        idFlowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                customMood.setInfo(String.valueOf(selectPosSet));
                ((CustomActivity)getActivity()).setCustomMood(customMood);
            }
        });


        idFlowlayout.setAdapter(mAdapter = new TagAdapter<Tag>(lists) {
            @Override
            public View getView(FlowLayout parent, int position, Tag tag) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv_choose, idFlowlayout, false);
                tv.setText(tag.getTagInfo());
                return tv;
            }

        });
        //设置选中的按钮
        mAdapter.setSelectedList(-1);

    }

}
