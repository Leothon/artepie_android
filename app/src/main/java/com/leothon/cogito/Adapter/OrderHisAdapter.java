package com.leothon.cogito.Adapter;

import android.content.Context;

import com.leothon.cogito.Bean.OrderHis;
import com.leothon.cogito.R;

import java.util.ArrayList;

public class OrderHisAdapter extends BaseAdapter{

    private Context context;
    public OrderHisAdapter(Context context, ArrayList<OrderHis> orderHis){
        super(context, R.layout.order_item,orderHis);
        this.context = context;
    }

    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {
        OrderHis orderHis = (OrderHis)bean;
        holder.setText(R.id.order_his_author,orderHis.getAuthorName());

        holder.setText(R.id.order_his_title,orderHis.getOrderTitle());
        holder.setText(R.id.order_his_des,orderHis.getOrderDes());
        holder.setText(R.id.order_his_time,orderHis.getOrderTime());
        holder.setText(R.id.order_his_count,orderHis.getOrderCount());

        holder.setImageUrls(R.id.order_his_img,orderHis.getOrderImg());

        if (orderHis.getOrderStatus().equals("已支付")){
            holder.setText(R.id.order_his_status,orderHis.getOrderStatus());
        }else {
            holder.setText(R.id.order_his_status,orderHis.getOrderStatus(),context.getResources().getColor(R.color.colorAccent));
        }


    }
}
