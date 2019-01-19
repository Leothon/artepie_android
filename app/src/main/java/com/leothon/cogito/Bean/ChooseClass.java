package com.leothon.cogito.Bean;

import com.leothon.cogito.Http.BaseResponse;

/**
 * created by leothon on 9/13/2018.
 */
public class ChooseClass extends BaseResponse {

    private int position;
    private boolean isLocked;

    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }
    public boolean getLocked(){
        return isLocked;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


}
