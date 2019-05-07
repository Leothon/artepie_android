package com.leothon.cogito.Weight;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.leothon.cogito.R;
import com.leothon.cogito.Utils.CommonUtils;
import com.leothon.cogito.Utils.ImageLoader.ImageLoader;

public class UpdateDialog extends Dialog {


    private TextView titleTv ;

    private TextView messageTv ;

    private TextView negtive,updateMarket,updateLink;



    private Context context;
    public UpdateDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.context = context;
    }

    private String message;

    /**
     * * 底部是否只有一个按钮
     * */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_dialog);
        setCanceledOnTouchOutside(true);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }
    /**
     * * 初始化界面的确定和取消监听器
     * */
    private void initEvent() {
        negtive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onClickBottomListener!= null) {
                    onClickBottomListener.onNegativeClick();
                }
            }
        });

        updateMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onClickBottomListener!= null) {
                    onClickBottomListener.onMarketClick();
                }
            }
        });

        updateLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( onClickBottomListener!= null) {
                    onClickBottomListener.onLinkClick();
                }
            }
        });

    }

    /** * 初始化界面控件的显示数据 */
    private void refreshView() {

        if (!TextUtils.isEmpty(message)) {
            messageTv.setText(message);
        }
        //如果设置按钮的文字



    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }
    /**
     * * 初始化界面控件
     * */
    private void initView() {

        updateMarket = (TextView) findViewById(R.id.update_market);
        negtive = (TextView) findViewById(R.id.update_negtive);
        updateLink = (TextView)findViewById(R.id.update_link);
        titleTv = (TextView) findViewById(R.id.update_dialog_title);
        messageTv = (TextView) findViewById(R.id.update_message);
        titleTv.setText("检测到新版本更新");

    }
    /**
     *  * 设置确定取消按钮的回调
     *  */
    public OnClickBottomListener onClickBottomListener;
    public UpdateDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    public interface OnClickBottomListener{

        void onMarketClick();

        void onNegativeClick();

        void onLinkClick();
    }

    public String getMessage() {
        return message;
    }

    public UpdateDialog setMessage(String message) {
        this.message = message;
        return this ;
    }



    @Override
    public void setCanceledOnTouchOutside(boolean cancel) {
        super.setCanceledOnTouchOutside(false);
    }
}
