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
import android.widget.Toast;

import com.leothon.cogito.DTO.ClassDetail;
import com.leothon.cogito.Mvp.View.Activity.PayInfoActivity.PayInfoActivity;
import com.leothon.cogito.Mvp.View.Activity.PlayerActivity.PlayerActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.View.MyToast;
import com.leothon.cogito.Weight.CommonDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.8.6
 */
public class SelectClassAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    public OnDeleteClickListener onDeleteClickListener;
    private ClassDetail classDetail;
    private Context context;

    private int HEAD = 0;
    private int BODY = 100;
    private View headView;



    public favOnClickListener favOnClickListener;

    public interface QQUIListener{
        void setQQUIListener(ClassDetail classDetail);
    }

    public QQUIListener qquiListener;

    public void setQquiListener(QQUIListener listener){
        this.qquiListener = listener;
    }

    public void setFavOnClickListener(favOnClickListener favOnClickListener) {
        this.favOnClickListener = favOnClickListener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }
    public SelectClassAdapter(ClassDetail classDetail, Context context){
        this.classDetail = classDetail;
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
        final SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(context,"saveToken");
        if (viewType == HEAD){
            final SelectHeadHolder selectHeadHolder= (SelectHeadHolder) holder;
            ImageLoader.loadImageViewThumbnailwitherror(context,classDetail.getTeaClasss().getSelectbackimg(),selectHeadHolder.selectBackImg,R.drawable.defalutimg);
            selectHeadHolder.selectTitle.setText(classDetail.getTeaClasss().getSelectlisttitle());
            selectHeadHolder.selectClassAuthor.setText("讲师 : " + classDetail.getTeaClasss().getSelectauthor());
            selectHeadHolder.selectClassTime.setText("课程总时长 ：" + CommonUtils.msTomin(Long.parseLong(classDetail.getTeaClasss().getSelecttime())) + "分钟");
            selectHeadHolder.selectClassDesc.setText(classDetail.getTeaClasss().getSelectdesc());
            selectHeadHolder.selectClassAuthorDesc.setText(classDetail.getTeaClasss().getSelectauthordes());
            selectHeadHolder.selectClassLevel.setText(classDetail.getTeaClasss().getSelectscore());
            if (classDetail.getTeaClasss().isIsfav()){
                selectHeadHolder.selectClassFav.setImageResource(R.drawable.faved);
            }else {
                selectHeadHolder.selectClassFav.setImageResource(R.drawable.fav);
            }
            if (classDetail.getTeaClasss().isIsbuy()){
                selectHeadHolder.selectClassPrice.setText("已购买");
            }else if (classDetail.getTeaClasss().getSelectprice().equals("0.00")){
                selectHeadHolder.selectClassPrice.setText("免费");
            }else {
                selectHeadHolder.selectClassPrice.setText("￥ " + classDetail.getTeaClasss().getSelectprice());
            }
            selectHeadHolder.selectClassFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favOnClickListener.favClickListener(classDetail.getTeaClasss().isIsfav(),classDetail.getTeaClasss().getSelectId());
                    if (!classDetail.getTeaClasss().isIsfav()){
                        selectHeadHolder.selectClassFav.setImageResource(R.drawable.faved);
                        classDetail.getTeaClasss().setIsfav(true);
                    }else {
                        selectHeadHolder.selectClassFav.setImageResource(R.drawable.fav);
                        classDetail.getTeaClasss().setIsfav(false);
                    }
                }
            });
            selectHeadHolder.selectClassShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    qquiListener.setQQUIListener(classDetail);
                }
            });
        }else if (viewType == BODY){
            int realposition = position - 1;
            final SelectClassHolder selectClassHolder = (SelectClassHolder)holder;
            selectClassHolder.selectClassTitle.setText(classDetail.getClassDetailLists().get(realposition).getClassd_title());

            if (!classDetail.getTeaClasss().getSelectprice().equals("0.00") && realposition >= 1){
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

                    if (classDetail.getTeaClasss().getSelectauthorid().equals(tokenUtils.ValidToken(sharedPreferencesUtils.getParams("token","").toString()).getUid())){
                        Bundle bundle = new Bundle();
                        bundle.putString("classdid",classDetail.getClassDetailLists().get(realposition).getClassd_id());
                        bundle.putString("classid",classDetail.getTeaClasss().getSelectId());
                        bundle.putBoolean("myself",true);
                        IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
                    }else if (classDetail.getClassDetailLists().get(realposition).getClassdStatus() == 1){
                        if (realposition >= 1 && !classDetail.getTeaClasss().getSelectprice().equals("0.00")){

                            loadDialog(classDetail);
                        }else {
                            Bundle bundle = new Bundle();
                            bundle.putString("classdid",classDetail.getClassDetailLists().get(realposition).getClassd_id());
                            bundle.putString("classid",classDetail.getTeaClasss().getSelectId());
                            bundle.putBoolean("myself",false);
                            IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
                        }
                    }else {
                        MyToast.getInstance(context).show("该节课程正在审核中",Toast.LENGTH_LONG);
                    }
                }
            });

            selectClassHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int realposition = position - 1;
                    if (classDetail.getTeaClasss().getSelectauthorid().equals(tokenUtils.ValidToken(sharedPreferencesUtils.getParams("token","").toString()).getUid())){

                        onDeleteClickListener.deleteClickListener(classDetail.getClassDetailLists().get(realposition).getClassd_id());
                    }else {
                        MyToast.getInstance(context).show("轻点进入视频课程",Toast.LENGTH_SHORT);
                    }
                    return false;
                }
            });
        }
    }




    private void loadDialog(final ClassDetail classDetail){
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
                        bundle.putString("classId",classDetail.getTeaClasss().getSelectId());
                        IntentUtils.getInstence().intent(context, PayInfoActivity.class,bundle);
                    }

                    @Override
                    public void onNegativeClick() {
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
        return classDetail.getClassDetailLists().size() + 1;
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


        @BindView(R.id.home_class_detail_author)
        TextView selectClassAuthor;
        @BindView(R.id.home_class_detail_level)
        TextView selectClassLevel;
        @BindView(R.id.home_class_detail_price)
        TextView selectClassPrice;
        @BindView(R.id.home_class_detail_time)
        TextView selectClassTime;
        @BindView(R.id.home_class_detail_author_desc)
        TextView selectClassAuthorDesc;
        @BindView(R.id.home_class_detail_desc)
        TextView selectClassDesc;

        @BindView(R.id.select_class_fav)
        ImageView selectClassFav;
        @BindView(R.id.select_class_share)
        ImageView selectClassShare;
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

    public interface OnDeleteClickListener{
        void deleteClickListener(String classdId);
    }

    public interface favOnClickListener{
        void favClickListener(boolean isFav,String classId);
    }
}
