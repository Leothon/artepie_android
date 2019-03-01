package com.leothon.cogito.Receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.leothon.cogito.Message.NoticeMessage;
import com.leothon.cogito.Mvp.View.Activity.AboutusActivity.AboutusActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;

import org.greenrobot.eventbus.EventBus;

import cn.jpush.android.api.JPushInterface;

public class MyJpushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)){
            //获得message的内容
            Bundle bundle=intent.getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            NoticeMessage noticeMessage = new NoticeMessage();
            noticeMessage.setMessage("show");
            noticeMessage.setTitle(title);
            EventBus.getDefault().post(noticeMessage);
        }

    }


}