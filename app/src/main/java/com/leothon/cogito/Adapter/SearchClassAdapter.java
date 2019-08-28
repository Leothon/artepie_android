package com.leothon.cogito.Adapter;

import android.content.Context;
import android.view.View;

import com.leothon.cogito.Bean.Article;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.R;

import java.util.ArrayList;

public class SearchClassAdapter extends BaseAdapter {
    private int size;
    private Context context;
    public SearchClassAdapter(Context context, ArrayList<SelectClass> classes){
        super(context, R.layout.class_item,classes);
        this.size = classes.size();
        this.context = context;
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {


        if (size == 1){
            holder.convertView.setBackground(context.getResources().getDrawable(R.drawable.gradient_all));
            holder.setViewVisiable(R.id.class_bottom_line,0);
        }else {
            if (position == 0){
                holder.convertView.setBackground(context.getResources().getDrawable(R.drawable.class_item_bg_top));
                holder.setViewVisiable(R.id.class_bottom_line,1);


            }else if (position == size - 1){
                holder.convertView.setBackground(context.getResources().getDrawable(R.drawable.class_item_bg_bottom));
                holder.setViewVisiable(R.id.class_bottom_line,0);

            }else {
                holder.convertView.setBackgroundColor(context.getResources().getColor(R.color.white));

                holder.setViewVisiable(R.id.class_bottom_line,1);
            }
        }
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
