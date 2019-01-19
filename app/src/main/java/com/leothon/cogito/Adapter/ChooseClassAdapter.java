package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.ChooseClass;
import com.leothon.cogito.R;

import java.util.ArrayList;

/**
 * created by leothon on 9/13/2018.
 */
public class ChooseClassAdapter extends BaseAdapter {

    private Context context;
    public ChooseClassAdapter(Context context, ArrayList<ChooseClass> chooseClasses){
        super(context, R.layout.chooseitem, chooseClasses);
        this.context = context;
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean,int position) {


        ChooseClass chooseClass = (ChooseClass)bean;

        if (position == chooseClass.getPosition()){
            holder.setText(R.id.choose_count,chooseClass.getCount(),context.getResources().getColor(R.color.white),R.drawable.textviewbackgroundshow);

        }else if (!chooseClass.getLocked()){
            holder.setText(R.id.choose_count,chooseClass.getCount(),context.getResources().getColor(R.color.fontColor),R.drawable.textviewbackground);
        }else {
            holder.setText(R.id.choose_count,"购买观看",context.getResources().getColor(R.color.fontColor),R.drawable.textviewbackground);
        }
    }
}
