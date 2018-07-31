package com.appsinventiv.corperstripadmin;

/**
 * Created by AliAh on 24/07/2018.
 */

public class GroupModel {
    String id,name,description,lastMessage,type,picUrl;
    boolean isActive;
    long time;



    public GroupModel() {
    }

    public GroupModel(String id, String name, String description, String lastMessage, String type, String picUrl, boolean isActive, long time) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.lastMessage = lastMessage;
        this.type = type;
        this.picUrl = picUrl;
        this.isActive = isActive;
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
