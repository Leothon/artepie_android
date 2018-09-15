package com.leothon.cogito.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.leothon.cogito.Bean.Activityed;
import com.leothon.cogito.R;

import java.util.ArrayList;
/**
 * created by leothon on 2018.8.5
 */
public class ListCommonAdapter extends BaseAdapter{

    public ListCommonAdapter(Context context, ArrayList<Activityed> activityeds){
        super(context, R.layout.class_item,activityeds);
    }

    @Override
    public  <T> void convert(BaseViewHolder holder, T bean,int position) {
        Activityed activityed = (Activityed) bean;
        holder.setImageUrls(R.id.class_img,activityed.getClassurl());
        holder.setText(R.id.class_title,activityed.getClasstitle());
        holder.setText(R.id.class_description,activityed.getClassdescription());
        holder.setViewVisiable(R.id.price,2);
    }


}
