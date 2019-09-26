package com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Adapter.UseAdapter;
import com.leothon.cogito.Bean.CustomUse;
import com.leothon.cogito.Bean.Tag;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomActivity;
import com.leothon.cogito.R;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;


public class Custom2Fragment extends BaseFragment {


    @BindView(R.id.use1_choose)
    TagFlowLayout idFlowlayout1;
    @BindView(R.id.use2_choose)
    TagFlowLayout idFlowlayout2;
    @BindView(R.id.use3_choose)
    TagFlowLayout idFlowlayout3;
    @BindView(R.id.use4_choose)
    TagFlowLayout idFlowlayout4;
    @BindView(R.id.use5_choose)
    TagFlowLayout idFlowlayout5;
    @BindView(R.id.use6_choose)
    TagFlowLayout idFlowlayout6;
    TagAdapter mAdapter;

    private ArrayList<Tag> lists1;
    private ArrayList<Tag> lists2;
    private ArrayList<Tag> lists3;
    private ArrayList<Tag> lists4;
    private ArrayList<Tag> lists5;
    private ArrayList<Tag> lists6;

    private CustomUse customUse;

    public Custom2Fragment() {

    }


    public static Custom2Fragment newInstance() {
        Custom2Fragment fragment = new Custom2Fragment();
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_custom2;
    }

    @Override
    protected void initView() {
        customUse = new CustomUse();
        initAdapter1();
        initAdapter2();
        initAdapter3();
        initAdapter4();
        initAdapter5();
        initAdapter6();
    }


    private void initAdapter1() {
        final LayoutInflater mInflater = LayoutInflater.from(getMContext());



        idFlowlayout1.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                customUse.setInfo1(String.valueOf(selectPosSet));
                ((CustomActivity)getActivity()).setCustomUse(customUse);
            }
        });


        idFlowlayout1.setAdapter(mAdapter = new TagAdapter<Tag>(lists1) {
            @Override
            public View getView(FlowLayout parent, int position, Tag tag) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv_choose, idFlowlayout1, false);
                tv.setText(tag.getTagInfo());
                return tv;
            }

        });
        //设置选中的按钮
        mAdapter.setSelectedList(-1);

    }

    private void initAdapter2() {
        final LayoutInflater mInflater = LayoutInflater.from(getMContext());



        idFlowlayout1.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                customUse.setInfo2(String.valueOf(selectPosSet));
                ((CustomActivity)getActivity()).setCustomUse(customUse);
            }
        });


        idFlowlayout2.setAdapter(mAdapter = new TagAdapter<Tag>(lists2) {
            @Override
            public View getView(FlowLayout parent, int position, Tag tag) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv_choose, idFlowlayout2, false);
                tv.setText(tag.getTagInfo());
                return tv;
            }

        });
        //设置选中的按钮
        mAdapter.setSelectedList(-1);

    }
    private void initAdapter3() {
        final LayoutInflater mInflater = LayoutInflater.from(getMContext());



        idFlowlayout3.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                customUse.setInfo3(String.valueOf(selectPosSet));
                ((CustomActivity)getActivity()).setCustomUse(customUse);
            }
        });


        idFlowlayout3.setAdapter(mAdapter = new TagAdapter<Tag>(lists3) {
            @Override
            public View getView(FlowLayout parent, int position, Tag tag) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv_choose, idFlowlayout3, false);
                tv.setText(tag.getTagInfo());
                return tv;
            }

        });
        //设置选中的按钮
        mAdapter.setSelectedList(-1);

    }
    private void initAdapter4() {
        final LayoutInflater mInflater = LayoutInflater.from(getMContext());



        idFlowlayout4.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                customUse.setInfo4(String.valueOf(selectPosSet));
                ((CustomActivity)getActivity()).setCustomUse(customUse);
            }
        });


        idFlowlayout4.setAdapter(mAdapter = new TagAdapter<Tag>(lists4) {
            @Override
            public View getView(FlowLayout parent, int position, Tag tag) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv_choose, idFlowlayout4, false);
                tv.setText(tag.getTagInfo());
                return tv;
            }

        });
        //设置选中的按钮
        mAdapter.setSelectedList(-1);

    }
    private void initAdapter5() {
        final LayoutInflater mInflater = LayoutInflater.from(getMContext());



        idFlowlayout5.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

                customUse.setInfo5(String.valueOf(selectPosSet));
                ((CustomActivity)getActivity()).setCustomUse(customUse);
            }
        });


        idFlowlayout5.setAdapter(mAdapter = new TagAdapter<Tag>(lists5) {
            @Override
            public View getView(FlowLayout parent, int position, Tag tag) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv_choose, idFlowlayout5, false);
                tv.setText(tag.getTagInfo());
                return tv;
            }

        });
        //设置选中的按钮
        mAdapter.setSelectedList(-1);

    }
    private void initAdapter6() {
        final LayoutInflater mInflater = LayoutInflater.from(getMContext());



        idFlowlayout6.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> selectPosSet) {

                customUse.setInfo6(String.valueOf(selectPosSet));
                ((CustomActivity)getActivity()).setCustomUse(customUse);
            }
        });


        idFlowlayout6.setAdapter(mAdapter = new TagAdapter<Tag>(lists6) {
            @Override
            public View getView(FlowLayout parent, int position, Tag tag) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv_choose, idFlowlayout6, false);
                tv.setText(tag.getTagInfo());
                return tv;
            }

        });
        //设置选中的按钮
        mAdapter.setSelectedList(-1);

    }
    @Override
    protected void initData() {
        lists1 = new ArrayList<>();
        lists1.add(new Tag("正能量"));
        lists1.add(new Tag("励志"));
        lists1.add(new Tag("青春"));
        lists1.add(new Tag("浪漫"));
        lists1.add(new Tag("清新"));
        lists1.add(new Tag("放松"));
        lists1.add(new Tag("温馨"));
        lists1.add(new Tag("诙谐"));
        lists1.add(new Tag("大气"));
        lists1.add(new Tag("抒情"));
        lists1.add(new Tag("科技"));
        lists1.add(new Tag("运动"));
        lists1.add(new Tag("商务"));
        lists1.add(new Tag("母婴"));
        lists1.add(new Tag("时尚"));
        lists1.add(new Tag("公益"));
        lists1.add(new Tag("旅游"));
        lists1.add(new Tag("美食"));
        lists1.add(new Tag("酒吧"));
        lists1.add(new Tag("餐厅"));
        lists1.add(new Tag("酒店"));
        lists1.add(new Tag("咖啡厅"));
        lists1.add(new Tag("古韵古香"));
        lists1.add(new Tag("大好河山"));
        lists1.add(new Tag("商场超市"));
        lists1.add(new Tag("人文艺术"));
        lists2 = new ArrayList<>();
        lists2.add(new Tag("爱情"));
        lists2.add(new Tag("都市"));
        lists2.add(new Tag("家庭"));
        lists2.add(new Tag("喜剧"));
        lists2.add(new Tag("科幻"));
        lists2.add(new Tag("冒险"));
        lists2.add(new Tag("动画"));
        lists2.add(new Tag("战争"));
        lists2.add(new Tag("历史"));
        lists2.add(new Tag("动作"));
        lists2.add(new Tag("古装"));
        lists2.add(new Tag("军事"));
        lists2.add(new Tag("史诗"));
        lists2.add(new Tag("魔幻"));
        lists2.add(new Tag("体育"));
        lists2.add(new Tag("武侠"));
        lists2.add(new Tag("综艺"));
        lists2.add(new Tag("片头"));
        lists2.add(new Tag("新闻"));
        lists2.add(new Tag("剧情"));
        lists2.add(new Tag("故事"));
        lists2.add(new Tag("纪录片"));
        lists2.add(new Tag("预告片"));
        lists3 = new ArrayList<>();
        lists3.add(new Tag("城市"));
        lists3.add(new Tag("棋牌"));
        lists3.add(new Tag("战争"));
        lists3.add(new Tag("战斗"));
        lists3.add(new Tag("养成"));
        lists3.add(new Tag("策略"));
        lists3.add(new Tag("跑酷"));
        lists3.add(new Tag("捕鱼"));
        lists3.add(new Tag("村庄"));
        lists3.add(new Tag("场景"));
        lists3.add(new Tag("科幻"));
        lists3.add(new Tag("武侠"));
        lists3.add(new Tag("像素"));
        lists3.add(new Tag("史诗"));
        lists3.add(new Tag("胜利"));
        lists3.add(new Tag("失败"));
        lists3.add(new Tag("角色扮演"));
        lists3.add(new Tag("休闲益智"));
        lists3.add(new Tag("飞行射击"));
        lists3.add(new Tag("模拟经营"));
        lists4 = new ArrayList<>();
        lists4.add(new Tag("春节"));
        lists4.add(new Tag("新年"));
        lists4.add(new Tag("元旦"));
        lists4.add(new Tag("庆祝"));
        lists4.add(new Tag("婚礼"));
        lists4.add(new Tag("派对"));
        lists4.add(new Tag("元宵节"));
        lists4.add(new Tag("情人节"));
        lists4.add(new Tag("愚人节"));
        lists4.add(new Tag("青年节"));
        lists4.add(new Tag("父亲节"));
        lists4.add(new Tag("母亲节"));
        lists4.add(new Tag("劳动节"));
        lists4.add(new Tag("儿童节"));
        lists4.add(new Tag("端午节"));
        lists4.add(new Tag("七夕节"));
        lists4.add(new Tag("教师节"));
        lists4.add(new Tag("中秋节"));
        lists4.add(new Tag("国庆节"));
        lists4.add(new Tag("运动会"));
        lists5 = new ArrayList<>();
        lists5.add(new Tag("演讲"));
        lists5.add(new Tag("解说"));
        lists5.add(new Tag("教育"));
        lists5.add(new Tag("生活"));
        lists5.add(new Tag("促销"));
        lists5.add(new Tag("成就"));
        lists5.add(new Tag("瑜伽"));
        lists5.add(new Tag("舞蹈"));
        lists5.add(new Tag("搞笑"));
        lists5.add(new Tag("培训"));
        lists5.add(new Tag("成就"));
        lists5.add(new Tag("宣传片"));
        lists5.add(new Tag("美食"));
        lists6 = new ArrayList<>();
        lists6.add(new Tag("酒店"));
        lists6.add(new Tag("酒吧"));
        lists6.add(new Tag("商场"));
        lists6.add(new Tag("展览"));

    }
}
