package com.example.zhcs.bean;

import java.util.List;

public class LoveRecommendBean {

    private int code;
    private String msg;
    private int total;
    private List<RowsDTO> rows;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public class RowsDTO{

        private String createTime;
        private int id;
        private String imgUrl;
        private int typeId;
        private String name;
        private String author;
        private String activityAt;
        private int moneyTotal;
        private int moneyNow;
        private String isRecommend;
        private int donateCount;
        private LoveCateBean.DataDTO type;

        public String getCreateTime() {
            return createTime;
        }

        public int getDonateCount() {
            return donateCount;
        }

        public void setDonateCount(int donateCount) {
            this.donateCount = donateCount;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getActivityAt() {
            return activityAt;
        }

        public void setActivityAt(String activityAt) {
            this.activityAt = activityAt;
        }

        public int getMoneyTotal() {
            return moneyTotal;
        }

        public void setMoneyTotal(int moneyTotal) {
            this.moneyTotal = moneyTotal;
        }

        public int getMoneyNow() {
            return moneyNow;
        }

        public void setMoneyNow(int moneyNow) {
            this.moneyNow = moneyNow;
        }

        public String getIsRecommend() {
            return isRecommend;
        }

        public void setIsRecommend(String isRecommend) {
            this.isRecommend = isRecommend;
        }

        public LoveCateBean.DataDTO getType() {
            return type;
        }

        public void setType(LoveCateBean.DataDTO type) {
            this.type = type;
        }
    }

}


















