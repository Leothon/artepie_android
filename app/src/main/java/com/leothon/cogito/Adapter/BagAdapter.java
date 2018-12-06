package com.leothon.cogito.Adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Bean.BagBuy;

import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;

import com.leothon.cogito.Utils.IntentUtils;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * created by leothon on 2018.8.10
 */
public class BagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private ArrayList<BagBuy> bagBuyclass;
    private ArrayList<BagBuy> recommendclass;

    private Context context;
    private int HEAD0 = 0;
    private int HEAD1 = 1;
    private int HEAD2 = 2;
    private int HEAD3 = 3;
    private int HEAD4 = 4;


    public BagAdapter(ArrayList<BagBuy> bagBuyclass,ArrayList<BagBuy> recommendclass, Context context){
        this.bagBuyclass = bagBuyclass;
        this.recommendclass = recommendclass;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        if (viewType == HEAD0) {
            return new BuyClassTitleHolder(LayoutInflater.from(context).inflate(R.layout.dividerview,parent,false));
        }else if(viewType == HEAD1){
            return new BuyClassHolder(LayoutInflater.from(context).inflate(R.layout.mic2_item,parent,false));
        }else if (viewType == HEAD2){
            return new RecommentClassTitleHolder(LayoutInflater.from(context).inflate(R.layout.dividerview,parent,false));
        }else if (viewType == HEAD3){
            return new RecommentClassHolder(LayoutInflater.from(context).inflate(R.layout.videoforyou_home,parent,false));
        }else {
            return new BottomShowHolder(LayoutInflater.from(context).inflate(R.layout.bottom_show_empty,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == HEAD0 ) {
            BuyClassTitleHolder buyClassTitleHolder = (BuyClassTitleHolder)holder;
            buyClassTitleHolder.dividerBuy.setText("已订阅课程");
            buyClassTitleHolder.topDiv.setVisibility(View.VISIBLE);
        }else if(viewType == HEAD1){
            int position1 = position - 1;
            BuyClassHolder buyClassHolder = (BuyClassHolder)holder;
            ImageLoader.loadImageViewThumbnailwitherror(context,bagBuyclass.get(position1).getImgurl(),buyClassHolder.classbagImg,R.drawable.defalutimg);
            buyClassHolder.classbagTitle.setText(bagBuyclass.get(position1).getTitle());
            buyClassHolder.classbagAuthor.setText(bagBuyclass.get(position1).getDescription());
            buyClassHolder.classbagPrice.setVisibility(View.GONE);
            buyClassHolder.classbagTime.setText(bagBuyclass.get(position1).getClassCount());
            buyClassHolder.classbagCount.setText(bagBuyclass.get(position1).getTime());
            buyClassHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //TODO 跳转到课程分类
                    int position1 = position - 1;
                    Bundle bundle = new Bundle();
                    bundle.putString("url",bagBuyclass.get(position1).getImgurl());
                    bundle.putString("title",bagBuyclass.get(position1).getTitle());
                    bundle.putString("author",bagBuyclass.get(position1).getAuthor());
                    bundle.putString("price","0");
                    IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                }
            });
        }else if(viewType == HEAD2){
            RecommentClassTitleHolder recommentClassTitleHolder = (RecommentClassTitleHolder)holder;
            recommentClassTitleHolder.dividerRe.setText("为您推荐课程");
            recommentClassTitleHolder.topDiv.setVisibility(View.VISIBLE);
        }else if (viewType == HEAD3){
            int position2 = position - (bagBuyclass.size() + 2);
            RecommentClassHolder recommentClassHolder = (RecommentClassHolder)holder;
            ImageLoader.loadImageViewThumbnailwitherror(context,recommendclass.get(position2).getImgurl(),recommentClassHolder.recommentImg,R.drawable.defalutimg);
            //recommentClassHolder.playMark.setVisibility(View.GONE);
            recommentClassHolder.recommentTv.setText(recommendclass.get(position2).getTitle());
            recommentClassHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position2 = position - (bagBuyclass.size() + 2);
                    Bundle bundle = new Bundle();
                    bundle.putString("url",recommendclass.get(position2).getImgurl());
                    bundle.putString("title",recommendclass.get(position2).getTitle());
                    bundle.putString("author",recommendclass.get(position2).getAuthor());
                    bundle.putString("price",recommendclass.get(position2).getPrice());
                    IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                }
            });
        }else {
            return;
        }


    }

//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
//        if (manager instanceof GridLayoutManager){
//            final GridLayoutManager gridLayoutManager = ((GridLayoutManager)manager);
//            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//                @Override
//                public int getSpanSize(int position) {
//                    int type = getItemViewType(position);
//                    if (type == HEAD3){
//                        return 1;
//                    }else {
//                        return 2;
//                    }
//
//                }
//            });
//        }
//    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 ) {
            return HEAD0;
        }else if (position <= bagBuyclass.size() && position != 0){
            return HEAD1;
        }else if (position == bagBuyclass.size() + 1){
            return HEAD2;
        }else if (position <= (recommendclass.size() + bagBuyclass.size() + 1) && position != 0){
            return HEAD3;
        }else {
            return HEAD4;
        }
    }


    @Override
    public int getItemCount() {
        return bagBuyclass.size() + recommendclass.size() + 3;
    }

    @Override
    public void onClick(View view) {

    }



    class BuyClassTitleHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.divider_title)
        TextView dividerBuy;
        @BindView(R.id.topdiv)
        View topDiv;
        @BindView(R.id.bottomdiv)
        View bottomDiv;
        public BuyClassTitleHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    class BuyClassHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.mic2_img)
        RoundedImageView classbagImg;
        @BindView(R.id.mic2_author)
        TextView classbagAuthor;
        @BindView(R.id.mic2_title)
        TextView classbagTitle;
        @BindView(R.id.mic2_class_count)
        TextView classbagCount;
        @BindView(R.id.mic2_time)
        TextView classbagTime;
        @BindView(R.id.mic2_class_price)
        TextView classbagPrice;
        @BindView(R.id.mic2_divider)
        TextView classbagDivider;


        public BuyClassHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class RecommentClassTitleHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.divider_title)
        TextView dividerRe;
        @BindView(R.id.topdiv)
        View topDiv;
        @BindView(R.id.bottomdiv)
        View bottomDiv;
        public RecommentClassTitleHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class RecommentClassHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.foryou_iv)
        RoundedImageView recommentImg;
        @BindView(R.id.foryou_tv)
        TextView recommentTv;
//        @BindView(R.id.play_mark)
//        ImageView playMark;

        public RecommentClassHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class BottomShowHolder extends RecyclerView.ViewHolder{
        public BottomShowHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View v,int postion);
        void onItemLongClick(View v,int postion);
    }
    /**自定义条目点击监听*/
    private OnItemClickListener mOnItemClickLitener;

    public void setmOnItemClickLitener(OnItemClickListener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
