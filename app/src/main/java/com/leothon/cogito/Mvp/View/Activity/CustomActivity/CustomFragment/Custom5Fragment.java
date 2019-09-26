package com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leothon.cogito.Bean.CustomStyle;
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


public class Custom5Fragment extends BaseFragment {

    @BindView(R.id.style_choose)
    TagFlowLayout idFlowlayout;
    TagAdapter mAdapter;
    private ArrayList<Tag> lists;
    public Custom5Fragment() { }

    private String result = "";
    private CustomStyle customStyle;
    public static Custom5Fragment newInstance() {
        Custom5Fragment fragment = new Custom5Fragment();
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_custom5;
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

        customStyle = new CustomStyle();
        lists = new ArrayList<>();
        lists.add(new Tag("中国风"));
        lists.add(new Tag("管弦"));
        lists.add(new Tag("打击"));
        lists.add(new Tag("电子"));
        lists.add(new Tag("摇滚"));
        lists.add(new Tag("古典"));
        lists.add(new Tag("爵士"));
        lists.add(new Tag("民谣"));
        lists.add(new Tag("拉丁"));
        lists.add(new Tag("乡村"));
        lists.add(new Tag("中国"));
        lists.add(new Tag("中东"));
        lists.add(new Tag("日本"));
        lists.add(new Tag("印度"));
        lists.add(new Tag("蒙古"));
        lists.add(new Tag("法国"));
        lists.add(new Tag("夏威夷"));
        lists.add(new Tag("苏格兰"));
        lists.add(new Tag("爱尔兰"));
        lists.add(new Tag("非洲"));
        lists.add(new Tag("探戈"));
        lists.add(new Tag("舞曲"));
        lists.add(new Tag("圆舞曲"));
        lists.add(new Tag("迪斯科"));
        lists.add(new Tag("蓝调"));
        lists.add(new Tag("牛仔"));




        idFlowlayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

                customStyle.setInfo(String.valueOf(selectPosSet));
                ((CustomActivity)getActivity()).setCustomStyle(customStyle);
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
