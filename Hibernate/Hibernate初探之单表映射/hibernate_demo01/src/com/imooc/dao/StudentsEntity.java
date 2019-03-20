package com.imooc.dao;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * @Author zhangxiaoliang98
 * @Date 3/20/19 8:56 AM
 * @Description
 */

/*
    Java Bean 设计原则

    1.公有的类
    2.提供公有的不带参数的默认构造方法
    3.属性私有
    4.属性 serrer/getter 封装
*/
@Entity
@Table(name = "students", schema = "hibernate_imooc", catalog = "")
public class StudentsEntity {
    private int sid;
    private String sname;
    private String gender;
    private Date birthday;
    private String address;

    public StudentsEntity() {
    }

    public StudentsEntity(String sname, String gender, Date birthday, String address) {
        this.sname = sname;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
    }

    public StudentsEntity(int sid, String sname, String gender, Date birthday, String address) {
        this.sid = sid;
        this.sname = sname;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
    }

    @Id
    @Column(name = "sid")
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    @Basic
    @Column(name = "sname")
    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Basic
    @Column(name = "gender")
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentsEntity that = (StudentsEntity) o;
        return sid == that.sid &&
                Objects.equals(sname, that.sname) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(birthday, that.birthday) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sid, sname, gender, birthday, address);
    }

    @Override
    public String toString() {
        return "StudentsEntity{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                '}';
    }
}
