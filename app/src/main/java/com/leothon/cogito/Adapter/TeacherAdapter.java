package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.Teacher;
import com.leothon.cogito.Bean.User;
import com.leothon.cogito.R;

import java.util.ArrayList;
import java.util.List;

/**
 * created by leothon on 2018.7.30
 */
public class TeacherAdapter extends BaseAdapter {

    public TeacherAdapter(Context context, ArrayList<User> teachers){
        super(context, R.layout.tea_layout_rv,teachers);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean,int position) {
        User teacher = (User) bean;
        holder.setImageUrls(R.id.tea_icon,teacher.getUser_icon());
        holder.setText(R.id.tea_name,teacher.getUser_name());
    }
}
