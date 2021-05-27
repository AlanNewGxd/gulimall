package com.gxd.gulimall.search;

/**
 * @author guxiaodong
 * @version 1.0
 * @title TODO
 * @date 2021/5/26 16:47
 */
public class User {

    private  String userName;

    private  Integer age;

    private  String  gender;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
