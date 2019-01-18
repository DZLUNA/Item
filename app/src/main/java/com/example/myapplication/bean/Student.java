package com.example.myapplication.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 叟 on 2018/12/25.
 */
@Entity
public class Student {

    @Id(autoincrement = true)
    private Long id;

    private String name;
    private String img;
    private String page;
    @Generated(hash = 1099990839)
    public Student(Long id, String name, String img, String page) {
        this.id = id;
        this.name = name;
        this.img = img;
        this.page = page;
    }
    @Generated(hash = 1556870573)
    public Student() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getImg() {
        return this.img;
    }
    public void setImg(String img) {
        this.img = img;
    }
    public String getPage() {
        return this.page;
    }
    public void setPage(String page) {
        this.page = page;
    }
    
}
