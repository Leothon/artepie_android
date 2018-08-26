package com.leothon.cogito.Mvp.View.Activity.SuccessActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.Mvp.View.Activity.HomeActivity.HostActivity;
import com.leothon.cogito.Mvp.View.Activity.SelectClassActivity.SelectClassActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.Weight.CommonDialog;

import butterknife.BindView;
import butterknife.OnClick;

import static com.leothon.cogito.Constants.isRefreshtruebagFragment;

public class SuccessActivity extends BaseActivity {


    private Intent intent;
    private Bundle bundle;

    @BindView(R.id.success_img)
    ImageView successImg;
    @BindView(R.id.success_title)
    TextView successTitle;
    @BindView(R.id.success_to_class)
    TextView successToAction;

    @Override
    public int initLayout() {
        return R.layout.activity_success;
    }

    @Override
    public void initview() {

        intent = getIntent();
        bundle = intent.getExtras();
        setToolbarSubTitle("");
        setToolbarTitle("支付成功");
        if (bundle.getString("type").equals("class")){
            successImg.setVisibility(View.VISIBLE);
            successTitle.setText(bundle.getString("title"));
            successToAction.setText("立即学习");
            successToAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundleto = new Bundle();
                    bundleto.putString("title",bundle.getString("title"));
                    bundleto.putString("url","");
                    bundleto.putString("author","");
                    IntentUtils.getInstence().intent(SuccessActivity.this, SelectClassActivity.class,bundleto);
                    finish();
                }
            });
        }else if (bundle.getString("type").equals("activity")){
            ImageLoader.loadImageViewwithError(this,bundle.getString("url"),successImg,R.drawable.defalutimg);
            successTitle.setText(bundle.getString("title"));
            successToAction.setText("查看电子票");
            successToAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadDialog();
                }
            });
        }

    }

    @Override
    protected boolean isShowBacking() {
        return false;
    }

    private void loadDialog(){
        final CommonDialog dialog = new CommonDialog(this);



        dialog.setTitle("取票二维码")
                .setImageByurl("")
                .setSingle(false)
                .setPositive("保存二维码")
                .setNegtive("取消")
                .setOnClickBottomListener(new CommonDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick() {

                        CommonUtils.makeText(SuccessActivity.this,"已成功保存取票二维码");

                        dialog.dismiss();

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
        isRefreshtruebagFragment = true;
        Bundle bundleto = new Bundle();
        bundleto.putString("type","bag");
        IntentUtils.getInstence().intent(SuccessActivity.this, HostActivity.class,bundleto);
        finish();
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
