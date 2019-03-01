package com.leothon.cogito.Mvp.View.Activity.AboutusActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.leothon.cogito.Base.BaseApplication;
import com.leothon.cogito.Mvp.BaseActivity;
import com.leothon.cogito.Mvp.BaseModel;
import com.leothon.cogito.Mvp.BasePresenter;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.StatusBarUtils;
import com.leothon.cogito.View.MyToast;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
public class AboutusActivity extends AppCompatActivity {


    public View aboutPage;
    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //StatusBarUtils.setStatusBarColor(this,R.color.white);
        aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.mipmap.icon_about)
                .setDescription("艺派联手国内各大知名艺术教育家推出线上教育视频学习、互动、在线提问等一站式用户知识管理解决方案，用户通过互联网渠道获取教学资源，让每个用户都能接受公平的、优质的、科学的高等教育。")
                .addItem(new Element().setTitle("联系我们").setGravity(Gravity.CENTER))
                .addGroup("客户服务")
                .addEmail("CogitoTec@163.com","点击此处发送邮件")
                .addItem(new Element().setTitle("联系电话：(010)5338 1318 点击拨打").setIconDrawable(R.drawable.baseline_phone_black_24).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 跳转到拨号

                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + "(010)5338 1318");
                        intent.setData(data);
                        startActivity(intent);
                    }
                }))
                .addItem(new Element().setTitle("客服提醒：为了更好更快的帮您解决问题，请在邮件中留下电话、艺派账号、昵称、相关截图、详细问题等信息。").setIconDrawable(R.drawable.service))
                .addGroup("课程合作")
                .addItem(new Element().setTitle("如果您希望在艺派上开设课程，请与我们联系。"))
                .addEmail("CogitoTec@163.com","点击此处发送邮件")
                .addItem(new Element().setTitle("联系电话：(010)5338 1318 点击拨打").setIconDrawable(R.drawable.baseline_phone_black_24).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 跳转到拨号
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + "(010)5338 1318");
                        intent.setData(data);
                        startActivity(intent);
                    }
                }))
                .addGroup("商务合作")
                .addEmail("CogitoTec@163.com","点击此处发送邮件")
                .addItem(new Element().setTitle("联系电话：(010)5338 1318 点击拨打").setIconDrawable(R.drawable.baseline_phone_black_24).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //TODO 跳转到拨号
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + "(010)5338 1318");
                        intent.setData(data);
                        startActivity(intent);
                    }
                }))
                //.addWebsite("")
                .addGitHub("Leothon")
                .addItem(new Element().setTitle("版本号 V 1.0").setGravity(Gravity.CENTER))
                .create();
        setContentView(aboutPage);


    }


    @Override
    public void onBackPressed() {
        finish();
    }

}
