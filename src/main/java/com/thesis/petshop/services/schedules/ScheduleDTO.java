package com.thesis.petshop.services.schedules;

public class ScheduleDTO {

    private Long id;
    private Long userId;
    private String name;
    private String date;
    private String time;
    private String message;
    private String petCode;
    private String zoomLink;
    private String status;
    private Boolean hasProofPayment;

    public ScheduleDTO() { }

    public Boolean getHasProofPayment() {
        return hasProofPayment;
    }

    public void setHasProofPayment(Boolean hasProofPayment) {
        this.hasProofPayment = hasProofPayment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPetCode() {
        return petCode;
    }

    public void setPetCode(String petCode) {
        this.petCode = petCode;
    }

    public String getZoomLink() {
        return zoomLink;
    }

    public void setZoomLink(String zoomLink) {
        this.zoomLink = zoomLink;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
