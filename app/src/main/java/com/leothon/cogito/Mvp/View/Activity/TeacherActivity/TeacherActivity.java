package com.leothon.cogito.Mvp.View.Activity.TeacherActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.leothon.cogito.Adapter.TeacherSelfAdapter;
import com.leothon.cogito.Bean.ClassItem;
import com.leothon.cogito.Bean.Teacher;
import com.leothon.cogito.Bean.TeacherSelf;
import com.leothon.cogito.Bean.VideoClass;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;

public class TeacherActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {


    private TeacherSelfAdapter teacherSelfAdapter;
    private TeacherSelf teacherSelf;
    @BindView(R.id.swp_tea)
    SwipeRefreshLayout swpTea;
    @BindView(R.id.rv_tea)
    RecyclerView rvTea;
    @BindView(R.id.tea_bar)
    CardView teaBar;
    @BindView(R.id.teacher_icon)
    RoundedImageView teacherIcon;
    private Intent intent;
    private Bundle bundle;

    private String[] title = {"发声位置","咬字问题","共鸣","歌曲的演奏方式","歌唱状态","气息问题","高音","歌曲的认识","中低声区","换声","ae训练法","咽腔肌肉训练","假声","半声","高腔"};
    private String[] des = {"歌唱中发声位置的一些问题",
            "在歌唱中怎么做到字正腔圆",
            "头腔共鸣与胸腔共鸣的区别",
            "怎么表达不同曲风的方式方法",
            "歌曲演唱者应保持什么状态",
            "歌唱中怎么解决气息问题",
            "解决高音的方式方法",
            "怎么理解该歌曲",
            "怎么建立良好的中低声区",
            "如何建立正确的换声系统",
            "如何用a和e进行歌唱训练",
            "如何进行咽腔肌肉的训练",
            "假声的发声训练",
            "半声训练的方式方法",
            "高腔训练的男高音小结"};
    private String[] price = {"0","0","369","189","459","99","179","179","359","459","399","469","269","329","599"};

    private LinearLayoutManager linearLayoutManager;
    @Override
    public int initLayout() {
        return R.layout.activity_teacher;
    }

    @Override
    public void initview() {
        StatusBarUtils.transparencyBar(this);
        teaBar.setRadius(CommonUtils.dip2px(this,5));
        intent = getIntent();
        bundle = intent.getExtras();
        loadFalseData();
        initAdapter();
        setToolbarTitle("");
        setToolbarSubTitle(teacherSelf.getTeaname());


    }

    public void initAdapter(){
        swpTea.setOnRefreshListener(this);
        teacherSelfAdapter = new TeacherSelfAdapter(teacherSelf,this);
        initHeadView(teacherSelfAdapter);
        initDesView(teacherSelfAdapter);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvTea.setLayoutManager(linearLayoutManager);
        rvTea.setAdapter(teacherSelfAdapter);
        rvTea.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (linearLayoutManager != null){
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    if (position > 0){
                        teaBar.setVisibility(View.VISIBLE);
                        teaBar.setTranslationY(CommonUtils.getStatusBarHeight(TeacherActivity.this));
                        teacherIcon.setVisibility(View.VISIBLE);
                        teacherIcon.setImageResource(teacherSelf.getTeaicon());
                    }else {
                        teaBar.setVisibility(View.GONE);
                    }
                }
            }
        });

    }




    public void initHeadView(TeacherSelfAdapter teacherSelfAdapter){
        View headView = View.inflate(this,R.layout.teacher_background,null);
        teacherSelfAdapter.addHeadView(headView);
    }

    public void initDesView(TeacherSelfAdapter teacherSelfAdapter){
        View desView = View.inflate(this,R.layout.teacher_background_description,null);
        teacherSelfAdapter.addDesView(desView);
    }

    private void loadFalseData(){
        ArrayList<ClassItem> classitems = new ArrayList<>();
        for (int i = 0;i < 15;i++){
            ClassItem classItem = new ClassItem();
            classItem.setClasstitle(title[i]);
            classItem.setClassdescription(des[i]);
            classItem.setClassprice(price[i]);
            classItem.setClassurl("http://image.baidu.com/search/down?tn=download&ipn=dwnl&word=download&ie=utf8&fr=result&url=http%3A%2F%2Fchina-ljsw.com%2Fupdate%2F3%2Fbb5f8d46b790e71e34.jpg&thumburl=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D494318182%2C3784477290%26fm%3D27%26gp%3D0.jpg");
            classitems.add(classItem);
        }
        teacherSelf = new TeacherSelf();
        teacherSelf.setTeaname(bundle.getString("name"));
        teacherSelf.setTeadescription(bundle.getString("description"));
        teacherSelf.setTeaicon(bundle.getInt("icon"));
        teacherSelf.setClassItems(classitems);
    }
    @Override
    public void initdata() {

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public BaseModel initModel() {
        return null;
    }



    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {

    }

    @Override
    public void onRefresh() {
        swpTea.setRefreshing(false);
    }
}
