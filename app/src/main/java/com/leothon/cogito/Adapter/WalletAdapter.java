package com.leothon.cogito.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leothon.cogito.Constants;
import com.leothon.cogito.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.8.12
 */
public class WalletAdapter extends RecyclerView.Adapter {

    public ClassTypeOnClickListener classTypeOnClickListener;

    public void setClassTypeOnClickListener(ClassTypeOnClickListener classTypeOnClickListener) {
        this.classTypeOnClickListener = classTypeOnClickListener;
    }

    private Context context;
    private ArrayList<String> wallets;
    private int lastPressIndex = -1;


    public WalletAdapter(ArrayList<String> wallets,Context context){
        this.wallets = wallets;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OneViewHolder(LayoutInflater.from(context).inflate(R.layout.recharge_item, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {


        OneViewHolder oneViewHolder = (OneViewHolder)holder;
        oneViewHolder.tv.setText(wallets.get(position));
        oneViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lastPressIndex == position){
                    lastPressIndex = -1;
                }else {
                    lastPressIndex = position;
                }
                    notifyDataSetChanged();
                }
            });

            if (position == lastPressIndex){
                oneViewHolder.tv.setSelected(true);
                oneViewHolder.tv.setTextColor(Color.WHITE);
                classTypeOnClickListener.classTypeClickListener(oneViewHolder.tv.getText().toString());
            }else {
                oneViewHolder.tv.setSelected(false);
                oneViewHolder.tv.setTextColor(Color.BLACK);
            }


    }

    @Override
    public int getItemCount() {
        return wallets.size();
    }


    public class OneViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv)
        TextView tv;

        public OneViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
        }

    }

    public interface ClassTypeOnClickListener{
        void classTypeClickListener(String type);
    }

}
