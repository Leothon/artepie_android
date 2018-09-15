package com.leothon.cogito.Adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.leothon.cogito.Http.BaseResponse;

import java.util.List;

/**
 * created by leothon on 2018.7.30
 * 封装基础的Adapter
 */
public abstract class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private int layoutId;
    private List<? extends BaseResponse> data;
    private Context context;
    private OnItemClickListener onItemClickListener;//单击事件
    private OnItemLongClickListener onItemLongClickListener;//长按单击事件
    private boolean clickFlag = true;//单击事件和长单击事件的屏蔽标识

    /**
     * @param context  //上下文
     * @param layoutId //布局id
     * @param data     //数据源
     */
    public BaseAdapter(Context context, int layoutId, List<? extends BaseResponse> data) {
        this.layoutId = layoutId;
        this.data = data;
        this.context = context;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(layoutId, parent, false);
        final BaseViewHolder holder = new BaseViewHolder(v, context);
        //单击事件回调
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickFlag) {
                    onItemClickListener.onItemClickListener(v, holder.getLayoutPosition());
                }
                clickFlag = true;
            }
        });
        //单击长按事件回调
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onItemLongClickListener.onItemLongClickListener(v, holder.getLayoutPosition());
                clickFlag = false;
                return false;
            }
        });


        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        convert(holder, data.get(position),position);

    }


    public abstract <T> void convert(BaseViewHolder holder, T bean,int position);

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(View v, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClickListener(View v, int position);
    }
}
