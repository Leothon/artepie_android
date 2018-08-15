package com.leothon.cogito.Mvp.View.Activity.AskActivity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leothon.cogito.Constants;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.HomeActivity.HostActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Weight.CommonDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;

public class AskActivity extends BaseActivity {

    @BindView(R.id.teacher_icon)
    RoundedImageView Icon;
    @BindView(R.id.send_icon)
    RoundedImageView send;
    @BindView(R.id.ask_add_img)
    ImageView askAddImg;
    @BindView(R.id.ask_add_sound)
    ImageView askAddSound;
    @BindView(R.id.ask_char_count)
    TextView askCharCound;
    @BindView(R.id.ask_content)
    MaterialEditText askContent;
    @BindView(R.id.ask_img_layout)
    LinearLayout askImgLayout;

    @Override
    public int initLayout() {
        return R.layout.activity_ask;
    }

    @Override
    public void initview() {
        setToolbarSubTitle("");
        setToolbarTitle("");
        Icon.setVisibility(View.VISIBLE);
        Icon.setImageResource(R.drawable.defalutimg);
        send.setVisibility(View.VISIBLE);
        getToolbar().setNavigationIcon(R.drawable.baseline_clear_black_24);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (askContent.getText().toString().equals("")){
                    finish();
                }else {
                    loadDialog();
                }
            }
        });
        askContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                askCharCound.setText("" + editable.toString().length());
                if (editable.toString().length() > 140){
                    askCharCound.setTextColor(Color.RED);
                }else {
                    askCharCound.setTextColor(getResources().getColor(R.color.fontColor));
                }
            }
        });
    }


    private void loadDialog(){
        final CommonDialog dialog = new CommonDialog(this);


        dialog.setMessage("确认退出编辑？")
                .setTitle("提示")
                .setSingle(false)
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {
                        dialog.dismiss();
                        //TODO 删除该收藏
                        finish();
                    }

                    @Override
                    public void onNegtiveClick() {
                        dialog.dismiss();

                    }

                })
                .show();
    }



    @Override
    public void onBackPressed() {
        if (askContent.getText().toString().equals("")){
            super.onBackPressed();
        }else {
            loadDialog();
        }

    }


    @OnClick(R.id.send_icon)
    public void sendAsk(View view){
        //TODO 发送问题
        if (askContent.getText().toString().equals("")){
            CommonUtils.makeText(this,"请输入问题描述");
        }else if (askContent.getText().toString().length() > 140){
            CommonUtils.makeText(this,"最多字数限制为140字");
        }else {
            String content = askContent.getText().toString();
            //TODO 向服务器提交数据
            CommonUtils.makeText(this,"已提交问题： " + content);
            Constants.isRefreshtrueaskFragment = true;
            Bundle bundleto = new Bundle();
            bundleto.putString("type","ask");
            IntentUtils.getInstence().intent(this, HostActivity.class,bundleto);

        }
    }

    @OnClick(R.id.ask_add_sound)
    public void addSound(View view){
        //TODO 增加声音
        CommonUtils.makeText(this,"添加声音功能尚未开启，敬请期待");
    }

    @OnClick(R.id.ask_add_img)
    public void addImg(View view){
        //TODO 增加图片
        CommonUtils.makeText(this,"添加图片");
        //使用动态加载的方式进行图片的添加
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
}
