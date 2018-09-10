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

    private TeacherSelf teacherSelfs;
    private Context context;

    private int HEAD = 0;
    private int DESCRIPTION = 1;
    private int BODY = 100;
    private View headView;
    private View descriptionView;
    public TeacherSelfAdapter(TeacherSelf teacherSelfs, Context context){
        this.teacherSelfs = teacherSelfs;
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
            teacherHeadHolder.teaIcon.setImageResource(teacherSelfs.getTeaicon());
            teacherHeadHolder.teaName.setText(teacherSelfs.getTeaname());
        }else if (viewType == DESCRIPTION){
            TeacherDesHolder teacherDesHolder = (TeacherDesHolder)holder;
            teacherDesHolder.teaDescription.setText(teacherSelfs.getTeadescription());
        }else if (viewType == BODY){
            int realposition = position - 2;
            ClassItemHolder classItemHolder = (ClassItemHolder)holder;
            classItemHolder.classTitle.setText(teacherSelfs.getClassItems().get(realposition).getClasstitle());
            classItemHolder.classDescription.setText(teacherSelfs.getClassItems().get(realposition).getClassdescription());
            ImageLoader.loadImageViewThumbnailwitherror(context,teacherSelfs.getClassItems().get(realposition).getClassurl(),classItemHolder.classImg,R.drawable.defalutimg);
            if (teacherSelfs.getClassItems().get(realposition).getClassprice().equals("0")){
                classItemHolder.classPrice.setText("免费");
            }else {
                classItemHolder.classPrice.setText("￥" + teacherSelfs.getClassItems().get(realposition).getClassprice());
            }

            classItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int realposition = position - 2;
                    if (teacherSelfs.getClassItems().get(realposition).getClassprice().equals("0")){
                        Bundle bundle = new Bundle();
                        bundle.putString("url",teacherSelfs.getClassItems().get(realposition).getClassurl());
                        bundle.putString("title",teacherSelfs.getClassItems().get(realposition).getClasstitle());
                        bundle.putString("author",teacherSelfs.getClassItems().get(realposition).getAuthorname());
                        bundle.putString("price",teacherSelfs.getClassItems().get(realposition).getClassprice());
                        bundle.putString("desc",teacherSelfs.getClassItems().get(realposition).getClassdescription());
                        IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                    }else {
                        loadDialog(teacherSelfs.getClassItems().get(realposition));
                    }

                }
            });
        }
    }

    private void loadDialog(final ClassItem classItem){
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
                        bundle.putString("url",classItem.getClassurl());
                        bundle.putString("title",classItem.getClasstitle());
                        bundle.putString("author",classItem.getAuthorname());
                        bundle.putString("price",classItem.getClassprice());
                        bundle.putString("desc",classItem.getClassdescription());
                        IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                    }

                    @Override
                    public void onNegtiveClick() {
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
        return teacherSelfs.getClassItems().size() + 2;
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
        public ClassItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

}
