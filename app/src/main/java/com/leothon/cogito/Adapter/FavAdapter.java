package com.leothon.cogito.Adapter;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Bean.Ask;
import com.leothon.cogito.Bean.Fav;
import com.leothon.cogito.Constants;
import com.leothon.cogito.Http.BaseResponse;
import com.leothon.cogito.Mvp.View.Activity.FavActivity.FavActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
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
    private ArrayList<Fav> favs;
    public FavAdapter(Context context, ArrayList<Fav> favs){
        this.context = context;
        this.favs = favs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FavViewHolder(LayoutInflater.from(context).inflate(R.layout.fav_item,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        Fav fav = favs.get(position);
        FavViewHolder favViewHolder = (FavViewHolder) holder;


        ImageLoader.loadImageViewThumbnailwitherror(context,fav.getFavurl(),favViewHolder.favImg,R.drawable.defalutimg);
        favViewHolder.favTitle.setText(fav.getTitle());
        favViewHolder.favDes.setText(fav.getDescription());
        favViewHolder.favClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 取消收藏

                loadDialog(position);
            }
        });

        favViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转相关页面
                Bundle bundle = new Bundle();
                bundle.putString("url",favs.get(position).getFavurl());
                bundle.putString("title",favs.get(position).getTitle());
                bundle.putString("author",favs.get(position).getAuthor());
                IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
            }
        });


    }

    private void loadDialog(final int position){
        final CommonDialog dialog = new CommonDialog(context);


        dialog.setMessage("您确定要取消收藏《" + favs.get(position).getTitle() + "》这门课程吗？")
                .setTitle("提示")
                .setSingle(false)
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        //TODO 删除该收藏
                        favs.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,favs.size());
                    }

                    @Override
                    public void onNegtiveClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }


    @Override
    public int getItemCount() {
        return favs.size();
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


}
