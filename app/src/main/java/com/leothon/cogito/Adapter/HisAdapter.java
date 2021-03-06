package com.leothon.cogito.Adapter;

import android.content.Context;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.leothon.cogito.Bean.ClassDetailList;
import com.leothon.cogito.Mvp.View.Activity.PlayerActivity.PlayerActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * created by leothon on 2018.8.10
 */
public class HisAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<ClassDetailList> classDetailLists;


    public removeViewOnClickListener removeViewOnClickListener;

    public void setRemoveViewClick(removeViewOnClickListener removeViewOnClickListener) {
        this.removeViewOnClickListener = removeViewOnClickListener;
    }
    public HisAdapter(Context context, ArrayList<ClassDetailList> classDetailLists){
        this.context = context;
        this.classDetailLists = classDetailLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new HisViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ClassDetailList classDetailList = classDetailLists.get(position);
        HisViewHolder hisViewHolder = (HisViewHolder) holder;


        ImageLoader.loadImageViewThumbnailwitherror(context,"",hisViewHolder.favImg,R.drawable.defalutimg);
        hisViewHolder.favTitle.setText(classDetailList.getClassd_title());
        hisViewHolder.favDes.setText(classDetailList.getClassd_des());
        hisViewHolder.favClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeViewOnClickListener.removeViewClickListener(classDetailList.getClassd_id());
                classDetailLists.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,classDetailLists.size());
            }
        });

        hisViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("classid",classDetailList.getClass_classd_id());
                bundle.putString("classdid",classDetailList.getClassd_id());
                IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
            }
        });


    }



    @Override
    public int getItemCount() {
        return classDetailLists.size();
    }


    public  class HisViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fav_title_fav)
        TextView favTitle;
        @BindView(R.id.fav_img_fav)
        RoundedImageView favImg;
        @BindView(R.id.fav_description_fav)
        TextView favDes;

        @BindView(R.id.fav_clear)
        FrameLayout favClear;


        public HisViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

    public interface removeViewOnClickListener{
        void removeViewClickListener(String classdId);
    }
}
