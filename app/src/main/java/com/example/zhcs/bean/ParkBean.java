package com.example.zhcs.bean;

import java.io.Serializable;
import java.util.List;

public class ParkBean implements Serializable {

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

    public class RowsDTO implements Serializable {
        private String address;
        private String allPark;
        private String distance;
        private int id;
        private String imgUrl;
        private String open;
        private String parkName;
        private String priceCaps;
        private String rates;
        private String vacancy;
        private String total;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAllPark() {
            return allPark;
        }

        public void setAllPark(String allPark) {
            this.allPark = allPark;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
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

        public String getOpen() {
            return open;
        }

        public void setOpen(String open) {
            this.open = open;
        }

        public String getParkName() {
            return parkName;
        }

        public void setParkName(String parkName) {
            this.parkName = parkName;
        }

        public String getPriceCaps() {
            return priceCaps;
        }

        public void setPriceCaps(String priceCaps) {
            this.priceCaps = priceCaps;
        }

        public String getRates() {
            return rates;
        }

        public void setRates(String rates) {
            this.rates = rates;
        }

        public String getVacancy() {
            return vacancy;
        }

        public void setVacancy(String vacancy) {
            this.vacancy = vacancy;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }
}






















