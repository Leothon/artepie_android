package com.leothon.cogito.Adapter;


import android.content.Context;
import android.os.Bundle;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * created by leothon on 2018.8.11
 */
public class FavAdapter extends RecyclerView.Adapter{


    private Context context;
    private ArrayList<SelectClass> selectClasses;
    public FavAdapter(Context context, ArrayList<SelectClass> selectClasses){
        this.context = context;
        this.selectClasses = selectClasses;
    }
    public removeFavOnClickListener removeFavOnClickListener;

    public void setRemoveFavClick(removeFavOnClickListener removeFavOnClickListener) {
        this.removeFavOnClickListener = removeFavOnClickListener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final SelectClass selectClass = selectClasses.get(position);
        FavViewHolder favViewHolder = (FavViewHolder) holder;


        ImageLoader.loadImageViewThumbnailwitherror(context,selectClass.getSelectbackimg(),favViewHolder.favImg,R.drawable.defalutimg);
        favViewHolder.favTitle.setText(selectClass.getSelectlisttitle());
        favViewHolder.favDes.setText(selectClass.getSelectdesc());
        favViewHolder.favClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDialog(position);
            }
        });

        favViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("classId",selectClass.getSelectId());
                IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
            }
        });


    }

    private void loadDialog(final int position){
        final CommonDialog dialog = new CommonDialog(context);


        dialog.setMessage("您确定要取消收藏《" + selectClasses.get(position).getSelectlisttitle() + "》这门课程吗？")
                .setTitle("提示")
                .setSingle(false)
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        removeFavOnClickListener.removeFavClickListener(selectClasses.get(position).getSelectId());
                        selectClasses.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,selectClasses.size());
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }


    @Override
    public int getItemCount() {
        return selectClasses.size();
    }


    public  class FavViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fav_title_fav)
        TextView favTitle;
        @BindView(R.id.fav_img_fav)
        RoundedImageView favImg;
        @BindView(R.id.fav_description_fav)
        TextView favDes;

        @BindView(R.id.fav_clear)
        FrameLayout favClear;


        public FavViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }
    }

    public interface removeFavOnClickListener{
        void removeFavClickListener(String classId);
    }
}
