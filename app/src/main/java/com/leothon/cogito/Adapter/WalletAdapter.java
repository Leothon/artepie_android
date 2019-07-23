package com.leothon.cogito.Adapter;

import android.content.Context;
import android.graphics.Color;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leothon.cogito.Bean.Bill;
import com.leothon.cogito.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.8.12
 */
public class WalletAdapter extends BaseAdapter {


    private Context context;
    public WalletAdapter(Context context, ArrayList<Bill> bills){

        super(context, R.layout.bill_item,bills);
        this.context = context;
    }
    @Override
    public <T> void convert(BaseViewHolder holder, T bean, int position) {
        Bill bill = (Bill)bean;
        if (bill.getOutIn() == 1){
            holder.setImageUrls(R.id.bill_icon,bill.getBillIcon());
            holder.setText(R.id.bill_count,"+" + bill.getCount(),context.getResources().getColor(R.color.colorPrimary));
        }else {
            holder.setText(R.id.bill_count,"-" + bill.getEndCount());
        }

        holder.setText(R.id.bill_title,bill.getTitle());
        holder.setText(R.id.bill_time,bill.getTime());

    }
}
