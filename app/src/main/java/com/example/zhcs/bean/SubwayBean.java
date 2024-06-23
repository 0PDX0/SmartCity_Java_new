package com.example.zhcs.bean;

import java.io.Serializable;
import java.util.List;

public class SubwayBean {

    private String msg;
    private int code;
    private List<DataDTO> data;

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

    public List<DataDTO> getData() {
        return data;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public class DataDTO implements Serializable {
        private int lineId;
        private String lineName;
        private Step preStep;
        private Step nextStep;
        private String currentName;
        private int reachTime;

        public int getLineId() {
            return lineId;
        }

        public void setLineId(int lineId) {
            this.lineId = lineId;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public Step getPreStep() {
            return preStep;
        }

        public void setPreStep(Step preStep) {
            this.preStep = preStep;
        }

        public Step getNextStep() {
            return nextStep;
        }

        public void setNextStep(Step nextStep) {
            this.nextStep = nextStep;
        }

        public String getCurrentName() {
            return currentName;
        }

        public void setCurrentName(String currentName) {
            this.currentName = currentName;
        }

        public int getReachTime() {
            return reachTime;
        }

        public void setReachTime(int reachTime) {
            this.reachTime = reachTime;
        }

        public class Step implements Serializable{

            private String name;
            private List<SubBean> lines;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<SubBean> getLines() {
                return lines;
            }

            public void setLines(List<SubBean> lines) {
                this.lines = lines;
            }
        }



    }


}
