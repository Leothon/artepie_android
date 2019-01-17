package com.leothon.cogito.Bean;

import androidx.annotation.NonNull;

public class Banner {
    private String banner_id;
    private String banner_img;
    private String banner_url;
    private String banner_type;
    private String banner_to_class_id;

    public String getBanner_to_class_id() {
        return banner_to_class_id;
    }

    public void setBanner_to_class_id(String banner_to_class_id) {
        this.banner_to_class_id = banner_to_class_id;
    }

    public String getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }

    public String getBanner_img() {
        return banner_img;
    }

    public void setBanner_img(String banner_img) {
        this.banner_img = banner_img;
    }

    public String getBanner_url() {
        return banner_url;
    }

    public void setBanner_url(String banner_url) {
        this.banner_url = banner_url;
    }

    public String getBanner_type() {
        return banner_type;
    }

    public void setBanner_type(String banner_type) {
        this.banner_type = banner_type;
    }

    @NonNull
    @Override
    public String toString() {
        return "id" + banner_id + "img" + banner_img + "url" + banner_url + "type" + banner_type;
    }
}
