package com.thesis.petshop.services.adopt_form;

import com.thesis.petshop.services.adopt_form.answer.FormAnswer;

public class AdoptFormDTO {

    private Long id;
    private Long userId;
    private String name;
    private String timestamp;
    private String validIdUrl;
    private FormAnswer formAnswer;
    private String petCode;
    private String petName;
    private String status;
    private String type;
    private Boolean hasProofOwnerShip;

    private int formScore;
    private String formResult;

    public AdoptFormDTO() { }


    public String getFormResult() {
        return formResult;
    }

    public void setFormResult(String formResult) {
        this.formResult = formResult;
    }

    public int getFormScore() {
        return formScore;
    }

    public void setFormScore(int formScore) {
        this.formScore = formScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getValidIdUrl() {
        return validIdUrl;
    }

    public void setValidIdUrl(String validIdUrl) {
        this.validIdUrl = validIdUrl;
    }

    public FormAnswer getFormAnswer() {
        return formAnswer;
    }

    public void setFormAnswer(FormAnswer formAnswer) {
        this.formAnswer = formAnswer;
    }

    public String getPetCode() {
        return petCode;
    }

    public void setPetCode(String petCode) {
        this.petCode = petCode;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getHasProofOwnerShip() {
        return hasProofOwnerShip;
    }

    public void setHasProofOwnerShip(Boolean hasProofOwnerShip) {
        this.hasProofOwnerShip = hasProofOwnerShip;
    }
}
