package com.example.work;

public class message {
    private String startname;
    private String endname;
    private String info;

    public String getStartname() {
        return startname;
    }

    public void setStartname(String startname) {
        this.startname = startname;
    }

    public String getEndname() {
        return endname;
    }

    public void setEndname(String endname) {
        this.endname = endname;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public message(String startname, String endname, String info) {
        this.startname = startname;
        this.endname = endname;
        this.info = info;
    }

    public message() {
    }

    @Override
    public String toString() {
        return "message{" +
                "startname='" + startname + '\'' +
                ", endname='" + endname + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
