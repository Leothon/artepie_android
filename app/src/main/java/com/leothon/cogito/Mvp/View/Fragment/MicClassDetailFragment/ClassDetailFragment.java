package com.leothon.cogito.Mvp.View.Fragment.MicClassDetailFragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.leothon.cogito.Bean.DetailClassInfo;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.MicClassActivity.Mic2ClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.View.ObserveScrollView;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;


public class ClassDetailFragment extends BaseFragment implements ObserveScrollView.ScrollListener{


    @BindView(R.id.class_detail_desc_2)
    TextView classDetailDesc;
    @BindView(R.id.class_detail_author_desc_2)
    TextView classDetailAuthorDesc;
    @BindView(R.id.comment_first_img2)
    RoundedImageView commentFistImg;
    @BindView(R.id.comment_first_name2)
    TextView commentFirstName;
    @BindView(R.id.comment_first_content2)
    TextView commentFirstContent;
    @BindView(R.id.comment_first_like2)
    TextView commentFirstLike;
    @BindView(R.id.comment_second_img2)
    RoundedImageView commentSecondImg;
    @BindView(R.id.comment_second_name2)
    TextView commentSecondName;
    @BindView(R.id.comment_second_content2)
    TextView commentSecondContent;
    @BindView(R.id.comment_second_like2)
    TextView commentSecondLike;
    @BindView(R.id.comment_third_img2)
    RoundedImageView commentThirdImg;
    @BindView(R.id.comment_third_name2)
    TextView commentThirdName;
    @BindView(R.id.comment_third_content2)
    TextView commentThirdContent;
    @BindView(R.id.comment_third_like2)
    TextView commentThirdLike;
    @BindView(R.id.comment_more_class2)
    TextView commentMore;
    @BindView(R.id.class_free_2)
    TextView classFree;
    @BindView(R.id.class_detail_buy_btn_2)
    TextView classDetailBuy;
    @BindView(R.id.scroll_mic2_class)
    ObserveScrollView scrollMicClass;
    private Mic2ClassActivity mic2ClassActivity;

    private DetailClassInfo detailClassInfo;
    public ClassDetailFragment() {
    }


    public static ClassDetailFragment newInstance(DetailClassInfo detailClassInfo) {
        ClassDetailFragment fragment = new ClassDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("info",detailClassInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_class_detail;
    }

    @Override
    protected void initView() {
        mic2ClassActivity = (Mic2ClassActivity)getActivity();
        detailClassInfo = new DetailClassInfo();
        scrollMicClass.setScrollListener(this);
        detailClassInfo = (DetailClassInfo)getArguments().getSerializable("info");
        classDetailDesc.setText(detailClassInfo.getDescClass());
        classDetailAuthorDesc.setText(detailClassInfo.getDescAuthor());
        ImageLoader.loadImageViewThumbnailwitherror(getMContext(),detailClassInfo.getFirstImg(),commentFistImg,R.drawable.defalutimg);
        commentFirstContent.setText(detailClassInfo.getFirstContent());
        commentFirstName.setText(detailClassInfo.getFirstName());
        commentFirstLike.setText(detailClassInfo.getFirstLike());
        ImageLoader.loadImageViewThumbnailwitherror(getMContext(),detailClassInfo.getSecondImg(),commentSecondImg,R.drawable.defalutimg);
        commentSecondContent.setText(detailClassInfo.getSecondContent());
        commentSecondName.setText(detailClassInfo.getSecondName());
        commentSecondLike.setText(detailClassInfo.getSecondLike());
        ImageLoader.loadImageViewThumbnailwitherror(getMContext(),detailClassInfo.getThirdImg(),commentThirdImg,R.drawable.defalutimg);
        commentThirdContent.setText(detailClassInfo.getThirdContent());
        commentThirdName.setText(detailClassInfo.getThirdName());
        commentThirdLike.setText(detailClassInfo.getThirdLike());
    }

    @Override
    public void scrollOrientation(int l, int t, int oldl, int oldt) {
        if (t > 400 && oldt < t){

        }else if (t <= 400 && oldt >= t){

        }
    }
    @Override
    protected void initData() {

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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            //Fragment隐藏时调用
        }else {
            //Fragment显示时调用
        }

    }
}
