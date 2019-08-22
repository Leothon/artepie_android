package com.leothon.cogito.Adapter;

import android.content.Context;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leothon.cogito.DTO.TypeClass;
import com.leothon.cogito.Mvp.View.Activity.PayInfoActivity.PayInfoActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;

import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestSelfAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private TypeClass typeClass;
    private Context context;

    private int HEAD = 0;
    private int DESCRIPTION = 1;
    private int BODY = 100;
    private View headView;
    private View descriptionView;
    private boolean isLogin;
    public TestSelfAdapter(TypeClass typeClass, Context context,boolean isLogin){
        this.typeClass = typeClass;
        this.context =context;
        this.isLogin = isLogin;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD){
            return new TestHeadHolder(LayoutInflater.from(context).inflate(R.layout.test_background,parent,false));
        }else if (viewType == DESCRIPTION){
            return new TestDesHolder(LayoutInflater.from(context).inflate(R.layout.test_background_description,parent,false));
        }else {
            return new ClassItemHolder(LayoutInflater.from(context).inflate(R.layout.class_item,parent,false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        final SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(context,"saveToken");
        if (viewType == HEAD){
            TestHeadHolder testHeadHolder= (TestHeadHolder) holder;
            testHeadHolder.testTitle.setText(typeClass.getType());
            testHeadHolder.testBackImg.setImageResource(R.drawable.activityback);
        }else if (viewType == DESCRIPTION){
            TestDesHolder testDesHolder = (TestDesHolder)holder;
            testDesHolder.testClassCount.setText("总课程数 ： " + typeClass.getTypeClassCount());
            testDesHolder.testClassDes.setText("显示描述");
        }else if (viewType == BODY){
            final int realposition = position - 2;
            final ClassItemHolder classItemHolder = (ClassItemHolder)holder;
            classItemHolder.classTitle.setText(typeClass.getTypeClass().get(realposition).getSelectlisttitle());
            classItemHolder.classDescription.setText(typeClass.getTypeClass().get(realposition).getSelectdesc());
            classItemHolder.classCount.setText(typeClass.getTypeClass().get(realposition).getSelectstucount() + "人次已学习");
            ImageLoader.loadImageViewThumbnailwitherror(context,typeClass.getTypeClass().get(realposition).getSelectbackimg(),classItemHolder.classImg,R.drawable.defalutimg);
            if (typeClass.getTypeClass().get(realposition).getSelectprice().equals("0.00")){
                classItemHolder.classPrice.setText("免费");
            }else if (typeClass.getTypeClass().get(realposition).isIsbuy()){
                classItemHolder.classPrice.setText("已购买");
            }else {
                classItemHolder.classPrice.setText("￥" + typeClass.getTypeClass().get(realposition).getSelectprice());
            }
            if (typeClass.getTypeClass().get(realposition).isAuthorize()){
                classItemHolder.authorize.setVisibility(View.VISIBLE);
            }else {
                classItemHolder.authorize.setVisibility(View.GONE);
            }
            if (typeClass.getTypeClass().get(realposition).isSerialize()){
                classItemHolder.serialize.setText(" 连载中... ");
            }else {
                classItemHolder.serialize.setText(" 已完结 ");
            }

            classItemHolder.classPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!typeClass.getTypeClass().get(realposition).isIsbuy() && !typeClass.getTypeClass().get(realposition).getSelectprice().equals("0.00") && !typeClass.getTypeClass().get(realposition).getSelectauthorid().equals(tokenUtils.ValidToken(sharedPreferencesUtils.getParams("token","").toString()).getUid())){

                        if (isLogin){
                            loadPayDialog(typeClass.getTypeClass().get(realposition).getSelectId());
                        }else {
                            CommonUtils.loadinglogin(context);
                        }

                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString("classId",typeClass.getTypeClass().get(realposition).getSelectId());
                        IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                    }
                }
            });
            classItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int realposition = position - 2;

                    if (classItemHolder.classPrice.getText().toString().equals("已购买") || classItemHolder.classPrice.getText().toString().equals("免费") || typeClass.getTypeClass().get(realposition).getSelectauthorid().equals(tokenUtils.ValidToken(sharedPreferencesUtils.getParams("token","").toString()).getUid())){
                        Bundle bundle = new Bundle();
                        bundle.putString("classId",typeClass.getTypeClass().get(realposition).getSelectId());
                        IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                    }else {
                        loadDialog(typeClass.getTypeClass().get(realposition).getSelectId());
                    }

                }
            });

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

    private void loadPayDialog(final String classId){
        final CommonDialog dialog = new CommonDialog(context);


        dialog.setMessage("是否购买本课程？")
                .setTitle("提醒")
                .setSingle(false)
                .setPositive("购买")
                .setNegtive("再看看")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("classId",classId);
                        IntentUtils.getInstence().intent(context, PayInfoActivity.class,bundle);
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
        if (position == 0 && headView != null){
            return HEAD;
        }else if (position == 1 && descriptionView != null){
            return DESCRIPTION;
        }else {
            return BODY;
        }
    }

    @Override
    public int getItemCount() {
        return typeClass.getTypeClass().size() + 2;
    }

    public void addHeadView(View view){
        this.headView = view;
    }

    public void addDesView(View view){
        this.descriptionView = view;
    }
    @Override
    public void onClick(View view) {

    }


    class TestHeadHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.test_back_img)
        ImageView testBackImg;
        @BindView(R.id.test_title_list)
        TextView testTitle;
        public TestHeadHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class TestDesHolder extends RecyclerView.ViewHolder{



        @BindView(R.id.test_class_count)
        TextView testClassCount;
        @BindView(R.id.test_class_des)
        TextView testClassDes;
        public TestDesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ClassItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.class_title)
        TextView classTitle;
        @BindView(R.id.class_description)
        TextView classDescription;
        @BindView(R.id.class_img)
        RoundedImageView classImg;
        @BindView(R.id.class_price)
        TextView classPrice;
        @BindView(R.id.class_count)
        TextView classCount;
        @BindView(R.id.serialize)
        TextView serialize;
        @BindView(R.id.authorize)
        TextView authorize;
        public ClassItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
