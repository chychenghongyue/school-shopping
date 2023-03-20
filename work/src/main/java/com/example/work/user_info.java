package com.example.work;

import java.util.Arrays;

public class user_info{
    private String  age;
    private String sex;
    private int[] hobby;
    private String location_1;
    private String location_2;

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int[] getHobby() {
        return hobby;
    }

    public void setHobby(int[] hobby) {
        this.hobby = hobby;
    }

    public String getLocation_1() {
        return location_1;
    }

    public void setLocation_1(String location_1) {
        this.location_1 = location_1;
    }

    public String getLocation_2() {
        return location_2;
    }

    public void setLocation_2(String location_2) {
        this.location_2 = location_2;
    }

    public user_info(String age, String sex, int[] hobby, String location_1, String location_2) {
        this.age = age;
        this.sex = sex;
        this.hobby = hobby;
        this.location_1 = location_1;
        this.location_2 = location_2;
    }

    public user_info() {
    }

    @Override
    public String toString() {
        return "user_info{" +
                "age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", hobby=" + Arrays.toString(hobby) +
                ", location_1='" + location_1 + '\'' +
                ", location_2='" + location_2 + '\'' +
                '}';
    }
}
