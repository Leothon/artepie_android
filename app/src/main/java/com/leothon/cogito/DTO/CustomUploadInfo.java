package com.leothon.cogito.DTO;

import com.leothon.cogito.Bean.CustomEnterprise;
import com.leothon.cogito.Bean.CustomHearing;
import com.leothon.cogito.Bean.CustomInstrument;
import com.leothon.cogito.Bean.CustomMood;
import com.leothon.cogito.Bean.CustomStyle;
import com.leothon.cogito.Bean.CustomUse;

public class CustomUploadInfo {

    private CustomEnterprise customEnterprise;
    private CustomMood customMood;
    private CustomHearing customHearing;
    private CustomStyle customStyle;
    private CustomInstrument customInstrument;
    private CustomUse customUse;

    public CustomEnterprise getCustomEnterprise() {
        return customEnterprise;
    }

    public void setCustomEnterprise(CustomEnterprise customEnterprise) {
        this.customEnterprise = customEnterprise;
    }

    public CustomHearing getCustomHearing() {
        return customHearing;
    }

    public void setCustomHearing(CustomHearing customHearing) {
        this.customHearing = customHearing;
    }

    public CustomInstrument getCustomInstrument() {
        return customInstrument;
    }

    public void setCustomInstrument(CustomInstrument customInstrument) {
        this.customInstrument = customInstrument;
    }

    public CustomMood getCustomMood() {
        return customMood;
    }

    public void setCustomMood(CustomMood customMood) {
        this.customMood = customMood;
    }

    public CustomStyle getCustomStyle() {
        return customStyle;
    }

    public void setCustomStyle(CustomStyle customStyle) {
        this.customStyle = customStyle;
    }

    public CustomUse getCustomUse() {
        return customUse;
    }

    public void setCustomUse(CustomUse customUse) {
        this.customUse = customUse;
    }


    @Override
    public String toString() {
        return "[{\"企业全称\":\"" + customEnterprise.getEnterpriseName() +  "\",\"企业所在地\":\"" + customEnterprise.getEnterpriseAddress() +  "\",\"从属行业\":\"" + customEnterprise.getEnterpriseIndustry() + "\",\"主营业务\":\"" + customEnterprise.getEnterpriseWork() + "\"},{\"选择用途\":\"" + customUse.getInfo1()  + " " + customUse.getInfo2() + ""+ customUse.getInfo3()  + ""+ customUse.getInfo4()  + ""+ customUse.getInfo5()  + ""+ customUse.getInfo6()  +  "\"},{\"作品情绪\":\"" + customMood.getInfo() + "\"},{\"作品听感\":\"" + customHearing.getInfo() + "\"},{\"作品风格\":\"" + customStyle.getInfo() + "\"},{\"乐器\":\"" + customInstrument.getInfo() + "\"}]";
    }
}
