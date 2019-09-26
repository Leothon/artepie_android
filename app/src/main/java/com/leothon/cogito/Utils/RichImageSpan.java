package com.leothon.cogito.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.style.ImageSpan;

public class RichImageSpan extends ImageSpan {
    private String url;
    public RichImageSpan(Context context, Bitmap b, String url) {
        super(context, b);
        this.url = url;
    }

    @Override
    public String getSource() {
        return url.toString();
    }
}
