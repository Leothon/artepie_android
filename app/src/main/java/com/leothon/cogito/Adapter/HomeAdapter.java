package com.leothon.cogito.Adapter;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.Mvp.View.Activity.TestActivity.TestActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.Weight.CommonDialog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.7.30
 * 专为首页复杂的布局编写的adapter
 */
public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{





    private String[] testesName = {"民族","美声","古典","戏曲","原生态","民谣","通俗","其他"};

    private Context context;

    private ArrayList<SelectClass> selectClasses;

    private boolean isLogin;
    public HomeAdapter(ArrayList<SelectClass> selectClasses,Context context,boolean isLogin){
        this.context = context;
        this.isLogin = isLogin;
        this.selectClasses = selectClasses;
    }
    private int HEAD0 = 0;
    private int HEAD1 = 1;
    private int HEAD2 = 2;
    private int HEAD3 = 3;
    private int HEAD4 = 4;
    private int HEAD5 = 5;


    private View headView0;
    private View headView1;
    private View headView2;
    private View headView3;



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == HEAD0) {
            return new BannerHolder(headView0);
        }else if(viewType == HEAD1){
            return new TeaHolder(headView1);
        }else if (viewType == HEAD2){
            return new testHolder(LayoutInflater.from(context).inflate(R.layout.test_home,parent,false));
        }else if(viewType == HEAD3){
            return new dividerHolder(LayoutInflater.from(context).inflate(R.layout.dividerview,parent,false));
        }else if (viewType == HEAD4){
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

        }else if (viewType == HEAD3){
            return;
        }else if (viewType == HEAD4){
            final foryouHolder foryouholder = (foryouHolder) holder;
            final int videoPosition = position - 4;
            final SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(context,"saveToken");
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

                    if (foryouholder.foryouPrice.getText().toString().equals("已购买") || foryouholder.foryouPrice.getText().toString().equals("免费") || selectClasses.get(pos).getSelectauthorid().equals(tokenUtils.ValidToken(sharedPreferencesUtils.getParams("token","").toString()).getUid())){
                        Bundle bundle = new Bundle();
                        bundle.putString("classId",selectClasses.get(pos).getSelectId());
                        IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                    }else {
                        if (isLogin){
                            String classId = selectClasses.get(pos).getSelectId();
                            loadDialog(classId);
                        }else {
                            CommonUtils.loadinglogin(context);
                        }

                    }
                }
            });


        }else {
            return;
        }


    }


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
        }else if (position == 3 && headView3 != null){
            return HEAD3;
        }else if (position <= (selectClasses.size() + 3) && position != 0){
            return HEAD4;
        }else {
            return HEAD5;
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




}
