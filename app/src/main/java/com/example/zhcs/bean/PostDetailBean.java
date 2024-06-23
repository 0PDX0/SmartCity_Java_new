package com.example.zhcs.bean;

public class PostDetailBean {

    private int code;
    private String msg;
    private DataDTO data;

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

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public class DataDTO{

        private int id;
        private String coverImgUrl;
        private String imageUrls;
        private String bedsCountBoy;
        private String bedsCountGirl;
        private String address;
        private String phone;
        private String workTime;
        private String introduce;
        private String internalFacilities;
        private String surroundingFacilities;
        private String specialService;

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
            return imageUrls;
        }

        public void setImageUrlList(String imageUrls) {
            this.imageUrls = imageUrls;
        }

        public String getBedsCountBoy() {
            return bedsCountBoy;
        }

        public void setBedsCountBoy(String bedsCountBoy) {
            this.bedsCountBoy = bedsCountBoy;
        }

        public String getBedsCountGirl() {
            return bedsCountGirl;
        }

        public void setBedsCountGirl(String bedsCountGirl) {
            this.bedsCountGirl = bedsCountGirl;
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

        public String getImageUrls() {
            return imageUrls;
        }

        public void setImageUrls(String imageUrls) {
            this.imageUrls = imageUrls;
        }

        public String getIntroduce() {
            return introduce;
        }

        public void setIntroduce(String introduce) {
            this.introduce = introduce;
        }

        public void setSpecialService(String specialService) {
            this.specialService = specialService;
        }
    }

}
