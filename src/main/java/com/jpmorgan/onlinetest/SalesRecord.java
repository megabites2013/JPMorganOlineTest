package com.jpmorgan.onlinetest;

public class SalesRecord {
    private String msgtype;
    private String pname;
    private double price;
    private double volume;
    private String adjust;

    public SalesRecord() {
    }

    public String printMe(){

        return new StringBuilder()
                .append("{")
                .append("\"msgtype\":\"").append(msgtype).append("\",")
                .append("\"pname\":\"").append(pname).append("\",")
                .append("\"price\":").append(price).append(",")
                .append("\"volume\":").append(volume).append(",")
                .append("\"adjust\":\"").append(adjust).append("\"")
                .append("}").toString();

    }

    public double getcost(){
        return price*volume;
    }


    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public String getAdjust() {
        return adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }
}
