package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.Teacher;
import com.leothon.cogito.R;

import java.util.ArrayList;
import java.util.List;

/**
 * created by leothon on 2018.7.30
 */
public class TeacherAdapter extends BaseAdapter {

    public TeacherAdapter(Context context, ArrayList<Teacher> teaList){
        super(context, R.layout.tea_layout_rv,teaList);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean) {
        Teacher teacher = (Teacher) bean;
        holder.setImageResource(R.id.tea_icon,teacher.getResId());
        holder.setText(R.id.tea_name,teacher.getName());
    }
}
