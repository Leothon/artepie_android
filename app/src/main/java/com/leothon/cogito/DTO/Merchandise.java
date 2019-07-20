package com.leothon.cogito.DTO;

public class Merchandise {
    private String Me_Id;
    private String Me_Name;
    private String Me_Author;
    private String ME_Des;
    private String ME_Create_Time;


    public String getMe_Id() {
        return Me_Id;
    }

    public void setMe_Id(String me_Id) {
        Me_Id = me_Id;
    }


    public String getMe_Author() {
        return Me_Author;
    }

    public void setMe_Author(String me_Author) {
        Me_Author = me_Author;
    }

    public void setME_Create_Time(String ME_Create_Time) {
        this.ME_Create_Time = ME_Create_Time;
    }

    public void setME_Des(String ME_Des) {
        this.ME_Des = ME_Des;
    }

    public void setMe_Name(String me_Name) {
        Me_Name = me_Name;
    }

    public String getME_Create_Time() {
        return ME_Create_Time;
    }

    public String getME_Des() {
        return ME_Des;
    }

    public String getMe_Name() {
        return Me_Name;
    }

    @Override
    public String toString() {
        return "商品ID" + Me_Id + "商品名字" + Me_Name + "商品归属者" + Me_Author + "商品描述" + ME_Des + "商品创建时间" + ME_Create_Time;
    }
}
