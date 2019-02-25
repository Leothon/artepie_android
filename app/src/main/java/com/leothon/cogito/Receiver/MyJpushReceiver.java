package com.leothon.cogito.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

public class MyJpushReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)){
            //获得message的内容
            Bundle bundle=intent.getExtras();
            String title = bundle.getString(JPushInterface.EXTRA_TITLE);
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            //吐司自定义内容
            Toast.makeText(context, "message title"+title+"content:"+message, Toast.LENGTH_LONG).show();
        }

    }
}
