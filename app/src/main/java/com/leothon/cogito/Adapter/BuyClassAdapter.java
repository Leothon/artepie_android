package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.R;

import java.util.ArrayList;

public class BuyClassAdapter extends BaseAdapter {
    public BuyClassAdapter(Context context, ArrayList<SelectClass> classes){
        super(context, R.layout.fav_item,classes);
    }


    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {
        SelectClass selectClass = (SelectClass)bean;
        holder.setViewVisiable(R.id.fav_clear,2);
        holder.setText(R.id.fav_title_fav,selectClass.getSelectlisttitle());
        holder.setText(R.id.fav_description_fav,selectClass.getSelectdesc());
        holder.setImageUrls(R.id.fav_img_fav,selectClass.getSelectbackimg());
    }
}
