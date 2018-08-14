package com.leothon.cogito.Weight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;



import java.util.List;

/**
 * created by leothon on 2018.8.3
 */
public class SpinnerPopupwindow extends PopupWindow {

    private View contentView;
    private ListView listView;
    private Activity context;
    private TextView popTitle;
    private TextView popCancel;
    @SuppressLint("InflateParams")
    public SpinnerPopupwindow(final Activity context, final String string, final List<String> list, AdapterView.OnItemClickListener onItemClickListener){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
    }
}
