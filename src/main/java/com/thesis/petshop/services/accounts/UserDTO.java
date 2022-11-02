package com.thesis.petshop.services.accounts;

import com.thesis.petshop.services.accounts.qualification.QualificationForm;
import com.thesis.petshop.services.adopt_form.answer.FormAnswer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

public class UserDTO {

    private Long id;
    private String name;
    private String mobile;
    private String username;
    private String password;
    private String email;
    private String type;
    private String age;
    private String address;
    private String occupation;
    private String social;

    private QualificationForm qualificationAnswers;
    private Integer qualificationFormScore;

    private Boolean isUserValid;

    public UserDTO() { }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public QualificationForm getQualificationAnswers() {
        return qualificationAnswers;
    }

    public void setQualificationAnswers(QualificationForm qualificationAnswers) {
        this.qualificationAnswers = qualificationAnswers;
    }

    public Integer getQualificationFormScore() {
        return qualificationFormScore;
    }

    public void setQualificationFormScore(Integer qualificationFormScore) {
        this.qualificationFormScore = qualificationFormScore;
    }

    public Boolean getUserValid() {
        return isUserValid;
    }

    public void setUserValid(Boolean userValid) {
        isUserValid = userValid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
