package com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomFragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leothon.cogito.Adapter.ArticleCommentAdapter;
import com.leothon.cogito.Adapter.BaseAdapter;
import com.leothon.cogito.Adapter.UploadClassAdapter;
import com.leothon.cogito.Bean.CustomEnterprise;
import com.leothon.cogito.Listener.loadMoreDataListener;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.ArticleActivity.ArticleActivity;
import com.leothon.cogito.Mvp.View.Activity.CustomActivity.CustomActivity;
import com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity.EditIndividualActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.AddressPickTask;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.View.MyToast;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.entity.City;
import cn.addapp.pickers.entity.County;
import cn.addapp.pickers.entity.Province;


public class Custom1Fragment extends BaseFragment {


    @BindView(R.id.enterprise_size)
    RecyclerView enterpriseSize;

    @BindView(R.id.edit_enterprise_name)
    MaterialEditText editEnterpriseName;
    @BindView(R.id.choose_enterprise_address)
    RelativeLayout chooseEnterpriseAddress;


    @BindView(R.id.edit_enterprise_work)
    MaterialEditText editEnterpriseWork;

    @BindView(R.id.show_enterprise_address)
    TextView showEnterpriseAddress;
    @BindView(R.id.edit_enterprise_industry)
    MaterialEditText editEnterpriseIndustry;
    private ArrayList<String> list;
    private UploadClassAdapter uploadClassAdapter;
    private String resultEnterpriseName;
    private String resultEnterpriseAddress;
    private String resultEnterpriseIndustry;
    private String resultEnterpriseWork;
    private String resultEnterpriseSize;

    private CustomEnterprise customEnterprise;


    public Custom1Fragment() {

    }


    public static Custom1Fragment newInstance() {
        Custom1Fragment fragment = new Custom1Fragment();
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_custom1;
    }

    @Override
    protected void initView() {
        loadSize();
        initSizeAdapter();

        customEnterprise = new CustomEnterprise();
        editEnterpriseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                customEnterprise.setEnterpriseName(editable.toString());
                ((CustomActivity)getActivity()).setCustomEnterprise(customEnterprise);

            }
        });

        editEnterpriseIndustry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                customEnterprise.setEnterpriseIndustry(editable.toString());
                ((CustomActivity)getActivity()).setCustomEnterprise(customEnterprise);
            }
        });

        editEnterpriseWork.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                customEnterprise.setEnterpriseWork(editable.toString());
                ((CustomActivity)getActivity()).setCustomEnterprise(customEnterprise);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.choose_enterprise_address)
    public void chooseAddress(View view){
        getAddress();
    }

    private void loadSize() {
        list = new ArrayList<>();
        list.add("0-20人");
        list.add("20-99人");
        list.add("100-200人");
        list.add("200-999人");
        list.add("1000人以上");
    }


    private void getAddress(){
        AddressPickTask task = new AddressPickTask(getActivity());
        task.setHideProvince(false);
        task.setHideCounty(false);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                MyToast.getInstance(getMContext()).show("数据初始化失败", Toast.LENGTH_SHORT);
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    setAddress(province.getAreaName() + " " + city.getAreaName());
                } else {
                    if (province.getAreaName().equals("北京") || province.getAreaName().equals("上海") || province.getAreaName().equals("天津") || province.getAreaName().equals("重庆")){
                        setAddress(province.getAreaName() + " " + county.getAreaName());
                    }else {
                        setAddress(province.getAreaName() + " " + city.getAreaName() + " " + county.getAreaName());
                    }

                }
            }
        });
        task.execute("北京", "北京", "东城区");
    }

    private void setAddress(String addressString){
        resultEnterpriseAddress = addressString;
        showEnterpriseAddress.setText(resultEnterpriseAddress);
        customEnterprise.setEnterpriseAddress(resultEnterpriseAddress);
        ((CustomActivity)getActivity()).setCustomEnterprise(customEnterprise);
    }
    private void initSizeAdapter(){
        uploadClassAdapter = new UploadClassAdapter(list,getMContext());
        enterpriseSize.setHasFixedSize(true);
        enterpriseSize.setLayoutManager(new GridLayoutManager(getMContext(),3,GridLayoutManager.VERTICAL,false));
        enterpriseSize.setAdapter(uploadClassAdapter);
        uploadClassAdapter.setClassTypeOnClickListener(new UploadClassAdapter.ClassTypeOnClickListener() {
            @Override
            public void classTypeClickListener(String type) {
                customEnterprise.setEnterpriseSize(type);
                ((CustomActivity)getActivity()).setCustomEnterprise(customEnterprise);
            }
        });

    }


//    private void showSheetDialog() {
//        View view = View.inflate(getMContext(), R.layout.article_comment_layout, null);
//
//        close_article_comment = (ImageView) view.findViewById(R.id.close_article_comment);
//        article_comment_rv = (RecyclerView) view.findViewById(R.id.article_comment_rv);
//        article_comment_count = (TextView) view.findViewById(R.id.article_comment_count);
//        commentInArticle = (CardView) view.findViewById(R.id.comment_in_article);
//
//        swpArticleComment = (SwipeRefreshLayout) view.findViewById(R.id.swp_article_comment);
//        swpArticleComment.setRefreshing(false);
//        article_comment_count.setText("共" + article.getCommentCount() + "条留言");
//        articleCommentAdapter = new ArticleCommentAdapter(ArticleActivity.this,articleComments);
//        article_comment_rv.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ArticleActivity.this);
//        article_comment_rv.setLayoutManager(linearLayoutManager);
//        article_comment_rv.setItemAnimator(new DefaultItemAnimator());
//        article_comment_rv.setAdapter(articleCommentAdapter);
//        article_comment_rv.addOnScrollListener(new loadMoreDataListener(linearLayoutManager) {
//            @Override
//            public void onLoadMoreData(int currentPage) {
//                swpArticleComment.setRefreshing(true);
//                articlePresenter.getCommentMore(article.getArticleId(),currentPage * 15);
//            }
//        });
//        bottomSheetDialog = new BottomSheetDialog(ArticleActivity.this, R.style.dialog);
//        bottomSheetDialog.setContentView(view);
//        mDialogBehavior = BottomSheetBehavior.from((View) view.getParent());
//        mDialogBehavior.setPeekHeight(getWindowHeight());
//        mDialogBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
//                    bottomSheetDialog.dismiss();
//                    mDialogBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                }
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//            }
//        });
//        close_article_comment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                bottomSheetDialog.dismiss();
//            }
//        });
//
//        commentInArticle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if ((boolean)activitysharedPreferencesUtils.getParams("login",false)){
//                    showCommentDialog("0");
//                    commentDialog.show();
//                    popupInputMethod();
//                }else {
//                    CommonUtils.loadinglogin(ArticleActivity.this);
//                }
//
//
//            }
//        });
//        articleCommentAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClickListener(View v, int position) {
//                if (uuid.equals(article.getArticleAuthorId())){
//                    showCommentDialog(articleComments.get(position).getArticleCommentId());
//                    commentDialog.show();
//                    popupInputMethod();
//
//                }else {
//                    MyToast.getInstance(ArticleActivity.this).show("非本文作者不可回复留言",Toast.LENGTH_SHORT);
//                }
//            }
//        });
//        articleCommentAdapter.setOnItemLongClickListener(new BaseAdapter.OnItemLongClickListener() {
//            @Override
//            public void onItemLongClickListener(View v, int position) {
//                if (uuid.equals(article.getArticleAuthorId()) || articleComments.get(position).getArticleCommentUserId().equals(uuid)){
////                    MyToast.getInstance(ArticleActivity.this).show("删除功能暂缓开通",Toast.LENGTH_SHORT);
//                    loadDialog(articleComments.get(position),position,articleCommentAdapter);
//                }else {
//                    MyToast.getInstance(ArticleActivity.this).show("非作者或本人不可删除留言",Toast.LENGTH_SHORT);
//                }
//            }
//        });
//        Window window = bottomSheetDialog.getWindow();
//        window.setWindowAnimations(R.style.ActionSheetDialogAnimation);
//    }
}
