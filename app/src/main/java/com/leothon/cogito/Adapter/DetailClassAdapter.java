package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.DetailClass;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;

import java.util.ArrayList;

/**
 * created by leothon on 9/18/2018.
 */
public class DetailClassAdapter extends BaseAdapter {

    public DetailClassAdapter(Context context, ArrayList<DetailClass> detailClasses){
        super(context, R.layout.select_class_item,detailClasses);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {
        DetailClass detailClass = (DetailClass)bean;
        holder.setText(R.id.select_class_index, "第" + CommonUtils.toCharaNumber(position + 1) + "讲");
        holder.setText(R.id.select_class_title,detailClass.getTitle());
        if (position < 3){
            holder.setViewVisiable(R.id.select_class_lock,2);
        }else {
            holder.setViewVisiable(R.id.select_class_lock,1);
        }

    }
}
