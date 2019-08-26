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

import com.leothon.cogito.DTO.TypeClass;
import com.leothon.cogito.Mvp.View.Activity.PayInfoActivity.PayInfoActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;

import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestSelfAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{

    private TypeClass typeClass;
    private Context context;

    private int HEAD = 0;
    private int DESCRIPTION = 1;
    private int BODY = 100;
    private View headView;
    private View descriptionView;
    private boolean isLogin;


    /**
     *
     */
    Map<String , String> map = new HashMap<String , String>(){{
        put("民族", "所谓中国民族音乐就是祖祖辈辈生活、繁衍在中国这片土地上的各民族，从古到今在悠久历史文化传统上创造的具有民族特色，能体现民族文化和民族精神的音乐。而广义上，民族音乐是泛指中国音乐家所创作的的音乐和具有中国五声色彩的音乐。");
        put("美声", "美声不仅是一种发声方法，还代表着一种演唱风格，一种声乐学派，因之通常又可译作美声唱法、美声学派。 美声歌唱不同于其他歌唱方法的特点之一，是它采用了比其他唱法的喉头位置较低的发声方法，因而产生了一种明亮、丰满、松弛、圆润，而又具有一种金属色彩的、富于共鸣的音质；其次是它注重句法连贯，声音灵活，刚柔兼备，以柔为主的演唱风格。");
        put("古典", "古典是指那些从西方中世纪开始至今的、在欧洲主流文化背景下创作的西方古典音乐，主要因其复杂多样的创作技术和所能承载的厚重内涵而有别于通俗音乐和民间音乐。狭义指古典主义时期，1750年（J·S·巴赫去世）至1827年（贝多芬去世)，这一时期为古典主义音乐时期，它包含了两大时间段：“前古典时期”和“维也纳古典时期”。“最为著名的维也纳乐派也是在“维也纳古典时期”兴起，其代表作曲家有海顿、莫扎特和贝多芬，被后世称为“维也纳三杰”。");
        put("戏曲", "中国戏曲主要是由民间歌舞、说唱和滑稽戏三种不同艺术形式综合而成。它起源于原始歌舞，是一种历史悠久的综合舞台艺术样式。经过汉、唐到宋、金才形成比较完整的戏曲艺术，它由文学、音乐、舞蹈、美术、武术、杂技以及表演艺术综合而成，约有三百六十多个种类。它的特点是将众多艺术形式以一种标准聚合在一起，在共同具有的性质中体现其各自的个性。 [1]  中国的戏曲与希腊悲剧和喜剧、印度梵剧并称为世界三大古老的戏剧文化，经过长期的发展演变，逐步形成了以“京剧、越剧、黄梅戏、评剧、豫剧”五大戏曲剧种为核心的中华戏曲百花苑");
        put("原生态", "原生态指没有被特殊雕琢，存在于民间原始的、散发着乡土气息的表演形态，它包含着原生态唱法、原生态舞蹈、原生态歌手、原生态大写意山水画等。");
        put("民谣", "民间流行的、赋予民族色彩的歌曲，称为民谣或民歌。民谣的历史悠远，故其作者多不知名。民谣的内容丰富，有宗教的、爱情的、战争的、工作的，也有饮酒、舞蹈作乐、祭典等等。民谣表现一个民族的感情与习尚，因此各有其独特的音阶与情调风格。如法国民谣的蓬勃、意大利民谣的热情、英国民谣的淳朴、日本民谣的悲愤、西班牙民谣的狂放不羁、中国民谣的缠绵悱恻，都表现了强烈的民族气质与色彩。");
        put("通俗", "通俗唱法（原也称流行唱法）始于中国二十世纪30年代得到广泛的流传。其特点是声音自然，近似说话，中声区使用真声，高声区一般使用假声。很少使用共鸣，故音量较小。演唱时必须借助电声扩音器，演出形式以独唱为主，常配以舞蹈动作、追求声音自然甜美，感情细腻真实。");
        put("其他", "其他相关的艺术形式");
    }};

    public TestSelfAdapter(TypeClass typeClass, Context context,boolean isLogin){
        this.typeClass = typeClass;
        this.context =context;
        this.isLogin = isLogin;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD){
            return new TestHeadHolder(LayoutInflater.from(context).inflate(R.layout.test_background,parent,false));
        }else if (viewType == DESCRIPTION){
            return new TestDesHolder(LayoutInflater.from(context).inflate(R.layout.test_background_description,parent,false));
        }else {
            return new ClassItemHolder(LayoutInflater.from(context).inflate(R.layout.class_item,parent,false));

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        int viewType = getItemViewType(position);
        final SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(context,"saveToken");
        if (viewType == HEAD){
            TestHeadHolder testHeadHolder= (TestHeadHolder) holder;
            testHeadHolder.testTitle.setText(typeClass.getType());
            testHeadHolder.testBackImg.setImageResource(R.drawable.activityback);
        }else if (viewType == DESCRIPTION){
            TestDesHolder testDesHolder = (TestDesHolder)holder;
            testDesHolder.testClassCount.setText("总课程数 ： " + typeClass.getTypeClassCount());
            testDesHolder.testClassDes.setText(map.get(typeClass.getType()));
        }else if (viewType == BODY){
            final int realposition = position - 2;
            final ClassItemHolder classItemHolder = (ClassItemHolder)holder;
            classItemHolder.classTitle.setText(typeClass.getTypeClass().get(realposition).getSelectlisttitle());
            classItemHolder.classDescription.setText(typeClass.getTypeClass().get(realposition).getSelectdesc());
            classItemHolder.classCount.setText(typeClass.getTypeClass().get(realposition).getSelectstucount() + "人次已学习");
            ImageLoader.loadImageViewThumbnailwitherror(context,typeClass.getTypeClass().get(realposition).getSelectbackimg(),classItemHolder.classImg,R.drawable.defalutimg);
            if (typeClass.getTypeClass().get(realposition).getSelectprice().equals("0.00")){
                classItemHolder.classPrice.setText("免费");
            }else if (typeClass.getTypeClass().get(realposition).isIsbuy()){
                classItemHolder.classPrice.setText("已购买");
            }else {
                classItemHolder.classPrice.setText("￥" + typeClass.getTypeClass().get(realposition).getSelectprice());
            }
            if (typeClass.getTypeClass().get(realposition).isAuthorize()){
                classItemHolder.authorize.setVisibility(View.VISIBLE);
            }else {
                classItemHolder.authorize.setVisibility(View.GONE);
            }
            if (typeClass.getTypeClass().get(realposition).isSerialize()){
                classItemHolder.serialize.setText(" 连载中... ");
            }else {
                classItemHolder.serialize.setText(" 已完结 ");
            }

            classItemHolder.classPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!typeClass.getTypeClass().get(realposition).isIsbuy() && !typeClass.getTypeClass().get(realposition).getSelectprice().equals("0.00") && !typeClass.getTypeClass().get(realposition).getSelectauthorid().equals(tokenUtils.ValidToken(sharedPreferencesUtils.getParams("token","").toString()).getUid())){

                        if (isLogin){
                            loadPayDialog(typeClass.getTypeClass().get(realposition).getSelectId());
                        }else {
                            CommonUtils.loadinglogin(context);
                        }

                    }else {
                        Bundle bundle = new Bundle();
                        bundle.putString("classId",typeClass.getTypeClass().get(realposition).getSelectId());
                        IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                    }
                }
            });
            classItemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int realposition = position - 2;

                    if (classItemHolder.classPrice.getText().toString().equals("已购买") || classItemHolder.classPrice.getText().toString().equals("免费") || typeClass.getTypeClass().get(realposition).getSelectauthorid().equals(tokenUtils.ValidToken(sharedPreferencesUtils.getParams("token","").toString()).getUid())){
                        Bundle bundle = new Bundle();
                        bundle.putString("classId",typeClass.getTypeClass().get(realposition).getSelectId());
                        IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                    }else {
                        loadDialog(typeClass.getTypeClass().get(realposition).getSelectId());
                    }

                }
            });

        }
    }

    private void loadDialog(final String classId){
        final CommonDialog dialog = new CommonDialog(context);


        dialog.setMessage("该课程是付费课程，您尚未订阅")
                .setTitle("提醒")
                .setSingle(false)
                .setPositive("试看前几节")
                .setNegtive("返回")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("classId",classId);
                        IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                    }

                    @Override
                    public void onNegativeClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }

    private void loadPayDialog(final String classId){
        final CommonDialog dialog = new CommonDialog(context);


        dialog.setMessage("是否购买本课程？")
                .setTitle("提醒")
                .setSingle(false)
                .setPositive("购买")
                .setNegtive("再看看")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        Bundle bundle = new Bundle();
                        bundle.putString("classId",classId);
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
        }else if (position == 1 && descriptionView != null){
            return DESCRIPTION;
        }else {
            return BODY;
        }
    }

    @Override
    public int getItemCount() {
        return typeClass.getTypeClass().size() + 2;
    }

    public void addHeadView(View view){
        this.headView = view;
    }

    public void addDesView(View view){
        this.descriptionView = view;
    }
    @Override
    public void onClick(View view) {

    }


    class TestHeadHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.test_back_img)
        ImageView testBackImg;
        @BindView(R.id.test_title_list)
        TextView testTitle;
        public TestHeadHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class TestDesHolder extends RecyclerView.ViewHolder{



        @BindView(R.id.test_class_count)
        TextView testClassCount;
        @BindView(R.id.test_class_des)
        TextView testClassDes;
        public TestDesHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    class ClassItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.class_title)
        TextView classTitle;
        @BindView(R.id.class_description)
        TextView classDescription;
        @BindView(R.id.class_img)
        RoundedImageView classImg;
        @BindView(R.id.class_price)
        TextView classPrice;
        @BindView(R.id.class_count)
        TextView classCount;
        @BindView(R.id.serialize)
        TextView serialize;
        @BindView(R.id.authorize)
        TextView authorize;
        public ClassItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
