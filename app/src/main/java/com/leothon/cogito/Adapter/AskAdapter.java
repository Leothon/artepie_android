package com.leothon.cogito.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leothon.cogito.Bean.Ask;
import com.leothon.cogito.Mvp.View.Activity.AskDetailActivity.AskDetailActivity;
import com.leothon.cogito.Mvp.View.Activity.HomeActivity.HostActivity;
import com.leothon.cogito.Mvp.View.Fragment.AskPage.AskFragment;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final Ask ask = asks.get(position);
        AskViewHolder askViewHolder = (AskViewHolder) holder;

        ImageLoader.loadImageViewThumbnailwitherror(context,ask.getUsericonurl(),askViewHolder.userIcon,R.drawable.defalutimg);
        askViewHolder.userName.setText(ask.getUsername());
        askViewHolder.userDes.setText(ask.getUserdes());
        askViewHolder.contentAsk.setText(ask.getContent());


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


        askViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtils.getInstence().intent(context, AskDetailActivity.class);
            }
        });

//        askViewHolder.rvImg.setLayoutManager(new GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false));
//
//        askViewHolder.rvImg.setAdapter(new RecyclerView.Adapter() {
//            @Override
//            public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//
//                return new ImgViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.img_item,viewGroup,false));
//            }
//
//            @Override
//            public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
//                ImgViewHolder imgViewHolder = (ImgViewHolder)viewHolder;
//                ImageLoader.loadImageViewThumbnailwitherror(CommonUtils.getContext(),ask.getImgurl().get(i),imgViewHolder.imgin,R.drawable.defalutimg);
//            }
//
//            @Override
//            public int getItemCount() {
//                return ask.getImgurl().size();
//            }
//
//            class ImgViewHolder extends RecyclerView.ViewHolder{
//
//                @BindView(R.id.img_in)
//                ImageView imgin;
//                ImgViewHolder(View view){
//                    super(view);
//                    ButterKnife.bind(this,view);
//                }
//            }
//        });

//        switch (ask.getImgurl().size()){
//            case 1:
//                askViewHolder.imgLayout.setRowCount(1);
//                askViewHolder.imgLayout.setColumnCount(1);
//                break;
//            case 2:
//                askViewHolder.imgLayout.setRowCount(1);
//                askViewHolder.imgLayout.setColumnCount(2);
//                break;
//            case 3:
//                askViewHolder.imgLayout.setRowCount(1);
//                askViewHolder.imgLayout.setColumnCount(3);
//                break;
//            case 4:
//                askViewHolder.imgLayout.setRowCount(2);
//                askViewHolder.imgLayout.setColumnCount(2);
//                break;
//            case 5:
//                askViewHolder.imgLayout.setRowCount(2);
//                askViewHolder.imgLayout.setColumnCount(3);
//                break;
//            case 6:
//                askViewHolder.imgLayout.setRowCount(2);
//                askViewHolder.imgLayout.setColumnCount(3);
//                break;
//            case 7:
//                askViewHolder.imgLayout.setRowCount(3);
//                askViewHolder.imgLayout.setColumnCount(3);
//                break;
//            case 8:
//                askViewHolder.imgLayout.setRowCount(3);
//                askViewHolder.imgLayout.setColumnCount(3);
//                break;
//            case 9:
//                askViewHolder.imgLayout.setRowCount(3);
//                askViewHolder.imgLayout.setColumnCount(3);
//                break;
//            default:
//                break;
//        }
//        for (int i = 0;i < ask.getImgurl().size();i++){
//            ImageView img = addImageView();
//
//            GridLayout.Spec row = GridLayout.spec(1);
//            GridLayout.Spec column = GridLayout.spec(1);
//            switch (i){
//                case 0:
//                     row = GridLayout.spec(1);
//                     column = GridLayout.spec(1);
//                    break;
//                case 1:
//                     row = GridLayout.spec(1);
//                     column = GridLayout.spec(2);
//                    break;
//                case 2:
//                     row = GridLayout.spec(1);
//                     column = GridLayout.spec(3);
//                    break;
//                case 3:
//                     row = GridLayout.spec(2);
//                     column = GridLayout.spec(1);
//                    break;
//                case 4:
//                     row = GridLayout.spec(2);
//                     column = GridLayout.spec(2);
//                    break;
//                case 5:
//                     row = GridLayout.spec(2);
//                     column = GridLayout.spec(3);
//                    break;
//                case 6:
//                     row = GridLayout.spec(3);
//                     column = GridLayout.spec(1);
//                    break;
//                case 7:
//                     row = GridLayout.spec(3);
//                     column = GridLayout.spec(2);
//                    break;
//                case 8:
//                     row = GridLayout.spec(3);
//                     column = GridLayout.spec(3);
//                    break;
//                default:
//                    break;
//
//            }
//
//            img.setLayoutParams(new GridLayout.LayoutParams(row,column));
//            ImageLoader.loadImageViewThumbnailwitherror(context,ask.getImgurl().get(i),img,R.drawable.defalutimg);
//            askViewHolder.imgLayout.addView(img);

        //}

    }

    public void loadBigImg(int position,int urlposition){
        LayoutInflater inflater = LayoutInflater.from(context);
        View imgEntryView = inflater.inflate(R.layout.image, null); // 加载自定义的布局文件
        final AlertDialog dialog = new AlertDialog.Builder(context).create();
        ImageView img = (ImageView)imgEntryView.findViewById(R.id.image_big);
        ImageLoader.loadImageViewwithError(context,asks.get(position).getImgurl().get(urlposition),img,R.drawable.defalutimg);
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

    public ImageView addImageView(){
        ImageView imageView = new ImageView(context);
        return imageView;
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
        @BindView(R.id.img_layout)
        LinearLayout imgLayout;

        @BindView(R.id.rv_img)
        RecyclerView rvImg;

        public AskViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }
}
