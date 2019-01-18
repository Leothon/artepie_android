package com.leothon.cogito.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.DTO.ClassDetail;
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

    private OnItemClickListener onItemClickListener;
    private ClassDetail classDetail;
    private Context context;

    private int HEAD = 0;
    private int BODY = 100;
    private View headView;

    public favOnClickListener favOnClickListener;

    public void setFavOnClickListener(favOnClickListener favOnClickListener) {
        this.favOnClickListener = favOnClickListener;
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
        if (viewType == HEAD){
            final SelectHeadHolder selectHeadHolder= (SelectHeadHolder) holder;
            ImageLoader.loadImageViewwithError(context,classDetail.getTeaClasss().getSelectbackimg(),selectHeadHolder.selectBackImg,R.drawable.defalutimg);
            selectHeadHolder.selectTitle.setText(classDetail.getTeaClasss().getSelectlisttitle());
            selectHeadHolder.selectClassAuthor.setText("讲师 : " + classDetail.getTeaClasss().getSelectauthor());
            selectHeadHolder.selectClassTime.setText("课程总时长 ：" + classDetail.getTeaClasss().getSelecttime() + "分钟");
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
                    //TODO 收藏和取消
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
                    shareText("分享本节课程到",
                            "主题名称",
                            "我正在 @艺派-给生活以艺术 客户端学习《"
                                    + classDetail.getTeaClasss().getSelectlisttitle() +
                                    "》这个课程，点击加入体验"
                                    +"https://github.com/Leothon"+
                                    "\n (分享自艺派APP)");
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

                    if (realposition >= 1 && !classDetail.getTeaClasss().getSelectprice().equals("0.00")){
                        loadDialog(classDetail);
                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString("classd_id",classDetail.getClassDetailLists().get(realposition).getClassd_id());
                        bundle.putString("class_id",classDetail.getTeaClasss().getSelectId());
                        IntentUtils.getInstence().intent(context, PlayerActivity.class,bundle);
                    }

                }
            });
        }
    }

    /**
     * 分享文字内容
     *
     * @param dlgTitle
     *            分享对话框标题
     * @param subject
     *            主题
     * @param content
     *            分享内容（文字）
     */
    private void shareText(String dlgTitle, String subject, String content) {
        if (content == null || "".equals(content)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }

        intent.putExtra(Intent.EXTRA_TEXT, content);

        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题

            context.startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题

            context.startActivity(intent);
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

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }

    public interface favOnClickListener{
        void favClickListener(boolean isFav,String classId);
    }
}
