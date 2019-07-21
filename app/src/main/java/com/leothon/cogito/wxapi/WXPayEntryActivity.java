package com.leothon.cogito.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.leothon.cogito.Constants;
import com.leothon.cogito.Mvp.View.Activity.SuccessActivity.SuccessActivity;
import com.leothon.cogito.R;
import com.leothon.cogito.Utils.IntentUtils;
import com.leothon.cogito.View.MyToast;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity  implements IWXAPIEventHandler {



    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.WeChat_APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {


    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            if (resp.errCode == 0) {

                MyToast.getInstance(this).show("支付成功",Toast.LENGTH_SHORT);

                IntentUtils.getInstence().intent(WXPayEntryActivity.this, SuccessActivity.class);
                finish();
            } else if (resp.errCode == -2) {
                MyToast.getInstance(this).show( "您已取消付款!", Toast.LENGTH_SHORT);
                Log.e( "onResp: ", "取消");
                finish();
            } else {
                MyToast.getInstance(this).show( "参数错误!", Toast.LENGTH_SHORT);
                Log.e( "onResp: ", "失败");
                finish();
            }
        } else {
            finish();
        }
    }

}
