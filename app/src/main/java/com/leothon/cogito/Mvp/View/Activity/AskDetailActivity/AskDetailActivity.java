package com.leothon.cogito.Mvp.View.Activity.AskDetailActivity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.leothon.cogito.Adapter.AskDetailAdapter;
import com.leothon.cogito.Bean.AskDetail;
import com.leothon.cogito.Bean.UserComment;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class AskDetailActivity extends BaseActivity {


    @BindView(R.id.rv_ask_detail)
    RecyclerView rvAskDetail;
    @BindView(R.id.comment_to_detail_icon)
    RoundedImageView commentIcon;
    @BindView(R.id.comment_to_detail)
    MaterialEditText comment;
    @BindView(R.id.send_comment_detail)
    ImageView sendComment;

    private AskDetail askDetail;
    private AskDetailAdapter askDetailAdapter;
    @Override
    public int initLayout() {
        return R.layout.activity_ask_detail;
    }

    @Override
    public void initview() {
        setToolbarSubTitle("");
        setToolbarTitle("");
        loadFalseData();
        initAdapter();
        commentIcon.setImageResource(R.drawable.defalutimg);
    }

    private void loadFalseData(){
        askDetail = new AskDetail();
        askDetail.setUsericon("https://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=https%3A%2F%2Ftimgsa.baidu.com%2Ftimg%3Fimage%26quality%3D80%26size%3Db9999_10000%26sec%3D1534355936116%26di%3De8d37adaf4bac509d40549f88a07bac9%26imgtype%3D0%26src%3Dhttp%253A%252F%252Fimgsrc.baidu.com%252Fforum%252Fpic%252Fitem%252Fb0ca8026d72fe30a8b82a131.jpg&thumburl=https%3A%2F%2Fss3.bdstatic.com%2F70cFv8Sh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D552083791%2C3490826282%26fm%3D27%26gp%3D0.jpg");
        askDetail.setUsername("刘备");
        askDetail.setUserdes("汉昭烈帝刘玄德");
        askDetail.setContent("话说天下合久必分，分久必合，合久必分，分久必合，合久必分，分久必合，合久必分，分久必合");
        askDetail.setImgurl("https://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=https%3A%2F%2Ftimgsa.baidu.com%2Ftimg%3Fimage%26quality%3D80%26size%3Db9999_10000%26sec%3D1534356012658%26di%3Dcb53e7fcc3bcfa3d17500b6204f6bbdc%26imgtype%3D0%26src%3Dhttp%253A%252F%252Fn9.cmsfile.pg0.cn%252Fgroup2%252FM00%252F21%252F58%252FCgqg2lj37g2ARnsOAACIU6n0Aoo911.jpg%253Fenable%253D%2526w%253D550%2526h%253D366%2526cut%253D&thumburl=https%3A%2F%2Fss1.bdstatic.com%2F70cFuXSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D2083899954%2C2860567755%26fm%3D27%26gp%3D0.jpg");
        ArrayList<UserComment> userComments = new ArrayList<>();
        for (int i = 0;i < 20;i++){
            UserComment userComment = new UserComment();
            userComment.setUsername("曹操");
            userComment.setUsericon("https://image.baidu.com/search/down?tn=download&word=download&ie=utf8&fr=detail&url=https%3A%2F%2Ftimgsa.baidu.com%2Ftimg%3Fimage%26quality%3D80%26size%3Db9999_10000%26sec%3D1534355965654%26di%3D80b2e428590ce5cef81a1060a5ae48d4%26imgtype%3D0%26src%3Dhttp%253A%252F%252Fimgsa.baidu.com%252Fbaike%252Fpic%252Fitem%252Fc2fdfc039245d6889a29f356a8c27d1ed21b241c.jpg&thumburl=https%3A%2F%2Fss0.bdstatic.com%2F70cFvHSh_Q1YnxGkpoWK1HF6hhy%2Fit%2Fu%3D1522657165%2C2769090755%26fm%3D11%26gp%3D0.jpg");
            userComment.setUsercomment("刘皇叔讲的很对，我赞同，改天再约");
            userComments.add(userComment);
        }
        askDetail.setUserComments(userComments);
    }

    private void initAdapter(){
        askDetailAdapter = new AskDetailAdapter(askDetail,this);
        rvAskDetail.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvAskDetail.setAdapter(askDetailAdapter);

    }
    @Override
    public void initdata() {

    }

    @OnClick(R.id.send_comment_detail)
    public void sendComment(View view){
        String commentContent = comment.getText().toString();
        if (commentContent.equals("")){
            CommonUtils.makeText(CommonUtils.getContext(),"请输入评论的内容");
        }else {
            //TODO 发送评论
            CommonUtils.makeText(CommonUtils.getContext(),"已发送评论" + commentContent);
        }
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
}
