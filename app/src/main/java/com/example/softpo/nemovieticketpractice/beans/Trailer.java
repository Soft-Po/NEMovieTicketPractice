package com.example.softpo.nemovieticketpractice.beans;

import java.util.List;

/**
 * Created by home on 2016/6/29.
 */
public class Trailer {

    /**
     * id : 47161
     * name : 所以……和黑粉结婚了
     * highlight : 爆猛料，你们的秘密我全都知道
     * grade : 7.0
     * logo : http://pimg1.126.net/movie/product/movie/146519836950510124_148_197_webp.webp
     * isNew : 0
     * isSale : 1
     * isDiscount : 0
     * dimensional : 2D
     * isSeatOccupy : 0
     * releaseDate : 2016-06-30
     * description : null
     * duration : 1小时30分钟
     * director : 金帝荣
     * actors : 朴灿烈 袁姗姗 姜潮 徐珠贤
     * logo1 : http://pimg1.126.net/movie/product/movie/146519836892110121_520_692_webp.webp
     * logo2 : http://pimg1.126.net/movie/product/movie/146519836915310122_260_346_webp.webp
     * logo3 : http://pimg1.126.net/movie/product/movie/146519836983810126_390_519_webp.webp
     * logo556640 : http://pimg1.126.net/movie/product/movie/146519837029910128_webp.webp
     * category : 喜剧/爱情
     * area : 中国大陆
     * language : null
     * isHot : 0
     * preview : null
     * timeList : null
     * notifyCount : 642
     * onShowStatus : 1
     * lowPrice : 27
     * logo180320 : null
     * logo520692 : http://pimg1.126.net/movie/product/movie/146519836687010119_webp.jpg
     * highBoxOffice : null
     * stillsList : null
     * mobilePreview : http://flv.bn.netease.com/videolib3/1606/06/YtBmD8035/HD/YtBmD8035-mobile.mp4
     * isAvailableInCurrentCity : true
     * isScheduleSupport : 1
     * screenings : 下周四上映
     * spell : suoyi……heheifenjiehunliao
     * musicId : null
     */

    private List<TrailerItem> list;
    /**
     * id : 45533
     * name : 神秘家族
     * highlight : 人妻林依晨婚后首部作品
     * grade : 7.0
     * logo : http://pimg1.126.net/movie/product/movie/143071131865510242_148_197_webp.jpg
     * isNew : 0
     * isSale : 0
     * isDiscount : 0
     * dimensional : 2D
     * isSeatOccupy : 0
     * releaseDate : 2016-07-01
     * description : null
     * duration : 2小时2分钟
     * director : 朴裕焕
     * actors : 林依晨 陈晓 惠英红 姜武 安琥 蓝正龙
     * logo1 : http://pimg1.126.net/movie/product/movie/143071131775610239_520_692_webp.jpg
     * logo2 : http://pimg1.126.net/movie/product/movie/143071131840910240_260_346_webp.jpg
     * logo3 : http://pimg1.126.net/movie/product/movie/143071131884210244_390_519_webp.jpg
     * logo556640 : http://pimg1.126.net/movie/product/movie/142560804092811060_640_556_webp.jpg
     * category : 剧情/惊悚
     * area : 中国大陆 韩国
     * language : null
     * isHot : 0
     * preview : null
     * timeList : null
     * notifyCount : 3390
     * onShowStatus : 0
     * lowPrice : null
     * logo180320 : http://pimg1.126.net/movie/product/movie/143071131914010246_320_180_webp.jpg
     * logo520692 : http://pimg1.126.net/movie/product/movie/143071131775610239_520_692_webp.jpg
     * highBoxOffice : null
     * stillsList : null
     * mobilePreview : http://flv.bn.netease.com/videolib3/1505/04/EyWMR1365/SD/EyWMR1365-mobile.mp4
     * isAvailableInCurrentCity : false
     * isScheduleSupport : 0
     * screenings : 下周五上映
     * spell : shenmijiazu
     * musicId : null
     */

    private List<TrailerItem> maxNotifyList;

    public List<TrailerItem> getList() {
        return list;
    }

    public void setList(List<TrailerItem> list) {
        this.list = list;
    }

    public List<TrailerItem> getMaxNotifyList() {
        return maxNotifyList;
    }

    public void setMaxNotifyList(List<TrailerItem> maxNotifyList) {
        this.maxNotifyList = maxNotifyList;
    }

    public static class TrailerItem {
        private String id;
        private String name;
        private String highlight;
        private String grade;
        private String logo;
        private String isNew;
        private String isSale;
        private String isDiscount;
        private String dimensional;
        private int isSeatOccupy;
        private String releaseDate;
        private Object description;
        private String duration;
        private String director;
        private String actors;
        private String logo3;
        private String logo556640;
        private String category;
        private String area;
        private Object language;
        private String isHot;
        private Object preview;
        private Object timeList;
        private String notifyCount;
        private String onShowStatus;
        private String lowPrice;
        private String logo520692;
        private Object highBoxOffice;
        private Object stillsList;
        private String mobilePreview;
        private String isAvailableInCurrentCity;
        private String isScheduleSupport;
        private String screenings;
        private String spell;
        private Object musicId;

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

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public String getIsNew() {
            return isNew;
        }

        public void setIsNew(String isNew) {
            this.isNew = isNew;
        }

        public String getIsSale() {
            return isSale;
        }

        public void setIsSale(String isSale) {
            this.isSale = isSale;
        }

        public String getIsDiscount() {
            return isDiscount;
        }

        public void setIsDiscount(String isDiscount) {
            this.isDiscount = isDiscount;
        }

        public String getDimensional() {
            return dimensional;
        }

        public void setDimensional(String dimensional) {
            this.dimensional = dimensional;
        }

        public int getIsSeatOccupy() {
            return isSeatOccupy;
        }

        public void setIsSeatOccupy(int isSeatOccupy) {
            this.isSeatOccupy = isSeatOccupy;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public Object getDescription() {
            return description;
        }

        public void setDescription(Object description) {
            this.description = description;
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

        public String getLogo3() {
            return logo3;
        }

        public void setLogo3(String logo3) {
            this.logo3 = logo3;
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

        public Object getLanguage() {
            return language;
        }

        public void setLanguage(Object language) {
            this.language = language;
        }

        public String getIsHot() {
            return isHot;
        }

        public void setIsHot(String isHot) {
            this.isHot = isHot;
        }

        public Object getPreview() {
            return preview;
        }

        public void setPreview(Object preview) {
            this.preview = preview;
        }

        public Object getTimeList() {
            return timeList;
        }

        public void setTimeList(Object timeList) {
            this.timeList = timeList;
        }

        public String getNotifyCount() {
            return notifyCount;
        }

        public void setNotifyCount(String notifyCount) {
            this.notifyCount = notifyCount;
        }

        public String getOnShowStatus() {
            return onShowStatus;
        }

        public void setOnShowStatus(String onShowStatus) {
            this.onShowStatus = onShowStatus;
        }

        public String getLowPrice() {
            return lowPrice;
        }

        public void setLowPrice(String lowPrice) {
            this.lowPrice = lowPrice;
        }

        public String getLogo520692() {
            return logo520692;
        }

        public void setLogo520692(String logo520692) {
            this.logo520692 = logo520692;
        }

        public Object getHighBoxOffice() {
            return highBoxOffice;
        }

        public void setHighBoxOffice(Object highBoxOffice) {
            this.highBoxOffice = highBoxOffice;
        }

        public Object getStillsList() {
            return stillsList;
        }

        public void setStillsList(Object stillsList) {
            this.stillsList = stillsList;
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

        public Object getMusicId() {
            return musicId;
        }

        public void setMusicId(Object musicId) {
            this.musicId = musicId;
        }
    }
}
