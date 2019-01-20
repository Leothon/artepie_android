package com.leothon.cogito.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leothon.cogito.Bean.ClassItem;
import com.leothon.cogito.Bean.TeacherSelf;
import com.leothon.cogito.DTO.TeaClass;
import com.leothon.cogito.Mvp.View.Activity.PayInfoActivity.PayInfoActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Weight.BottomButton;

import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.8.4
 * 教师主页的adapter;
 */
public class TeacherSelfAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private TeaClass teaClass;
    private Context context;

    private int HEAD = 0;
    private int DESCRIPTION = 1;
    private int BODY = 100;
    private View headView;
    private View descriptionView;
    public TeacherSelfAdapter(TeaClass teaClass, Context context){
        this.teaClass = teaClass;
        this.context =context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD){
            return new TeacherHeadHolder(LayoutInflater.from(context).inflate(R.layout.teacher_background,parent,false));
        }else if (viewType == DESCRIPTION){
            return new TeacherDesHolder(LayoutInflater.from(context).inflate(R.layout.teacher_background_description,parent,false));
        }else {
            return new ClassItemHolder(LayoutInflater.from(context).inflate(R.layout.class_item,parent,false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == HEAD){
            TeacherHeadHolder teacherHeadHolder = (TeacherHeadHolder)holder;
            ImageLoader.loadImageViewThumbnailwitherror(context,teaClass.getTeacher().getUser_icon(),teacherHeadHolder.teaIcon,R.drawable.defaulticon);
            teacherHeadHolder.teaName.setText(teaClass.getTeacher().getUser_name());
        }else if (viewType == DESCRIPTION){
            TeacherDesHolder teacherDesHolder = (TeacherDesHolder)holder;
            teacherDesHolder.teaDescription.setText(teaClass.getTeacher().getUser_signal());
        }else if (viewType == BODY){
            final int realposition = position - 2;
            final ClassItemHolder classItemHolder = (ClassItemHolder)holder;
            classItemHolder.classTitle.setText(teaClass.getTeaClassses().get(realposition).getSelectlisttitle());
            classItemHolder.classDescription.setText(teaClass.getTeaClassses().get(realposition).getSelectdesc());
            classItemHolder.classCount.setText(teaClass.getTeaClassses().get(realposition).getSelectstucount() + "人次已学习");
            ImageLoader.loadImageViewThumbnailwitherror(context,teaClass.getTeaClassses().get(realposition).getSelectbackimg(),classItemHolder.classImg,R.drawable.defalutimg);
            if (teaClass.getTeaClassses().get(realposition).getSelectprice().equals("0.00")){
                classItemHolder.classPrice.setText("免费");
            }else if (teaClass.getTeaClassses().get(realposition).isIsbuy()){
                classItemHolder.classPrice.setText("已购买");

            }else {
                classItemHolder.classPrice.setText("￥" + teaClass.getTeaClassses().get(realposition).getSelectprice());
            }

            classItemHolder.classPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!teaClass.getTeaClassses().get(realposition).isIsbuy() && !teaClass.getTeaClassses().get(realposition).getSelectprice().equals("0.00")){

                        loadPayDialog(teaClass.getTeaClassses().get(realposition).getSelectId());
                    }
                }
            });
            classItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int realposition = position - 2;

                    if (classItemHolder.classPrice.getText().toString().equals("已购买") || classItemHolder.classPrice.getText().toString().equals("免费")){
                        Bundle bundle = new Bundle();
                        bundle.putString("classId",teaClass.getTeaClassses().get(realposition).getSelectId());
                        IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                    }else {
                        loadDialog(teaClass.getTeaClassses().get(realposition).getSelectId());
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
        return teaClass.getTeaClassses().size() + 2;
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


    class TeacherHeadHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tea_icon_list)
        RoundedImageView teaIcon;
        @BindView(R.id.tea_name_list)
        TextView teaName;
        public TeacherHeadHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class TeacherDesHolder extends RecyclerView.ViewHolder{


        @BindView(R.id.tea_description_list)
        TextView teaDescription;
        public TeacherDesHolder(View itemView) {
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
        public ClassItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
