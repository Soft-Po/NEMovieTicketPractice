package com.example.softpo.nemovieticketpractice.beans;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by home on 2016/6/27.
 */
@Table(name = "City")
public class City {

    /**
     * name : 海南
     * spell : hainan
     * code : 632500
     */
    @Column(name = "name")
    private String name;
    @Column(name = "spell")
    private String spell;
    @Column(name = "code", isId = true, autoGen = false)
    private int code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
