package com.leothon.cogito.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leothon.cogito.Bean.MicClass;
import com.leothon.cogito.Mvp.View.Activity.MicClassActivity.Mic1ClassActivity;
import com.leothon.cogito.Mvp.View.Activity.MicClassActivity.backup;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 9/9/2018.
 */
public class Mic1ClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private ArrayList<MicClass> micClasses;

    private Context context;
    private int HEAD0 = 0;
    private int HEAD1 = 1;


    public Mic1ClassAdapter(ArrayList<MicClass> micClasses, Context context){
        this.micClasses = micClasses;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD0) {
            return new Content1Holder(LayoutInflater.from(context).inflate(R.layout.mic1_item,parent,false));
        }else {
            return new Bottom1EmptyHolder(LayoutInflater.from(context).inflate(R.layout.bottom_show_empty,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == HEAD0 ) {
            Content1Holder contentHolder = (Content1Holder)holder;
            ImageLoader.loadImageViewThumbnailwitherror(context,micClasses.get(position).getImgurl(),contentHolder.mic1Img,R.drawable.defalutimg);
            contentHolder.mic1Author.setText(micClasses.get(position).getAuthor());
            contentHolder.mic1Title.setText(micClasses.get(position).getTitle());
            contentHolder.mic1ClassTime.setText(micClasses.get(position).getTime());
            contentHolder.mic1ClassPrice.setText(micClasses.get(position).getPrice());
            contentHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("title",micClasses.get(position).getTitle());
                    bundle.putString("img",micClasses.get(position).getImgurl());
                    bundle.putString("author",micClasses.get(position).getAuthor());
                    bundle.putString("time",micClasses.get(position).getTime());
                    bundle.putString("price",micClasses.get(position).getPrice());
                    IntentUtils.getInstence().intent(context, Mic1ClassActivity.class,bundle);
                }
            });
        }else if(viewType == HEAD1){
            return;
        }


    }



    @Override
    public int getItemViewType(int position) {
        if (position < micClasses.size() ) {
            return HEAD0;
        }else {
            return HEAD1;
        }
    }


    @Override
    public int getItemCount() {
        return micClasses.size() + 1;
    }

    @Override
    public void onClick(View view) {

    }



    class Content1Holder extends RecyclerView.ViewHolder{

        @BindView(R.id.mic1_img)
        RoundedImageView mic1Img;
        @BindView(R.id.mic1_author)
        TextView mic1Author;
        @BindView(R.id.mic1_title)
        TextView mic1Title;
        @BindView(R.id.mic1_class_time)
        TextView mic1ClassTime;
        @BindView(R.id.mic1_class_price)
        TextView mic1ClassPrice;
        public Content1Holder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    class Bottom1EmptyHolder extends RecyclerView.ViewHolder{



        public Bottom1EmptyHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View v,int postion);
        void onItemLongClick(View v,int postion);
    }
    /**自定义条目点击监听*/
    private BagAdapter.OnItemClickListener mOnItemClickLitener;

    public void setmOnItemClickLitener(BagAdapter.OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
