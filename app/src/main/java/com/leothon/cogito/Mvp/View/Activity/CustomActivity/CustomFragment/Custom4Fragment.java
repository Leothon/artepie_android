package com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer.chunk.BaseChunkSampleSourceEventListener;
import com.leothon.cogito.Bean.CustomHearing;
import com.leothon.cogito.Bean.Tag;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomActivity;
import com.leothon.cogito.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;


public class Custom4Fragment extends BaseFragment {

    @BindView(R.id.hearing_choose)
    TagFlowLayout idFlowlayout;
    TagAdapter mAdapter;
    private String result = "";
    private CustomHearing customHearing;
    private ArrayList<Tag> lists;
    public Custom4Fragment() {
    }


    public static Custom4Fragment newInstance() {
        Custom4Fragment fragment = new Custom4Fragment();
        return fragment;
    }



    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_custom4;
    }

    @Override
    protected void initView() {

        initAdapter();
    }

    @Override
    protected void initData() {

    }

    private void initAdapter() {
        final LayoutInflater mInflater = LayoutInflater.from(getMContext());

        customHearing = new CustomHearing();
        lists = new ArrayList<>();
        lists.add(new Tag("华丽"));
        lists.add(new Tag("神秘"));
        lists.add(new Tag("安静"));
        lists.add(new Tag("严肃"));
        lists.add(new Tag("恶搞"));
        lists.add(new Tag("抒情"));
        lists.add(new Tag("纯净"));
        lists.add(new Tag("大气磅礴"));
        lists.add(new Tag("优美"));
        lists.add(new Tag("空灵"));
        lists.add(new Tag("优雅"));
        lists.add(new Tag("奋斗"));
        lists.add(new Tag("炫酷"));
        lists.add(new Tag("压抑感"));
        lists.add(new Tag("动感"));
        lists.add(new Tag("回忆"));
        lists.add(new Tag("悲伤"));
        lists.add(new Tag("激烈"));
        lists.add(new Tag("紧张"));
        lists.add(new Tag("悲壮"));
        lists.add(new Tag("危险"));
        lists.add(new Tag("凄凉"));


        idFlowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

                customHearing.setInfo(String.valueOf(selectPosSet));
                ((CustomActivity)getActivity()).setCustomHearing(customHearing);
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
