package com.example.zhcs.bean;

import java.util.List;

public class LoveBannerBean {

    private String code;
    private String msg;
    private List<RowsDTO> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<RowsDTO> getData() {
        return data;
    }

    public void setData(List<RowsDTO> data) {
        this.data = data;
    }

    public class RowsDTO{
        private int hospitalId;
        private int id;
        private String imgUrl;

        public int getHospitalId() {
            return hospitalId;
        }

        public void setHospitalId(int hospitalId) {
            this.hospitalId = hospitalId;
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
    }
}
