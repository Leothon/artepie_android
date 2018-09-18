package com.leothon.cogito.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.leothon.cogito.Http.BaseResponse;

import java.io.Serializable;

/**
 * created by leothon on 9/18/2018.
 */
public class DetailClass extends BaseResponse implements Serializable {

    private String title;
    private String count;

    private boolean isLocked;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
    public boolean getLocked(){
        return isLocked;
    }



    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }


}
