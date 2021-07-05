package com.example.lib_resource.bean;

public class CustomListItem {
    private String id;
    private String number;
    private String name;
    private String title;
    private String message;

    public CustomListItem(String id, String number, String name, String title, String message) {
        this.id = id;
        this.number = number;
        this.name = name;
        this.title = title;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
