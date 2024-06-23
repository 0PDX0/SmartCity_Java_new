package com.example.zhcs.bean;

import java.io.Serializable;
import java.util.List;

public class AppealBean {

    private int code;
    private String msg;

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

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public class RowsDTO implements Comparable<RowsDTO>,Serializable {

        private int id;
        private String content;
        private int undertaker;
        private String imgUrl;
        private String appealCategoryName;
        private String state;
        private String createTime;

        public String getCreateTime() {
            return createTime;
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

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getUndertaker() {
            return undertaker;
        }

        public void setUndertaker(int undertaker) {
            this.undertaker = undertaker;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getAppealCategoryName() {
            return appealCategoryName;
        }

        public void setAppealCategoryName(String appealCategoryName) {
            this.appealCategoryName = appealCategoryName;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        @Override
        public int compareTo(RowsDTO o) {
            return this.getState().compareTo(o.getState());
        }
    }
}


























