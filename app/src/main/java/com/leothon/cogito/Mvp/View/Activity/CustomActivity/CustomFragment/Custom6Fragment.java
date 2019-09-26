package com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leothon.cogito.Bean.CustomInstrument;
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


public class Custom6Fragment extends BaseFragment {
    @BindView(R.id.instrument_choose)
    TagFlowLayout idFlowlayout;
    TagAdapter mAdapter;
    private ArrayList<Tag> lists;

    private String result = "";
    private CustomInstrument customInstrument;
    public Custom6Fragment() {

    }


    public static Custom6Fragment newInstance() {
        Custom6Fragment fragment = new Custom6Fragment();
        return fragment;
    }


    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_custom6;
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

        customInstrument = new CustomInstrument();
        lists = new ArrayList<>();
        lists.add(new Tag("钢琴"));
        lists.add(new Tag("古筝"));
        lists.add(new Tag("竖琴"));
        lists.add(new Tag("长笛"));
        lists.add(new Tag("吉他"));
        lists.add(new Tag("电吉他"));
        lists.add(new Tag("号"));
        lists.add(new Tag("鼓"));
        lists.add(new Tag("弦乐"));
        lists.add(new Tag("管乐"));
        lists.add(new Tag("笛子"));
        lists.add(new Tag("二胡"));
        lists.add(new Tag("排钟"));
        lists.add(new Tag("扬琴"));
        lists.add(new Tag("大提琴"));
        lists.add(new Tag("尤克里里"));
        lists.add(new Tag("萨克斯"));
        lists.add(new Tag("风笛"));
        lists.add(new Tag("尺八"));
        lists.add(new Tag("琵琶"));
        lists.add(new Tag("小提琴"));
        lists.add(new Tag("钟琴"));
        lists.add(new Tag("萧"));
        lists.add(new Tag("铜管"));
        lists.add(new Tag("口哨"));
        lists.add(new Tag("葫芦丝"));
        lists.add(new Tag("唢呐"));
        lists.add(new Tag("马头琴"));




        idFlowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

                customInstrument.setInfo(String.valueOf(selectPosSet));
                ((CustomActivity)getActivity()).setCustomInstrument(customInstrument);
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
