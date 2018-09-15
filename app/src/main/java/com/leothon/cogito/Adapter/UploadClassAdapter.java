package com.leothon.cogito.Adapter;

import android.content.Context;
import android.view.View;

import com.leothon.cogito.Bean.ChooseClass;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;

import java.util.ArrayList;

/**
 * created by leothon on 9/15/2018.
 */
public class UploadClassAdapter extends BaseAdapter{
    public UploadClassAdapter(Context context, ArrayList<ChooseClass> titles){
        super(context, R.layout.upload_class_item,titles);
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {
        ChooseClass titles = (ChooseClass)bean;
        holder.setText(R.id.count_upload, "第" + CommonUtils.toCharaNumber(position+1) + "课");
        holder.setText(R.id.title_sub_class,titles.getName());
        holder.getView(R.id.more_class_upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 显示更多并执行删除操作
            }
        });

    }
}
