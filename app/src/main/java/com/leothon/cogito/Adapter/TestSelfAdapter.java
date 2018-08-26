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

import com.leothon.cogito.Bean.TeacherSelf;
import com.leothon.cogito.Bean.TestSelf;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;

import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestSelfAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private TestSelf testSelf;
    private Context context;

    private int HEAD = 0;
    private int DESCRIPTION = 1;
    private int BODY = 100;
    private View headView;
    private View descriptionView;

    public TestSelfAdapter(TestSelf testSelf, Context context){
        this.testSelf = testSelf;
        this.context =context;
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
        if (viewType == HEAD){
            TestHeadHolder testHeadHolder= (TestHeadHolder) holder;
            testHeadHolder.testTitle.setText(testSelf.getTesttitle());
            testHeadHolder.testBackImg.setImageResource(R.drawable.activityback);
        }else if (viewType == DESCRIPTION){
            TestDesHolder testDesHolder = (TestDesHolder)holder;

            testDesHolder.testDescription.setText(testSelf.getTestdescription());
        }else if (viewType == BODY){
            int realposition = position - 2;
            ClassItemHolder classItemHolder = (ClassItemHolder)holder;
            classItemHolder.classTitle.setText(testSelf.getTestclasses().get(realposition).getClasstitle());
            classItemHolder.classDescription.setText(testSelf.getTestclasses().get(realposition).getClassdescription());
            if (testSelf.getTestclasses().get(realposition).getClassprice().equals("0")){
                classItemHolder.classPrice.setText("免费");
            }else {
                classItemHolder.classPrice.setText("￥" + testSelf.getTestclasses().get(realposition).getClassprice());
            }

            classItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int realposition = position -2;
                    if (testSelf.getTestclasses().get(realposition).getClassprice().equals("0")){
                        Bundle bundle = new Bundle();
                        bundle.putString("url",testSelf.getTestclasses().get(realposition).getClassurl());
                        bundle.putString("title",testSelf.getTestclasses().get(realposition).getClasstitle());
                        bundle.putString("author",testSelf.getTestclasses().get(realposition).getAuthorname());
                        IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                    }else {
                        //TODO 跳转支付页面
                    }
                }
            });
        }
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
        return testSelf.getTestclasses().size() + 2;
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



        @BindView(R.id.test_description_list)
        TextView testDescription;
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
        public ClassItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
