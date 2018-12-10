package com.leothon.cogito.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.leothon.cogito.Bean.BagBuy;

import com.leothon.cogito.Bean.studyLine;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;

import com.leothon.cogito.Utils.IntentUtils;

import com.makeramen.roundedimageview.RoundedImageView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import freemarker.template.utility.DateUtil;

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


    private XAxis xAxis;                //X轴
    private YAxis leftYAxis;            //左侧Y轴
    private YAxis rightYaxis;           //右侧Y轴
    private Legend legend;              //图例
    private LimitLine limitLine;        //限制线


    public BagAdapter(ArrayList<BagBuy> bagBuyclass,ArrayList<BagBuy> recommendclass, Context context){
        this.bagBuyclass = bagBuyclass;
        this.recommendclass = recommendclass;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        if (viewType == HEAD0) {
            return new BuyClassTitleHolder(LayoutInflater.from(context).inflate(R.layout.studyline,parent,false));
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

            List<studyLine> lists = new ArrayList<>();

            for (int i = 0;i < 7;i ++){
                studyLine studyline = new studyLine();

                studyline.setClassCount((int)(Math.random()*10));
                if (i == 6){
                    studyline.setDate("今天");
                }else {
                    studyline.setDate(CommonUtils.getTime(i - 6));
                }
                lists.add(studyline);
            }
            initChart(buyClassTitleHolder.studyLine,lists);
            showLineChart(buyClassTitleHolder.studyLine,lists);
            Drawable drawable = CommonUtils.getContext().getResources().getDrawable(R.drawable.fade_orange);
            setChartFillDrawable(buyClassTitleHolder.studyLine,drawable);
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
        @BindView(R.id.study_line)
        LineChart studyLine;
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


    /**
     * 初始化图表
     */
    private void initChart(LineChart lineChart,List<studyLine> lines) {
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
        lineDataSet.setCircleHoleColor(Color.parseColor("#f26402"));
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
    public void showLineChart(LineChart lineChart,final List<studyLine> dataList) {
        List<Entry> entries = new ArrayList<>();
        Log.e("测试", "数量: " + dataList.size());
        for (int i = 0; i < dataList.size(); i++) {
            studyLine data = dataList.get(i);

            Entry entry = new Entry(i, (float) data.getClassCount());
            entries.add(entry);

        }


        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                String date = dataList.get((int) value % dataList.size()).getDate();
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
