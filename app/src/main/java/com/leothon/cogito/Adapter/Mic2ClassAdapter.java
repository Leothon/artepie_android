package com.leothon.cogito.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leothon.cogito.Bean.ActivityAndVideo;
import com.leothon.cogito.Bean.MicClass;
import com.leothon.cogito.Mvp.View.Activity.MicClassActivity.Mic2ClassActivity;
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
public class Mic2ClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{



    private ArrayList<MicClass> micClasses;

    private Context context;
    private int HEAD0 = 0;
    private int HEAD1 = 1;


    public Mic2ClassAdapter(ArrayList<MicClass> micClasses, Context context){
        this.micClasses = micClasses;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD0) {
            return new Content2Holder(LayoutInflater.from(context).inflate(R.layout.mic2_item,parent,false));
        }else {
            return new Bottom2EmptyHolder(LayoutInflater.from(context).inflate(R.layout.bottom_show_empty,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == HEAD0 ) {
            Content2Holder content2Holder = (Content2Holder)holder;
            ImageLoader.loadImageViewThumbnailwitherror(context,micClasses.get(position).getImgurl(),content2Holder.mic2Img,R.drawable.defalutimg);
            content2Holder.mic2Author.setText(micClasses.get(position).getAuthor());
            content2Holder.mic2Title.setText(micClasses.get(position).getTitle());
            content2Holder.mic2ClassTime.setText(micClasses.get(position).getTime());
            content2Holder.mic2ClassPrice.setText(micClasses.get(position).getPrice());
            content2Holder.mic2ClassCount.setText(micClasses.get(position).getClassCount());
            content2Holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("title",micClasses.get(position).getTitle());
                bundle.putString("img",micClasses.get(position).getImgurl());
                bundle.putString("author",micClasses.get(position).getAuthor());
                bundle.putString("time",micClasses.get(position).getTime());
                bundle.putString("price",micClasses.get(position).getPrice());
                bundle.putString("count",micClasses.get(position).getClassCount());
                  IntentUtils.getInstence().intent(context, Mic2ClassActivity.class,bundle);
                }
            });
        }else if(viewType == HEAD1){
            return;
        }


    }



    @Override
    public int getItemViewType(int position) {
        if (position < micClasses.size()) {
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



    class Content2Holder extends RecyclerView.ViewHolder{

        @BindView(R.id.mic2_img)
        RoundedImageView mic2Img;
        @BindView(R.id.mic2_author)
        TextView mic2Author;
        @BindView(R.id.mic2_title)
        TextView mic2Title;
        @BindView(R.id.mic2_time)
        TextView mic2ClassTime;
        @BindView(R.id.mic2_class_price)
        TextView mic2ClassPrice;
        @BindView(R.id.mic2_class_count)
        TextView mic2ClassCount;

        public Content2Holder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    class Bottom2EmptyHolder extends RecyclerView.ViewHolder{



        public Bottom2EmptyHolder(View itemView){
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
