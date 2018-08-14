package com.leothon.cogito.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leothon.cogito.Bean.Ask;
import com.leothon.cogito.Mvp.View.Activity.HomeActivity.HostActivity;
import com.leothon.cogito.Mvp.View.Fragment.AskPage.AskFragment;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.8.8
 */
public class AskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ArrayList<Ask> asks;
    public AskAdapter(Context context, ArrayList<Ask> asks){
        this.context = context;
        this.asks = asks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AskViewHolder(LayoutInflater.from(context).inflate(R.layout.askitem,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Ask ask = asks.get(position);
        AskViewHolder askViewHolder = (AskViewHolder) holder;

        ImageLoader.loadImageViewThumbnailwitherror(context,ask.getUsericonurl(),askViewHolder.userIcon,R.drawable.defalutimg);
        ImageLoader.loadImageViewThumbnailwitherror(context,ask.getContenturl(),askViewHolder.img1,R.drawable.defalutimg);
        askViewHolder.userName.setText(ask.getUsername());
        askViewHolder.userDes.setText(ask.getUserdes());
        askViewHolder.contentAsk.setText(ask.getContent());

        askViewHolder.img2.setVisibility(View.GONE);
        askViewHolder.img3.setVisibility(View.GONE);
        askViewHolder.img4.setVisibility(View.GONE);
        askViewHolder.img5.setVisibility(View.GONE);
        askViewHolder.img6.setVisibility(View.GONE);
        askViewHolder.img7.setVisibility(View.GONE);
        askViewHolder.img8.setVisibility(View.GONE);
        askViewHolder.img9.setVisibility(View.GONE);
        askViewHolder.userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转个人主页
                CommonUtils.makeText(context,"个人主页");
            }
        });
        askViewHolder.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转个人主页
                CommonUtils.makeText(context,"个人主页");
            }
        });
        askViewHolder.userDes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转个人主页
                CommonUtils.makeText(context,"个人主页");
            }
        });

        askViewHolder.contentAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转详情页面
                CommonUtils.makeText(context,"详情");
            }
        });

        askViewHolder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转大图
                LayoutInflater inflater = LayoutInflater.from(context);
                View imgEntryView = inflater.inflate(R.layout.image, null); // 加载自定义的布局文件
                final AlertDialog dialog = new AlertDialog.Builder(context).create();
                ImageView img = (ImageView)imgEntryView.findViewById(R.id.image_big);
                ImageLoader.loadImageViewwithError(context,ask.getContenturl(),img,R.drawable.defalutimg);
                //img.setImageResource(R.drawable.defalutimg);
                dialog.setView(imgEntryView); // 自定义dialog
                dialog.show();
                // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
                imgEntryView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View paramView) {
                        dialog.cancel();
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return asks.size();
    }


    public  class AskViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_icon_ask)
        RoundedImageView userIcon;
        @BindView(R.id.user_name_ask)
        TextView userName;
        @BindView(R.id.user_des_ask)
        TextView userDes;
        @BindView(R.id.content_ask)
        TextView contentAsk;
        @BindView(R.id.image_in_context1)
        ImageView img1;
        @BindView(R.id.image_in_context2)
        ImageView img2;
        @BindView(R.id.image_in_context3)
        ImageView img3;
        @BindView(R.id.image_in_context4)
        ImageView img4;
        @BindView(R.id.image_in_context5)
        ImageView img5;
        @BindView(R.id.image_in_context6)
        ImageView img6;
        @BindView(R.id.image_in_context7)
        ImageView img7;
        @BindView(R.id.image_in_context8)
        ImageView img8;
        @BindView(R.id.image_in_context9)
        ImageView img9;

        public AskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
