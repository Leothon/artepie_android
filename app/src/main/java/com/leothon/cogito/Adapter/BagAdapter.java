package com.leothon.cogito.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import com.leothon.cogito.Bean.SelectClass;
import com.leothon.cogito.Bean.StudyLine;
import com.leothon.cogito.DTO.BagPageData;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;

import com.leothon.cogito.Utils.IntentUtils;

import com.leothon.cogito.Utils.SharedPreferencesUtils;
import com.leothon.cogito.Utils.tokenUtils;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by leothon on 2018.8.10
 */
public class BagAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private BagPageData bagPageData;

    private Context context;
    private int HEAD0 = 0;
    private int HEAD1 = 1;
    private int HEAD2 = 2;
    private int HEAD3 = 3;
    private int HEAD4 = 4;


    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线


    private boolean isLogin;
    public BagAdapter(BagPageData bagPageData, Context context,boolean isLogin){
        this.bagPageData = bagPageData;
        this.context = context;
        this.isLogin = isLogin;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        if (viewType == HEAD0) {
            return new BuyClassTitleHolder(LayoutInflater.from(context).inflate(R.layout.studyline,parent,false));
        }else if(viewType == HEAD1){
            return new BuyClassHolder(LayoutInflater.from(context).inflate(R.layout.class_item,parent,false));
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
        final SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(context,"saveToken");
        if (viewType == HEAD0 ) {
            BuyClassTitleHolder buyClassTitleHolder = (BuyClassTitleHolder)holder;
            buyClassTitleHolder.dividerBuy.setText("已订阅课程");
            buyClassTitleHolder.topDiv.setVisibility(View.VISIBLE);

            StudyLine studyline = bagPageData.getStudyLine();
            ArrayList<String> days = new ArrayList<>();
            for (int i = 0;i < bagPageData.getStudyLine().getClassCountDat().size();i ++){
                if (i == 6){
                    days.add("今天");
                }else {
                    days.add(CommonUtils.getTime(i - 6));
                }
            }
            buyClassTitleHolder.studyCountLine.setText("总学习课程 ：" + bagPageData.getStudyLine().getClassCount() + "个");
            studyline.setDate(days);
            initChart(buyClassTitleHolder.studyLine,studyline);
            showLineChart(buyClassTitleHolder.studyLine,studyline);
            Drawable drawable = CommonUtils.getContext().getResources().getDrawable(R.drawable.fade_orange);
            setChartFillDrawable(buyClassTitleHolder.studyLine,drawable);
        }else if(viewType == HEAD1){
            int position1 = position - 1;
            final SelectClass buyClass = bagPageData.getSelectClasses().get(position1);
            BuyClassHolder buyClassHolder = (BuyClassHolder)holder;
            ImageLoader.loadImageViewThumbnailwitherror(context,buyClass.getSelectbackimg(),buyClassHolder.classImg,R.drawable.defalutimg);
            buyClassHolder.classCount.setText(buyClass.getSelectstucount() + "人次已学习");
            buyClassHolder.classDescription.setText(buyClass.getSelectdesc());
            buyClassHolder.classPrice.setVisibility(View.GONE);
            buyClassHolder.classTitle.setText(buyClass.getSelectlisttitle());
            buyClassHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position1 = position - 1;
                    Bundle bundle = new Bundle();
                    bundle.putString("classId",buyClass.getSelectId());
                    IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                }
            });
        }else if(viewType == HEAD2){
            RecommentClassTitleHolder recommentClassTitleHolder = (RecommentClassTitleHolder)holder;
            recommentClassTitleHolder.dividerRe.setText("为您推荐课程");
            recommentClassTitleHolder.topDiv.setVisibility(View.VISIBLE);
        }else if (viewType == HEAD3){
            final int position2 = position - (bagPageData.getSelectClasses().size() + 2);
            final RecommentClassHolder recommentClassHolder = (RecommentClassHolder)holder;
            final SelectClass fineClass = bagPageData.getFineClasses().get(position2);
            ImageLoader.loadImageViewThumbnailwitherror(context,fineClass.getSelectbackimg(),recommentClassHolder.foryouIV,R.drawable.defalutimg);
            recommentClassHolder.foryouTV.setText(fineClass.getSelectlisttitle());
            recommentClassHolder.foryouAuthor.setText(fineClass.getSelectauthor());
            recommentClassHolder.foryouCount.setText(fineClass.getSelectstucount() + "人次已学习");
            String price = fineClass.getSelectprice();
            if (fineClass.isIsbuy()){
                recommentClassHolder.foryouPrice.setText("已购买");
            }else if (price.equals("0.00")){
                recommentClassHolder.foryouPrice.setText("免费");
            }else {
                recommentClassHolder.foryouPrice.setText("￥" + price);
            }
            recommentClassHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    int pos = position2;
                    if (recommentClassHolder.foryouPrice.getText().toString().equals("已购买") || recommentClassHolder.foryouPrice.getText().toString().equals("免费") || bagPageData.getFineClasses().get(pos).getSelectauthorid().equals(tokenUtils.ValidToken(sharedPreferencesUtils.getParams("token","").toString()).getUid())){
                        Bundle bundle = new Bundle();
                        bundle.putString("classId",fineClass.getSelectId());
                        IntentUtils.getInstence().intent(context, SelectClassActivity.class,bundle);
                    }else {
                        if (isLogin){
                            String classId = fineClass.getSelectId();
                            loadDialog(classId);
                        }else {
                            CommonUtils.loadinglogin(context);
                        }

                    }
                }
            });
        }else {
            return;
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

    @Override
    public int getItemViewType(int position) {
        if (position == 0 ) {
            return HEAD0;
        }else if (position <= bagPageData.getSelectClasses().size() && position != 0){
            return HEAD1;
        }else if (position == bagPageData.getSelectClasses().size() + 1){
            return HEAD2;
        }else if (position <= (bagPageData.getFineClasses().size() + bagPageData.getSelectClasses().size() + 1) && position != 0){
            return HEAD3;
        }else {
            return HEAD4;
        }
    }


    @Override
    public int getItemCount() {


        return bagPageData.getSelectClasses().size() + bagPageData.getFineClasses().size() + 3;
    }




    class BuyClassTitleHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.divider_title)
        TextView dividerBuy;
        @BindView(R.id.topdiv)
        View topDiv;
        @BindView(R.id.bottomdiv)
        View bottomDiv;
        @BindView(R.id.study_line)
        LineChart studyLine;
        @BindView(R.id.study_count_line)
        TextView studyCountLine;
        public BuyClassTitleHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    class BuyClassHolder extends RecyclerView.ViewHolder{

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
        public BuyClassHolder(View itemView) {
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
        ImageView foryouIV;
        @BindView(R.id.foryou_tv)
        TextView foryouTV;
        @BindView(R.id.foryou_price)
        TextView foryouPrice;
        @BindView(R.id.foryou_author)
        TextView foryouAuthor;
        @BindView(R.id.foryou_count)
        TextView foryouCount;
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

    /**
     * 初始化图表
     */
    private void initChart(LineChart lineChart,StudyLine lines) {
        /***图表设置***/
        //是否展示网格线
        lineChart.setDrawGridBackground(false);
        //是否显示边界
        lineChart.setDrawBorders(false);
        //是否可以拖动
        lineChart.setDragEnabled(false);
        //是否有触摸事件
        lineChart.setTouchEnabled(false);
        //设置XY轴动画效果
        lineChart.animateY(2500);
        lineChart.animateX(1500);

        /***XY轴的设置***/
        xAxis = lineChart.getXAxis();
        leftYAxis = lineChart.getAxisLeft();
        rightYaxis = lineChart.getAxisRight();
        //X轴设置显示位置在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAxisMinimum(0f);
        xAxis.setGranularity(1f);
        xAxis.setDrawAxisLine(false);
        xAxis.setTextColor(Color.parseColor("#808080"));
        //保证Y轴从0开始，不然会上移一点
        leftYAxis.setAxisMinimum(-2f);
        rightYaxis.setAxisMinimum(-2f);

        xAxis.setDrawGridLines(false);
        rightYaxis.setDrawGridLines(false);
        leftYAxis.setDrawGridLines(false);
        rightYaxis.setEnabled(false);
        leftYAxis.setEnabled(false);








        /***折线图例 标签 设置***/
        legend = lineChart.getLegend();
        //设置显示类型，LINE CIRCLE SQUARE EMPTY 等等 多种方式，查看LegendForm 即可
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextSize(12f);
        //显示位置 左下方
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        //是否绘制在图表里面
        legend.setDrawInside(false);
        legend.setEnabled(false);

        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);
    }
    /**
     * 曲线初始化设置 一个LineDataSet 代表一条曲线
     *
     * @param lineDataSet 线条

     * @param mode
     */
    private void initLineDataSet(LineDataSet lineDataSet, LineDataSet.Mode mode) {
        lineDataSet.setColor(Color.parseColor("#f26402"));
        lineDataSet.setCircleColor(Color.parseColor("#ffffff"));
        lineDataSet.setLineWidth(2f);
        lineDataSet.setCircleRadius(6f);
        //设置曲线值的圆点是实心还是空心
        lineDataSet.setDrawCircleHole(true);
        lineDataSet.setValueTextSize(10f);
        //设置折线图填充
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFormLineWidth(1f);
        lineDataSet.setFormSize(15.f);
        lineDataSet.setCircleColorHole(Color.parseColor("#f26402"));
        lineDataSet.setCircleHoleRadius(4f);


        lineDataSet.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                DecimalFormat df = new DecimalFormat("");
                return df.format(value);
            }
        });

        if (mode == null) {
            //设置曲线展示为圆滑曲线（如果不设置则默认折线）
            lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        } else {
            lineDataSet.setMode(mode);
        }
    }

    /**
     * 展示曲线
     *
     * @param dataList 数据集合


     */
    public void showLineChart(LineChart lineChart,final StudyLine dataList) {
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < dataList.getClassCountDat().size(); i++) {

            Entry entry = new Entry(i, Float.valueOf(dataList.getClassCountDat().get(i)));
            entries.add(entry);

        }


        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String date = dataList.getDate().get((int) value % dataList.getClassCountDat().size());

                return date;
            }
        });

        // 每一个LineDataSet代表一条线
        LineDataSet lineDataSet = new LineDataSet(entries, "");

        initLineDataSet(lineDataSet, LineDataSet.Mode.CUBIC_BEZIER);
        LineData lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
    }


    /**
     * 设置线条填充背景颜色
     *
     * @param drawable
     */
    public void setChartFillDrawable(LineChart lineChart,Drawable drawable) {
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            LineDataSet lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            //避免在 initLineDataSet()方法中 设置了 lineDataSet.setDrawFilled(false); 而无法实现效果
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFillDrawable(drawable);
            lineChart.invalidate();
        }
    }
}
