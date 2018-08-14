package com.leothon.cogito.Mvp.View.Fragment.AboutPage;

import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.Constants;
import com.leothon.cogito.Mvp.BaseFragment;
import com.leothon.cogito.Mvp.View.Activity.AboutusActivity.AboutusActivity;
import com.leothon.cogito.Mvp.View.Activity.DownloadActivity.DownloadActivity;
import com.leothon.cogito.Mvp.View.Activity.EditIndividualActivity.EditIndividualActivity;
import com.leothon.cogito.Mvp.View.Activity.FavActivity.FavActivity;
import com.leothon.cogito.Mvp.View.Activity.HistoryActivity.HistoryActivity;
import com.leothon.cogito.Mvp.View.Activity.NoticeActivity.NoticeActivity;
import com.leothon.cogito.Mvp.View.Activity.SettingsActivity.SettingsActivity;
import com.leothon.cogito.Mvp.View.Activity.WalletActivity.WalletActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.ArcImageView;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * created by leothon on 2018.7.29
 * 我的页面fragment
 */
public class AboutFragment extends BaseFragment {



    @BindView(R.id.search)
    RelativeLayout searchAbout;

    @BindView(R.id.search_title)
    TextView searchTitle;

    @BindView(R.id.backimg_about)
    ArcImageView backImg;
    @BindView(R.id.usericon_about)
    RoundedImageView userIcon;
    @BindView(R.id.username_about)
    TextView userName;
    @BindView(R.id.signature_about)
    TextView signature;


    public AboutFragment() {}


    /**
     * 构造方法
     * @return
     */
    public static AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_about;
    }

    @Override
    protected void initView() {
        searchTitle.setText("搜索相关内容");
        //TODO 根据登录信息来设定用户的各种信息
        Log.e("登录状态", " "+Constants.loginStatus);
        if (Constants.loginStatus == 0){
            userName.setText("未登录");
        }else {
            userName.setText("李大钊");
        }
    }

    @OnClick(R.id.search)
    public void searchAbout(View view){
        CommonUtils.makeText(getMContext(),"点击了搜索");
    }

    @OnClick(R.id.backimg_about)
    public void backImgClick(View v){
        LayoutInflater inflater = LayoutInflater.from(getMContext());
        View imgEntryView = inflater.inflate(R.layout.image, null); // 加载自定义的布局文件
        final AlertDialog dialog = new AlertDialog.Builder(getMContext()).create();
        ImageView img = (ImageView)imgEntryView.findViewById(R.id.image_big);
        img.setImageResource(R.drawable.aboutbackground);
        dialog.setView(imgEntryView); // 自定义dialog
        dialog.show();
        // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
        imgEntryView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                dialog.cancel();
            }
        });
    }
    @OnClick(R.id.username_about)
    public void userNameClick(View v){

        IntentUtils.getInstence().intent(getMContext(), EditIndividualActivity.class);
        //TODO 个人主页
    }
    @OnClick(R.id.usericon_about)
    public void userIconClick(View v){

        IntentUtils.getInstence().intent(getMContext(), EditIndividualActivity.class);
        //TODO 个人主页
    }
    @OnClick(R.id.signature_about)
    public void signatureClick(View v){

        IntentUtils.getInstence().intent(getMContext(), EditIndividualActivity.class);
        //TODO 个人主页
    }
    @OnClick(R.id.fav_about)
    public void favClick(View v){
        IntentUtils.getInstence().intent(getMContext(), FavActivity.class);
        //TODO 收藏
    }
    @OnClick(R.id.download_about)
    public void downloadClick(View v){
        IntentUtils.getInstence().intent(getMContext(), DownloadActivity.class);
        //TODO 下载
    }
    @OnClick(R.id.histroy_about)
    public void historyClick(View v){
        IntentUtils.getInstence().intent(getMContext(), HistoryActivity.class);
        //TODO 历史
    }
    @OnClick(R.id.wallet_about)
    public void walletClick(View v){
        IntentUtils.getInstence().intent(getMContext(), WalletActivity.class);
        //TODO 钱包
    }
    @OnClick(R.id.message_about)
    public void messageClick(View v){
        IntentUtils.getInstence().intent(getMContext(), NoticeActivity.class);
        //TODO 信息
    }
    @OnClick(R.id.settings_about)
    public void settingsClick(View v){
        //TODO 设置
        IntentUtils.getInstence().intent(getMContext(), SettingsActivity.class);
    }
    @OnClick(R.id.about_about)
    public void aboutClick(View v){
        IntentUtils.getInstence().intent(getMContext(), AboutusActivity.class);
        //TODO 关于
    }
    @Override
    protected void initData() {

    }

    @Override
    public void hideLoading() {}

    @Override
    public void showMessage(@NonNull String message) {}

    @Override
    public void showLoading() {}
}
