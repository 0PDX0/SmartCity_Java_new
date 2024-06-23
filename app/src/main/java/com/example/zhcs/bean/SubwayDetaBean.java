package com.example.zhcs.bean;

import java.util.List;

public class SubwayDetaBean {

    private String msg;
    private int code;
    private DataDTO data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataDTO getData() {
        return data;
    }

    public void setData(DataDTO data) {
        this.data = data;
    }

    public class DataDTO{

        private int id;
        private String name;
        private String first;
        private String end;
        private String startTime;
        private String endTime;
        private int cityId;
        private int stationsNumber;
        private int km;
        private String runStationName;
        private List<MetroStep> metroStepList;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public int getStationsNumber() {
            return stationsNumber;
        }

        public void setStationsNumber(int stationsNumber) {
            this.stationsNumber = stationsNumber;
        }

        public int getKm() {
            return km;
        }

        public void setKm(int km) {
            this.km = km;
        }

        public String getRunStationName() {
            return runStationName;
        }

        public void setRunStationName(String runStationName) {
            this.runStationName = runStationName;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public List<MetroStep> getMetroStepList() {
            return metroStepList;
        }

        public void setMetroStepList(List<MetroStep> metroStepList) {
            this.metroStepList = metroStepList;
        }

        public class MetroStep{
            private int id;
            private String name;
            private int seq;
            private int lineId;
            private String firstCh;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getSeq() {
                return seq;
            }

            public void setSeq(int seq) {
                this.seq = seq;
            }

            public int getLineId() {
                return lineId;
            }

            public void setLineId(int lineId) {
                this.lineId = lineId;
            }

            public String getFirstCh() {
                return firstCh;
            }

            public void setFirstCh(String firstCh) {
                this.firstCh = firstCh;
            }
        }

    }
}
























