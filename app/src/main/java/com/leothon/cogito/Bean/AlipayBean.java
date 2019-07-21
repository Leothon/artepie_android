package com.leothon.cogito.Bean;

public class AlipayBean {

    private String product_code;
    private String total_amount;
    private String subject;
    private String body;
    private String out_trade_no;


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }


    //"{product_code:QUICK_MSECURITY_PAY,total_amount:0.01,subject:1,body:我是测试数据,out_trade_no:}"



    private static final String CODE = "QUICK_MSECURITY_PAY";
    @Override
    public String toString() {
        return "{\"product_code\":\"" + CODE + "\",\"total_amount\":\"" + total_amount + "\",\"subject\":\"" + subject + "\",\"body\":\"" + body + "\",\"out_trade_no\":\"" + out_trade_no + "\"}";
    }
}
