package com.example.softpo.nemovieticketpractice.beans;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by home on 2016/6/26.
 */
@Table(name = "District")
public class District {

    /**
     * id : 2791
     * name : 黄岛区
     */
    @Column(name = "id", isId = true, autoGen = false)
    private int id;
    @Column(name = "name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
