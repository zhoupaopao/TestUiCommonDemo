package com.example.lib_resource.bean;

import java.io.Serializable;

public class ExpListenerBean extends BaseBean implements Serializable {
    private String name;
    private ExpChangeBean change;

    public ExpListenerBean(String name, ExpChangeBean change) {
        this.name = name;
        this.change = change;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExpChangeBean getChange() {
        return change;
    }

    public void setChange(ExpChangeBean change) {
        this.change = change;
    }

    public static class ExpChangeBean{
        private String type;
        private ExpParamsBean params;

        public ExpChangeBean(String type, ExpParamsBean params) {
            this.type = type;
            this.params = params;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ExpParamsBean getParams() {
            return params;
        }

        public void setParams(ExpParamsBean params) {
            this.params = params;
        }

        public static class  ExpParamsBean{
            private String toId;
            private String value;

            public  ExpParamsBean(String toId, String value) {
                this.toId = toId;
                this.value = value;
            }

            public String getToId() {
                return toId;
            }

            public void setToId(String toId) {
                this.toId = toId;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
