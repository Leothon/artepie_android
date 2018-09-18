package com.leothon.cogito.Bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.leothon.cogito.Http.BaseResponse;

import java.io.Serializable;

/**
 * created by leothon on 9/18/2018.
 */
public class DetailClassInfo extends BaseResponse  implements Serializable {
    private String descClass;
    private String descAuthor;
    private String firstImg;
    private String firstName;
    private String firstContent;
    private String firstLike;
    private String secondImg;
    private String secondName;
    private String secondContent;
    private String secondLike;
    private String thirdImg;
    private String thirdName;
    private String thirdContent;
    private String thirdLike;


    public String getDescClass() {
        return descClass;
    }

    public void setDescClass(String descClass) {
        this.descClass = descClass;
    }

    public String getDescAuthor() {
        return descAuthor;
    }

    public void setDescAuthor(String descAuthor) {
        this.descAuthor = descAuthor;
    }

    public String getFirstContent() {
        return firstContent;
    }

    public void setFirstContent(String firstContent) {
        this.firstContent = firstContent;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getFirstLike() {
        return firstLike;
    }

    public void setFirstLike(String firstLike) {
        this.firstLike = firstLike;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondContent() {
        return secondContent;
    }

    public void setSecondContent(String secondContent) {
        this.secondContent = secondContent;
    }

    public String getSecondImg() {
        return secondImg;
    }

    public void setSecondImg(String secondImg) {
        this.secondImg = secondImg;
    }

    public String getSecondLike() {
        return secondLike;
    }

    public void setSecondLike(String secondLike) {
        this.secondLike = secondLike;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getThirdContent() {
        return thirdContent;
    }

    public void setThirdContent(String thirdContent) {
        this.thirdContent = thirdContent;
    }

    public String getThirdImg() {
        return thirdImg;
    }

    public void setThirdImg(String thirdImg) {
        this.thirdImg = thirdImg;
    }

    public String getThirdLike() {
        return thirdLike;
    }

    public void setThirdLike(String thirdLike) {
        this.thirdLike = thirdLike;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }
}
