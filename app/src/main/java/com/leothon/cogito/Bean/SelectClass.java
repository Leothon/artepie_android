package com.leothon.cogito.Bean;

import android.support.annotation.NonNull;

import com.leothon.cogito.Http.BaseResponse;

import java.io.Serializable;
import java.util.ArrayList;



/**
 * created by leothon on 2018.8.7
 */
public class SelectClass  extends BaseResponse implements Serializable {

    private String selectId;
    private String selectbackimg;
    private String selectlisttitle;
    private String selectstucount;
    private String selecttime;
    private String selectauthor;
    private boolean isfav;
    private String selectdesc;
    private String selectprice;
    private boolean isbuy;
    private String selectauthordes;
    private String selectscore;
    private String selectauthorid;

    private String type;

    private boolean serialize;

    public boolean isSerialize() {
        return serialize;
    }

    public void setSerialize(boolean serialize) {
        this.serialize = serialize;
    }

    private boolean authorize;

    public boolean isAuthorize() {
        return authorize;
    }

    public void setAuthorize(boolean authorize) {
        this.authorize = authorize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    public String getSelectauthorid() {
        return selectauthorid;
    }

    public void setSelectauthorid(String selectauthorid) {
        this.selectauthorid = selectauthorid;
    }

    public String getSelectscore() {
        return selectscore;
    }

    public void setSelectscore(String selectscore) {
        this.selectscore = selectscore;
    }
    public String getSelectauthordes() {
        return selectauthordes;
    }

    public void setSelectauthordes(String selectauthordes) {
        this.selectauthordes = selectauthordes;
    }
    public boolean isIsbuy() {
        return isbuy;
    }

    public void setIsbuy(boolean isbuy) {
        this.isbuy = isbuy;
    }




    public String getSelectId() {
        return selectId;
    }

    public void setSelectId(String selectId) {
        this.selectId = selectId;
    }


    public String getSelectprice() {
        return selectprice;
    }

    public void setSelectprice(String selectprice) {
        this.selectprice = selectprice;
    }

    public String getSelectdesc() {
        return selectdesc;
    }

    public void setSelectdesc(String selectdesc) {
        this.selectdesc = selectdesc;
    }

    public String getSelectauthor() {
        return selectauthor;
    }

    public void setSelectauthor(String selectauthor) {
        this.selectauthor = selectauthor;
    }

    public String getSelectbackimg() {
        return selectbackimg;
    }

    public void setSelectbackimg(String selectbackimg) {
        this.selectbackimg = selectbackimg;
    }

    public String getSelectlisttitle() {
        return selectlisttitle;
    }

    public void setSelectlisttitle(String selectlisttitle) {
        this.selectlisttitle = selectlisttitle;
    }

    public String getSelectstucount() {
        return selectstucount;
    }

    public void setSelectstucount(String selectstucount) {
        this.selectstucount = selectstucount;
    }

    public String getSelecttime() {
        return selecttime;
    }

    public void setSelecttime(String selecttime) {
        this.selecttime = selecttime;
    }

    public boolean isIsfav() {
        return isfav;
    }

    public void setIsfav(boolean isfav) {
        this.isfav = isfav;
    }


    @NonNull
    @Override
    public String toString() {
        return selectId + selectbackimg + selectlisttitle + selectprice + selectstucount + selectauthor;
    }
}

