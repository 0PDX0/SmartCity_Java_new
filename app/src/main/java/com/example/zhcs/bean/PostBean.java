package com.example.zhcs.bean;

import java.util.List;

public class PostBean {

    private String code;
    private String msg;
    private List<RowsDTO> rows;

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

    public List<RowsDTO> getRows() {
        return rows;
    }

    public void setRows(List<RowsDTO> rows) {
        this.rows = rows;
    }

    public class RowsDTO{

        private int id;
        private String name;
        private String coverImgUrl;
        private String imageUrlList;
        private int bedsCountBoy;
        private int bendCountGirl;
        private String address;
        private String phone;
        private String workTime;
        private String introduce;
        private String internalFacilities;
        private String surroundingFacilities;
        private String specialService;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCoverImgUrl() {
            return coverImgUrl;
        }

        public void setCoverImgUrl(String coverImgUrl) {
            this.coverImgUrl = coverImgUrl;
        }

        public String getImageUrlList() {
            return imageUrlList;
        }

        public void setImageUrlList(String imageUrlList) {
            this.imageUrlList = imageUrlList;
        }

        public int getBedsCountBoy() {
            return bedsCountBoy;
        }

        public void setBedsCountBoy(int bedsCountBoy) {
            this.bedsCountBoy = bedsCountBoy;
        }

        public int getBendCountGirl() {
            return bendCountGirl;
        }

        public void setBendCountGirl(int bendCountGirl) {
            this.bendCountGirl = bendCountGirl;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getWorkTime() {
            return workTime;
        }

        public void setWorkTime(String workTime) {
            this.workTime = workTime;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public String getInternalFacilities() {
            return internalFacilities;
        }

        public void setInternalFacilities(String internalFacilities) {
            this.internalFacilities = internalFacilities;
        }

        public String getSurroundingFacilities() {
            return surroundingFacilities;
        }

        public void setSurroundingFacilities(String surroundingFacilities) {
            this.surroundingFacilities = surroundingFacilities;
        }

        public String getSpecialService() {
            return specialService;
        }

        public void setSpecialService(String specialService) {
            this.specialService = specialService;
        }
    }

}
