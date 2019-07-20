package com.leothon.cogito.DTO;

public class Orders {

    private String order_id;
    private String order_number;
    private String order_user_id;
    private String order_date;
    private String order_class_id;
    private String order_pay_type;
    private String order_class_price;
    private String order_discount;
    private String order_end_price;
    private String order_status;

    private String local_ip;

    public String getLocal_ip() {
        return local_ip;
    }

    public void setLocal_ip(String local_ip) {
        this.local_ip = local_ip;
    }


    private String payInfo;

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    private Merchandise merchandise;

    public Merchandise getMerchandise() {
        return merchandise;
    }

    public void setMerchandise(Merchandise merchandise) {
        this.merchandise = merchandise;
    }

    public String getOrder_class_id() {
        return order_class_id;
    }

    public void setOrder_class_id(String order_class_id) {
        this.order_class_id = order_class_id;
    }

    public void setOrder_class_price(String order_class_price) {
        this.order_class_price = order_class_price;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public void setOrder_discount(String order_discount) {
        this.order_discount = order_discount;
    }

    public void setOrder_end_price(String order_end_price) {
        this.order_end_price = order_end_price;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public void setOrder_pay_type(String order_pay_type) {
        this.order_pay_type = order_pay_type;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public void setOrder_user_id(String order_user_id) {
        this.order_user_id = order_user_id;
    }

    public String getOrder_class_price() {
        return order_class_price;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getOrder_discount() {
        return order_discount;
    }

    public String getOrder_end_price() {
        return order_end_price;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public String getOrder_pay_type() {
        return order_pay_type;
    }

    public String getOrder_status() {
        return order_status;
    }

    public String getOrder_user_id() {
        return order_user_id;
    }


    @Override
    public String toString() {
        return "订单ID" + order_id +
                "订单号" + order_number +
                "购买者ID" + order_user_id +
                "订单日期" + order_date +
                "订单课程编号" + order_class_id  +
                "订单支付方式" + order_pay_type +
                "订单价格" + order_class_price +
                "订单折扣" + order_discount +
                "订单最终价格" + order_end_price +
                "订单状态" + order_status +
                "订单信息" +  payInfo +
                "商品信息" + merchandise +
                "本地IP为" + local_ip;
    }
}
