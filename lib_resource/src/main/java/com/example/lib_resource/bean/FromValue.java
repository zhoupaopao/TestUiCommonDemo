package com.example.lib_resource.bean;

public class FromValue {
    private String id;// 选项ID
    private String value;// 选项值
    private String other;// 选项其他参数 拓展可用，这儿可以直接换为泛型哦，这样就可以把我们需要的参数也传入进来

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public FromValue(String id, String value) {
        this.id = id;
        this.value = value;
    }
}
