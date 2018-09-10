package com.leothon.cogito.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Mvp.View.Activity.PayInfoActivity.PayInfoActivity;
import com.leothon.cogito.Mvp.View.Activity.PlayerActivity.PlayerActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Weight.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.8.6
 */
public class SelectClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private OnItemClickListener onItemClickListner;
    private SelectClass selectClass;
    private Context context;

    private int HEAD = 0;
    private int BODY = 100;
    private View headView;

    public SelectClassAdapter(SelectClass selectClass, Context context){
        this.selectClass = selectClass;
        this.context =context;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD){
            return new SelectHeadHolder(LayoutInflater.from(context).inflate(R.layout.select_class_background,parent,false));
        }else {
            return new SelectClassHolder(LayoutInflater.from(context).inflate(R.layout.select_class_item,parent,false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        if (viewType == HEAD){
            SelectHeadHolder selectHeadHolder= (SelectHeadHolder) holder;
            ImageLoader.loadImageViewwithError(context,selectClass.getSelectbackimg(),selectHeadHolder.selectBackImg,R.drawable.defalutimg);
            selectHeadHolder.selectTitle.setText(selectClass.getSelectlisttitle());
            //selectHeadHolder.selectTime.setText(selectClass.getSelecttime());
            //selectHeadHolder.selectStuCount.setText(selectClass.getSelectstucount());
            selectHeadHolder.selectAuthor.setText("讲师:" + selectClass.getSelectauthor());
        }else if (viewType == BODY){
            int realposition = position - 1;
            final SelectClassHolder selectClassHolder = (SelectClassHolder)holder;
            selectClassHolder.selectClassTitle.setText(selectClass.getVideoClasses().get(realposition).getVideoTitle());
            if (!selectClass.getSelectprice().equals("0") && realposition > 2){
                selectClassHolder.selectClassLock.setImageResource(R.drawable.baseline_lock_black_18);

            }else {
                selectClassHolder.selectClassLock.setImageResource(R.drawable.baseline_play_circle_outline_black_24);
            }
            int index = realposition + 1;
            selectClassHolder.selectClassIndex.setText("第" + CommonUtils.toCharaNumber(index) + "课");
            selectClassHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int realposition = position - 1;

                    if (realposition > 2){
                        loadDialog(selectClass);
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString("imgTitle",selectClass.getVideoClasses().get(realposition).getVideoTitle());
                        bundle.putString("imgUrls",selectClass.getVideoClasses().get(realposition).getVideoUrl());
                        IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
                    }

                }
            });
        }
    }
    private void loadDialog(final SelectClass selectData){
        final CommonDialog dialog = new CommonDialog(context);


        dialog.setMessage("是否购买整套课程？")
                .setTitle("提醒")
                .setSingle(false)
                .setPositive("购买")
                .setNegtive("放弃")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("imgurl",selectData.getSelectbackimg());
                        bundle.putString("title",selectData.getSelectlisttitle());
                        bundle.putString("des",selectData.getSelectdesc());
                        bundle.putString("price",selectData.getSelectprice());
                        bundle.putString("type","class");
                        bundle.putString("time","");
                        bundle.putString("address","");
                        bundle.putString("count","");
                        bundle.putString("name","");
                        bundle.putString("idcard","");
                        bundle.putString("phone","");
                        IntentUtils.getInstence().intent(context, PayInfoActivity.class,bundle);
                    }

                    @Override
                    public void onNegtiveClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && headView != null){
            return HEAD;
        }else {
            return BODY;
        }
    }

    @Override
    public int getItemCount() {
        return selectClass.getVideoClasses().size() + 1;
    }

    public void addHeadView(View view){
        this.headView = view;
    }


    @Override
    public void onClick(View view) {

    }


    class SelectHeadHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.select_back_img)
        ImageView selectBackImg;
        @BindView(R.id.select_title_list)
        TextView selectTitle;
        @BindView(R.id.select_class_time)
        TextView selectTime;
        @BindView(R.id.select_class_stucount)
        TextView selectStuCount;
        @BindView(R.id.select_class_author)
        TextView selectAuthor;
        public SelectHeadHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    class SelectClassHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.select_class_title)
        TextView selectClassTitle;
        @BindView(R.id.select_class_index)
        TextView selectClassIndex;
        @BindView(R.id.select_class_lock)
        ImageView selectClassLock;
        public SelectClassHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
}
