package com.example.softpo.nemovieticketpractice.beans;

import java.util.List;

/**
 * Created by home on 2016/6/28.
 */
public class HotMovie {

    /**
     * id : 45641
     * name : 惊天魔盗团2
     * highlight : 当哈利波特遇上周杰伦
     * grade : 7.0
     * logo : http://pimg1.126.net/movie/product/movie/142771430185513383_148_197_webp.jpg
     * isNew : 1
     * isSale : 0
     * isDiscount : 0
     * dimensional : 2D,3D
     * isSeatOccupy : 0
     * releaseDate : 2016-06-24
     * description : null
     * duration : 2小时10分钟
     * director : 朱浩伟
     * actors : 杰西·艾森伯格 伍迪·哈里森 艾拉·菲舍尔 戴夫·弗兰科 马克·鲁弗洛 迈克尔·凯恩 丹尼尔·雷德克里夫 周杰伦 丽兹·卡潘
     * logo1 : http://pimg1.126.net/movie/product/movie/142771430083313380_520_692_webp.jpg
     * logo2 : http://pimg1.126.net/movie/product/movie/142771430161013381_260_346_webp.jpg
     * logo3 : http://pimg1.126.net/movie/product/movie/142771430207013385_390_519_webp.jpg
     * logo556640 : http://pimg1.126.net/movie/product/movie/142771430246713387_640_556_webp.jpg
     * category : 喜剧/动作/惊悚
     * area : 美国
     * language : null
     * isHot : 0
     * preview : null
     * timeList : null
     * notifyCount : 5056
     * onShowStatus : 1
     * lowPrice : 24
     * logo180320 : null
     * logo520692 : http://pimg1.126.net/movie/product/movie/142771430083313380_520_692_webp.jpg
     * highBoxOffice : null
     * stillsList : null
     * mobilePreview : http://flv.bn.netease.com/videolib3/1606/06/LpuFK6325/HD/LpuFK6325-mobile.mp4
     * isAvailableInCurrentCity : true
     * isScheduleSupport : 1
     * screenings : 今天26家影院放映322场
     * spell : jingtianmodaotuan2
     * musicId : null
     */

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String id;
        private String name;
        private String highlight;
        private String grade;
        private String isNew;
        private String dimensional;
        private String releaseDate;
        private String duration;
        private String director;
        private String actors;
        private String logo3;
//        @com.google.gson.annotations.SerializedName("logo556640")
        private String logo556640;//logoTrailerCover
        private String category;
        private String area;
        private String isHot;
        private String lowPrice;
        private String mobilePreview;
        private String isAvailableInCurrentCity;
        private String isScheduleSupport;
        private String screenings;
        private String spell;

        private String logo;

        public String getLogo3() {
            return logo3;
        }

        public void setLogo3(String logo3) {
            this.logo3 = logo3;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHighlight() {
            return highlight;
        }

        public void setHighlight(String highlight) {
            this.highlight = highlight;
        }

        public String getGrade() {
            return grade;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        public String getIsNew() {
            return isNew;
        }

        public void setIsNew(String isNew) {
            this.isNew = isNew;
        }

        public String getDimensional() {
            return dimensional;
        }

        public void setDimensional(String dimensional) {
            this.dimensional = dimensional;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getActors() {
            return actors;
        }

        public void setActors(String actors) {
            this.actors = actors;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getLogo556640() {
            return logo556640;
        }

        public void setLogo556640(String logo556640) {
            this.logo556640 = logo556640;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getIsHot() {
            return isHot;
        }

        public void setIsHot(String isHot) {
            this.isHot = isHot;
        }

        public String getLowPrice() {
            return lowPrice;
        }

        public void setLowPrice(String lowPrice) {
            this.lowPrice = lowPrice;
        }

        public String getMobilePreview() {
            return mobilePreview;
        }

        public void setMobilePreview(String mobilePreview) {
            this.mobilePreview = mobilePreview;
        }

        public String getIsAvailableInCurrentCity() {
            return isAvailableInCurrentCity;
        }

        public void setIsAvailableInCurrentCity(String isAvailableInCurrentCity) {
            this.isAvailableInCurrentCity = isAvailableInCurrentCity;
        }

        public String getIsScheduleSupport() {
            return isScheduleSupport;
        }

        public void setIsScheduleSupport(String isScheduleSupport) {
            this.isScheduleSupport = isScheduleSupport;
        }

        public String getScreenings() {
            return screenings;
        }

        public void setScreenings(String screenings) {
            this.screenings = screenings;
        }

        public String getSpell() {
            return spell;
        }

        public void setSpell(String spell) {
            this.spell = spell;
        }
    }
}
