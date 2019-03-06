package com.leothon.cogito.Adapter;

import android.content.Context;
import android.view.View;

import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;

import java.util.ArrayList;

public class EditClassAdapter extends BaseAdapter {


    public EditClassAdapter(Context context, ArrayList<SelectClass> selectClasses){
        super(context, R.layout.edit_class_item,selectClasses);

    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {


        final SelectClass selectClass = (SelectClass)bean;
        holder.setText(R.id.class_title_edit,selectClass.getSelectlisttitle());
        holder.setText(R.id.class_description_edit,selectClass.getSelectdesc());
        holder.setText(R.id.class_count_edit,selectClass.getSelectstucount() + "人次已学习");

        holder.setImageUrls(R.id.class_img_edit,selectClass.getSelectbackimg());

    }

}
