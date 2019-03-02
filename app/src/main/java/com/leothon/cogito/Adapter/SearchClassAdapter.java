package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.R;

import java.util.ArrayList;

public class SearchClassAdapter extends BaseAdapter {
    public SearchClassAdapter(Context context, ArrayList<SelectClass> classes){
        super(context, R.layout.class_item,classes);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {

        SelectClass selectClass = (SelectClass)bean;
        holder.setText(R.id.class_title,selectClass.getSelectlisttitle());
        holder.setText(R.id.class_description,selectClass.getSelectdesc());
        if (selectClass.isIsbuy()){
            holder.setText(R.id.class_price,"已购买");
        }else if (selectClass.getSelectprice().equals("0.00")){
            holder.setText(R.id.class_price,"免费");
        }else {
            holder.setText(R.id.class_price,"￥" + selectClass.getSelectprice());
        }

        holder.setText(R.id.class_count,selectClass.getSelectstucount());
        holder.setImageUrls(R.id.class_img,selectClass.getSelectbackimg());

    }
}
