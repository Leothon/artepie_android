package com.leothon.cogito.Mvp.View.Activity.AboutusActivity;

import android.content.Intent;
import android.net.Uri;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
public class AboutusActivity extends AppCompatActivity {


    public View aboutPage;
    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(R.mipmap.icon_about)
                .setDescription("艺派，是一所没有围墙的创新型艺术大学。将联手国内各大智慧艺术教育家推出在线视频学习、直播、问答等功能，让每个艺术爱好者都能获得公平、优质、专业的学习资源。")

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
                .addItem(new Element().setTitle("联系电话：(010)5338 1318 点击拨打").setIconDrawable(R.drawable.phoneabout).setOnClickListener(new View.OnClickListener() {
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
                .addItem(new Element().setTitle("联系电话：(010)5338 1318 点击拨打").setIconDrawable(R.drawable.phoneabout).setOnClickListener(new View.OnClickListener() {
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
                //.addGitHub("Leothon")
                .addItem(new Element().setTitle("叮点科技（北京）有限公司版权所有").setGravity(Gravity.CENTER))
                .addItem(new Element().setTitle("Copyright©2018-2019  版本号 V " + CommonUtils.getVerName(this)).setGravity(Gravity.CENTER))
                .create();
        setContentView(aboutPage);


    }


    @Override
    public void onBackPressed() {
        finish();
    }

}
