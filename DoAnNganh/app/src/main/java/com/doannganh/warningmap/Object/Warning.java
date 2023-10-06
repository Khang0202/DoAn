package com.doannganh.warningmap.Object;

import java.time.LocalDateTime;
public class Warning {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    private int id;
    private User uploader;
    private Address address;
    private String linkImage;
    private LocalDateTime createdDateTime;
    private boolean active;
}
