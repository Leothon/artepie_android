package com.leothon.cogito.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leothon.cogito.R;
import com.leothon.cogito.Utils.SharePreferenceManager;

import cn.jiguang.imui.chatinput.ChatInputView;
import cn.jiguang.imui.chatinput.listener.OnCameraCallbackListener;
import cn.jiguang.imui.chatinput.listener.OnMenuClickListener;
import cn.jiguang.imui.chatinput.listener.RecordVoiceListener;
import cn.jiguang.imui.chatinput.record.RecordVoiceButton;
import cn.jiguang.imui.messages.MessageList;
import cn.jiguang.imui.messages.MsgListAdapter;

import static cn.jiguang.imui.chatinput.ChatInputView.KEYBOARD_STATE_HIDE;
import static cn.jiguang.imui.chatinput.ChatInputView.KEYBOARD_STATE_INIT;
import static cn.jiguang.imui.chatinput.ChatInputView.KEYBOARD_STATE_SHOW;

public class ChatView extends RelativeLayout {

    private Context mContext;
//    private TextView mTitle;
    private MessageList mMsgList;
    private ChatInputView mChatInput;
    private RecordVoiceButton mRecordVoiceBtn;
    private boolean mHasInit;
    private int mHeight;
    private boolean mHasKeyboard;
    private OnKeyboardChangedListener mKeyboardListener;
    private OnSizeChangedListener mSizeChangedListener;

    public ChatView(Context context) {
        super(context);
        mContext = context;
    }

    public ChatView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ChatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void initModule() {
//        mTitle = (TextView) findViewById(R.id.title_tv);
        mMsgList = (MessageList) findViewById(R.id.msg_list);
        mChatInput = (ChatInputView) findViewById(R.id.chat_input);
        mRecordVoiceBtn = mChatInput.getRecordVoiceButton();
        Log.i("ChatView", "setting softKeyboard height " + SharePreferenceManager.getCachedKeyboardHeight());
        mChatInput.setMenuContainerHeight(SharePreferenceManager.getCachedKeyboardHeight());
    }

    public void setTitle(String title) {
//        mTitle.setText(title);

    }

    public void setMenuClickListener(OnMenuClickListener listener) {
        mChatInput.setMenuClickListener(listener);
    }

    public void setAdapter(MsgListAdapter adapter) {
        mMsgList.setAdapter(adapter);
    }

    public void setRecordVoiceFile(String path, String fileName) {
        mRecordVoiceBtn.setVoiceFilePath(path, fileName);
    }

    public void setCameraCaptureFile(String path, String fileName) {
        mChatInput.setCameraCaptureFile(path, fileName);
    }

    public void setRecordVoiceListener(RecordVoiceListener listener) {
        mRecordVoiceBtn.setRecordVoiceListener(listener);
    }

    public void setOnCameraCallbackListener(OnCameraCallbackListener listener) {
        mChatInput.setOnCameraCallbackListener(listener);
    }

    public void setKeyboardChangedListener(OnKeyboardChangedListener listener) {
        mKeyboardListener = listener;
    }

    public void setOnSizeChangedListener(OnSizeChangedListener listener) {
        mSizeChangedListener = listener;
    }

    public void setOnTouchListener(OnTouchListener listener) {
        mMsgList.setOnTouchListener(listener);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mSizeChangedListener != null){
            mSizeChangedListener.onSizeChanged(w, h, oldw, oldh);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (!mHasInit) {
            mHasInit = true;
            mHeight = b;
            if (null != mKeyboardListener) {
                mKeyboardListener.onKeyBoardStateChanged(KEYBOARD_STATE_INIT);
            }
        } else {
            if (null != mKeyboardListener) {
                mKeyboardListener.onKeyBoardStateChanged(KEYBOARD_STATE_INIT);
            }
            mHeight = mHeight < b ? b : mHeight;
        }
        if (mHasInit && mHeight > b) {
            mHasKeyboard = true;
            if (null != mKeyboardListener) {
                mKeyboardListener.onKeyBoardStateChanged(KEYBOARD_STATE_SHOW);
            }
        }
        if (mHasInit && mHasKeyboard && mHeight == b) {
            mHasKeyboard = false;
            if (null != mKeyboardListener) {
                mKeyboardListener.onKeyBoardStateChanged(KEYBOARD_STATE_HIDE);
            }
        }
    }

    public ChatInputView getChatInputView() {
        return mChatInput;
    }

    public void setMenuHeight(int height) {
        mChatInput.setMenuContainerHeight(height);
    }

    /**
     * Keyboard status changed will invoke onKeyBoardStateChanged
     */
    public interface OnKeyboardChangedListener {

        /**
         * Soft keyboard status changed will invoke this callback, use this callback to do you logic.
         * @param state Three states: init, show, hide.
         */
        public void onKeyBoardStateChanged(int state);
    }

    public interface OnSizeChangedListener {
        void onSizeChanged(int w, int h, int oldw, int oldh);
    }

}
