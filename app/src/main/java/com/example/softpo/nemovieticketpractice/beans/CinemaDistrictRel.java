package com.example.softpo.nemovieticketpractice.beans;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by home on 2016/6/26.
 */
@Table(name = "CinemaDistrictRel")
public class CinemaDistrictRel {

    /**
     * districtId : 1108
     * cinemaId : 9773
     */
    @Column(name = "districtId")
    private int districtId;
    @Column(name = "cinemaId",isId = true,autoGen = false)
    private int cinemaId;

    public int getDistrictId() {
        return districtId;
    }

    public void setDistrictId(int districtId) {
        this.districtId = districtId;
    }

    public int getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(int cinemaId) {
        this.cinemaId = cinemaId;
    }
}
