package com.cqupt.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "warning_info", schema = "dbw", catalog = "")
public class WarningInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "id")
    private String id;

    private String driver_id;

    @Basic
    @Column(name = "time")
    private String time;

    @Basic
    @Column(name = "grade")
    private String grade;

    @Basic
    @Column(name = "head")
    private String head;

    @Basic
    @Column(name = "fatigue")
    private String fatigue;

    @Basic
    @Column(name = "account")
    private String account;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getDriverId() {
        return driver_id;
    }

    public void setDriverId(String driver_id) {
        this.driver_id = driver_id;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getGrade() {
        return grade;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }


    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }


    public String getFatigue() {
        return fatigue;
    }

    public void setFatigue(String fatigue) {
        this.fatigue = fatigue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WarningInfo that = (WarningInfo) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (time != null ? !time.equals(that.time) : that.time != null) return false;
        if (grade != null ? !grade.equals(that.grade) : that.grade != null) return false;
        if (head != null ? !head.equals(that.head) : that.head != null) return false;
        if (fatigue != null ? !fatigue.equals(that.fatigue) : that.fatigue != null) return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (grade != null ? grade.hashCode() : 0);
        result = 31 * result + (head != null ? head.hashCode() : 0);
        result = 31 * result + (fatigue != null ? fatigue.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WarningInfo{" +
                "id='" + id + '\'' +
                ", driver_id='" + driver_id + '\'' +
                ", time='" + time + '\'' +
                ", grade='" + grade + '\'' +
                ", head='" + head + '\'' +
                ", fatigue='" + fatigue + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
