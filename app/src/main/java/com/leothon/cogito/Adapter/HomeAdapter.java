package com.leothon.cogito.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Bean.ClassItem;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.HomeData;
import com.leothon.cogito.Mvp.View.Activity.ActivityActivity.ActivityActivity;
import com.leothon.cogito.Mvp.View.Activity.ActivityListActivity.ActivityListActivity;
import com.leothon.cogito.Mvp.View.Activity.PlayerActivity.PlayerActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.Mvp.View.Activity.TestActivity.TestActivity;
import com.leothon.cogito.Mvp.View.Activity.ViewPagerActivity.ViewPageActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.7.30
 * 专为首页复杂的布局编写的adapter
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    //首页布局总数

    //需要在首页加载的一整条数据
    private HomeData allDatas;



    private String[] testesName = {"民族","美声","古典","戏曲","原生态","民谣","通俗","其他"};

    private Context context;

    private ArrayList<SelectClass> selectClasses;

    public HomeAdapter(HomeData allDatas, ArrayList<SelectClass> selectClasses,Context context){
        this.allDatas = allDatas;
        this.context = context;
        this.selectClasses = selectClasses;
    }
    private int HEAD0 = 0;
    private int HEAD1 = 1;
    private int HEAD2 = 2;
    private int HEAD3 = 3;
    private int HEAD4 = 4;
    private int HEAD5 = 5;
    private int HEAD6 = 6;
    private int HEAD7 = 7;


    private View headView0;
    private View headView1;
    private View headView2;
    private View headView3;
    private View headView4;
    private View headView5;
    private View headView6;
    private View headView7;


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HEAD0) {
            return new BannerHolder(headView0);
        }else if(viewType == HEAD1){
            return new TeaHolder(headView1);
        }else if (viewType == HEAD2){
            return new testHolder(LayoutInflater.from(context).inflate(R.layout.test_home,parent,false));
        }else if(viewType == HEAD3){
            return new activityHolder(LayoutInflater.from(context).inflate(R.layout.tagcommon_home,parent,false));
        }else if(viewType == HEAD4){
            return new classHolder(LayoutInflater.from(context).inflate(R.layout.tagcommon_home,parent,false));
        }else if(viewType == HEAD5){
            return new dividerHolder(LayoutInflater.from(context).inflate(R.layout.dividerview,parent,false));
        }else if (viewType == HEAD6){
            return new foryouHolder(LayoutInflater.from(context).inflate(R.layout.videoforyou_home,parent,false));
        }else {
            return new bottomHolder(LayoutInflater.from(context).inflate(R.layout.bottom_show_empty,parent,false));
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == HEAD0 ) {
            return;
        }else if(viewType == HEAD1){
            return;
        }else if(viewType == HEAD2) {
            testHolder testholder = (testHolder) holder;

            testholder.test1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", testesName[0]);
                    IntentUtils.getInstence().intent(context, TestActivity.class, bundle);
                }
            });
            testholder.test2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", testesName[1]);
                    IntentUtils.getInstence().intent(context, TestActivity.class, bundle);
                }
            });
            testholder.test3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", testesName[2]);
                    IntentUtils.getInstence().intent(context, TestActivity.class, bundle);
                }
            });
            testholder.test4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", testesName[3]);
                    IntentUtils.getInstence().intent(context, TestActivity.class, bundle);
                }
            });
            testholder.test5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", testesName[4]);
                    IntentUtils.getInstence().intent(context, TestActivity.class, bundle);
                }
            });
            testholder.test6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", testesName[5]);
                    IntentUtils.getInstence().intent(context, TestActivity.class, bundle);
                }
            });
            testholder.test7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", testesName[6]);
                    IntentUtils.getInstence().intent(context, TestActivity.class, bundle);
                }
            });
            testholder.test8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("type", testesName[7]);
                    IntentUtils.getInstence().intent(context, TestActivity.class, bundle);
                }
            });

            testholder.test_title1.setText(testesName[0]);
            testholder.test_title2.setText(testesName[1]);
            testholder.test_title3.setText(testesName[2]);
            testholder.test_title4.setText(testesName[3]);
            testholder.test_title5.setText(testesName[4]);
            testholder.test_title6.setText(testesName[5]);
            testholder.test_title7.setText(testesName[6]);
            testholder.test_title8.setText(testesName[7]);

        }
//        }else if (viewType == HEAD3){
//
//            activityHolder activityholder = (activityHolder) holder;
//            activityholder.tagnameInActivity.setText("演出观摩");
//            activityholder.activity1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("title",activitiesName[0]);
//                    bundle.putString("url",activitiesImg[0]);
//                    IntentUtils.getInstence().intent(context, ActivityActivity.class,bundle);
//                }
//            });
//            activityholder.activity2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("title",activitiesName[1]);
//                    bundle.putString("url",activitiesImg[1]);
//                    IntentUtils.getInstence().intent(context, ActivityActivity.class,bundle);
//                }
//            });
//            activityholder.activity3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("title",activitiesName[2]);
//                    bundle.putString("url",activitiesImg[2]);
//                    IntentUtils.getInstence().intent(context, ActivityActivity.class,bundle);
//                }
//            });
//            activityholder.activity4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("title",activitiesName[3]);
//                    bundle.putString("url",activitiesImg[3]);
//                    IntentUtils.getInstence().intent(context, ActivityActivity.class,bundle);
//                }
//            });
//            activityholder.activity5.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("title",activitiesName[4]);
//                    bundle.putString("url",activitiesImg[4]);
//                    IntentUtils.getInstence().intent(context, ActivityActivity.class,bundle);
//                }
//            });
//            activityholder.activity6.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    /**
//                     * 此处的数据直接在ActivityListActivity中请求
//                     */
//                    Bundle bundle = new Bundle();
//                    bundle.putString("title","更多演出活动");
//                    IntentUtils.getInstence().intent(context, ActivityListActivity.class,bundle);
//                }
//            });
//
//            activityholder.activity1title.setText(activitiesName[0]);
//            activityholder.activity2title.setText(activitiesName[1]);
//            activityholder.activity3title.setText(activitiesName[2]);
//            activityholder.activity4title.setText(activitiesName[3]);
//            activityholder.activity5title.setText(activitiesName[4]);
//
//            //加载图片
//            ImageLoader.loadImageViewThumbnailwitherror(context,activitiesImg[0],activityholder.activity1img,R.drawable.defalutimg);
//            ImageLoader.loadImageViewThumbnailwitherror(context,activitiesImg[1],activityholder.activity2img,R.drawable.defalutimg);
//            ImageLoader.loadImageViewThumbnailwitherror(context,activitiesImg[2],activityholder.activity3img,R.drawable.defalutimg);
//            ImageLoader.loadImageViewThumbnailwitherror(context,activitiesImg[3],activityholder.activity4img,R.drawable.defalutimg);
//            ImageLoader.loadImageViewThumbnailwitherror(context,activitiesImg[4],activityholder.activity5img,R.drawable.defalutimg);
//
//        }else if(viewType == HEAD4){
//            classHolder classholder = (classHolder) holder;
//            classholder.tagnameInClass.setText("公益讲堂");
//
//             //TODO 需要判定是讲课还是活动，分别进行跳转
//            classholder.class1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("imgUrls",classesImg[0]);
//                    bundle.putString("imgTitle",classesName[0]);
//                    bundle.putInt("count",1);
//                    bundle.putInt("position",0);
//                    bundle.putString("price","0");
//                    IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
//                }
//            });
//            classholder.class2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("imgUrls",classesImg[1]);
//                    bundle.putString("imgTitle",classesName[1]);
//                    bundle.putInt("count",1);
//                    bundle.putInt("position",0);
//                    bundle.putString("price","0");
//                    IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
//                }
//            });
//            classholder.class3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("imgUrls",classesImg[2]);
//                    bundle.putString("imgTitle",classesName[2]);
//                    bundle.putInt("count",1);
//                    bundle.putInt("position",0);
//                    bundle.putString("price","0");
//                    IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
//                }
//            });
//            classholder.class4.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("imgUrls",classesImg[3]);
//                    bundle.putString("imgTitle",classesName[3]);
//                    bundle.putInt("count",1);
//                    bundle.putInt("position",0);
//                    bundle.putString("price","0");
//                    IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
//                }
//            });
//            classholder.class5.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Bundle bundle = new Bundle();
//                    bundle.putString("imgUrls",classesImg[4]);
//                    bundle.putString("imgTitle",classesName[4]);
//                    bundle.putInt("count",1);
//                    bundle.putInt("position",0);
//                    bundle.putString("price","0");
//                    IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
//                }
//            });
//            classholder.class6.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    /**
//                     * 内容点击进去进行请求
//                     */
//                    Bundle bundle = new Bundle();
//                    bundle.putString("title","更多公益内容");
//                    IntentUtils.getInstence().intent(context, ViewPageActivity.class,bundle);
//                }
//            });
//
//            classholder.class1title.setText(classesName[0]);
//            classholder.class2title.setText(classesName[1]);
//            classholder.class3title.setText(classesName[2]);
//            classholder.class4title.setText(classesName[3]);
//            classholder.class5title.setText(classesName[4]);
//
//            //加载图片
//            ImageLoader.loadImageViewThumbnailwitherror(context,classesImg[0],classholder.class1img,R.drawable.defalutimg);
//            ImageLoader.loadImageViewThumbnailwitherror(context,classesImg[1],classholder.class2img,R.drawable.defalutimg);
//            ImageLoader.loadImageViewThumbnailwitherror(context,classesImg[2],classholder.class3img,R.drawable.defalutimg);
//            ImageLoader.loadImageViewThumbnailwitherror(context,classesImg[3],classholder.class4img,R.drawable.defalutimg);
//            ImageLoader.loadImageViewThumbnailwitherror(context,classesImg[4],classholder.class5img,R.drawable.defalutimg);

        else if (viewType == HEAD5){
            return;
        }else if (viewType == HEAD6){
            //TODO 增加更多的数据显示
            final foryouHolder foryouholder = (foryouHolder) holder;
            final int videoPosition = position - 4;
            //foryouholder.foryouIV.setTag(videoPosition);

            foryouholder.foryouTV.setText(selectClasses.get(videoPosition).getSelectlisttitle());
            ImageLoader.loadImageViewThumbnailwitherror(context,selectClasses.get(videoPosition).getSelectbackimg(),foryouholder.foryouIV,R.drawable.defalutimg);
            foryouholder.foryouAuthor.setText(selectClasses.get(videoPosition).getSelectauthor());
            foryouholder.foryouCount.setText(selectClasses.get(videoPosition).getSelectstucount() + "人次已学习");
            String price = selectClasses.get(videoPosition).getSelectprice();
            if (selectClasses.get(videoPosition).isIsbuy()){
                foryouholder.foryouPrice.setText("已购买");
            }else if (price.equals("0.00")){
                foryouholder.foryouPrice.setText("免费");
            }else {
                foryouholder.foryouPrice.setText("￥" + price);
            }
            foryouholder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = videoPosition;
                    if (foryouholder.foryouPrice.getText().toString().equals("已购买") || foryouholder.foryouPrice.getText().toString().equals("免费")){
                        Bundle bundle = new Bundle();
                        bundle.putString("classId",selectClasses.get(pos).getSelectId());
                        IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                    }else {
                        String classId = selectClasses.get(pos).getSelectId();
                        loadDialog(classId);
                    }
                }
            });


        }else {
            return;
        }


    }

//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof GridLayoutManager){
//            final GridLayoutManager gridLayoutManager = ((GridLayoutManager)manager);
//            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    int type = getItemViewType(position);
//                    if (type == HEAD6){
//                        return 1;
//                    }else {
//                        return 2;
//                    }
//                }
//            });
//        }
//    }

    private void loadDialog(final String classId){
        final CommonDialog dialog = new CommonDialog(context);


        dialog.setMessage("该课程是付费课程，您尚未订阅")
                .setTitle("提醒")
                .setSingle(false)
                .setPositive("试看前几节")
                .setNegtive("返回")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("classId",classId);
                        IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && headView0 != null) {
            return HEAD0;
        }else if (position == 1 && headView1 != null){
            return HEAD1;
        }else if (position == 2 && headView2 != null){
            return HEAD2;
//        }else if (position == 3 && headView3 != null){
//            return HEAD3;
//        }else if (position == 4 && headView4 != null){
//            return HEAD4;
        }else if (position == 3 && headView5 != null){
            return HEAD5;
        }else if (position <= (selectClasses.size() + 3) && position != 0){
            return HEAD6;
        }else {
            return HEAD7;
        }

    }

    @Override
    public int getItemCount() {

        return 5 + selectClasses.size();
    }

    public void addHeadView0(View view){
        this.headView0 = view;
    }
    public void addHeadView1(View view){
        this.headView1 = view;
    }
    public void addHeadView2(View view){
        this.headView2 = view;
    }
    public void addHeadView3(View view){
        this.headView3 = view;
    }
    public void addHeadView4(View view){
        this.headView4 = view;
    }
//    public void addHeadView5(View view){
//        this.headView5 = view;
//    }
    public void addHeadView5(View view){
        this.headView5 = view;
    }
    @Override
    public void onClick(View view) {

    }

    /**
     * bannerholder
     */
    class BannerHolder extends RecyclerView.ViewHolder{

        public BannerHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    /**
     * 名师专栏holder
     */
    class TeaHolder extends RecyclerView.ViewHolder{

        public TeaHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class activityHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tag_name)
        TextView tagnameInActivity;
        @BindView(R.id.home1)
        FrameLayout activity1;
        @BindView(R.id.home2)
        FrameLayout activity2;
        @BindView(R.id.home3)
        FrameLayout activity3;
        @BindView(R.id.home4)
        FrameLayout activity4;
        @BindView(R.id.home5)
        FrameLayout activity5;
        @BindView(R.id.home6)
        FrameLayout activity6;

        @BindView(R.id.home_title1)
        TextView activity1title;
        @BindView(R.id.home_title2)
        TextView activity2title;
        @BindView(R.id.home_title3)
        TextView activity3title;
        @BindView(R.id.home_title4)
        TextView activity4title;
        @BindView(R.id.home_title5)
        TextView activity5title;

        @BindView(R.id.home_img1)
        RoundedImageView activity1img;
        @BindView(R.id.home_img2)
        RoundedImageView activity2img;
        @BindView(R.id.home_img3)
        RoundedImageView activity3img;
        @BindView(R.id.home_img4)
        RoundedImageView activity4img;
        @BindView(R.id.home_img5)
        RoundedImageView activity5img;

        public activityHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class testHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.test1)
        RelativeLayout test1;
        @BindView(R.id.test2)
        RelativeLayout test2;
        @BindView(R.id.test3)
        RelativeLayout test3;
        @BindView(R.id.test4)
        RelativeLayout test4;
        @BindView(R.id.test5)
        RelativeLayout test5;
        @BindView(R.id.test6)
        RelativeLayout test6;
        @BindView(R.id.test7)
        RelativeLayout test7;
        @BindView(R.id.test8)
        RelativeLayout test8;

        @BindView(R.id.test_title1)
        TextView test_title1;
        @BindView(R.id.test_title2)
        TextView test_title2;
        @BindView(R.id.test_title3)
        TextView test_title3;
        @BindView(R.id.test_title4)
        TextView test_title4;
        @BindView(R.id.test_title5)
        TextView test_title5;
        @BindView(R.id.test_title6)
        TextView test_title6;
        @BindView(R.id.test_title7)
        TextView test_title7;
        @BindView(R.id.test_title8)
        TextView test_title8;



        public testHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class classHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tag_name)
        TextView tagnameInClass;
        @BindView(R.id.home1)
        FrameLayout class1;
        @BindView(R.id.home2)
        FrameLayout class2;
        @BindView(R.id.home3)
        FrameLayout class3;
        @BindView(R.id.home4)
        FrameLayout class4;
        @BindView(R.id.home5)
        FrameLayout class5;
        @BindView(R.id.home6)
        FrameLayout class6;

        @BindView(R.id.home_title1)
        TextView class1title;
        @BindView(R.id.home_title2)
        TextView class2title;
        @BindView(R.id.home_title3)
        TextView class3title;
        @BindView(R.id.home_title4)
        TextView class4title;
        @BindView(R.id.home_title5)
        TextView class5title;

        @BindView(R.id.home_img1)
        RoundedImageView class1img;
        @BindView(R.id.home_img2)
        RoundedImageView class2img;
        @BindView(R.id.home_img3)
        RoundedImageView class3img;
        @BindView(R.id.home_img4)
        RoundedImageView class4img;
        @BindView(R.id.home_img5)
        RoundedImageView class5img;
        public classHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    class dividerHolder extends RecyclerView.ViewHolder{

        public dividerHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class foryouHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.foryou_iv)
        ImageView foryouIV;
        @BindView(R.id.foryou_tv)
        TextView foryouTV;
        @BindView(R.id.foryou_price)
        TextView foryouPrice;
        @BindView(R.id.foryou_author)
        TextView foryouAuthor;
        @BindView(R.id.foryou_count)
        TextView foryouCount;
        public foryouHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class bottomHolder extends RecyclerView.ViewHolder{

        public bottomHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View v,int postion);
        void onItemLongClick(View v,int postion);
    }
    /**自定义条目点击监听*/
    private OnItemClickListener mOnItemClickLitener;

    public void setmOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }


}
