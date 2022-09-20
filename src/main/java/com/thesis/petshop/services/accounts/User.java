package com.thesis.petshop.services.accounts;

import javax.persistence.*;

@Entity
@Table(name ="user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column (name = "id")
    private Long id;

    @Column( name = "name")
    private String name;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "type")
    private String type;

    @Column(name = "age")
    private String age;

    @Column(name = "address")
    private String address;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "social")
    private String social;

    @Column(name = "qualification_answers")
    private String qualificationAnswers;

    @Column(name = "qualification_passed")
    private Integer qualificationPassed;

    @Column(name = "is_user_valid")
    private Boolean isUserValid;

    @Column(name = "email_otp")
    private String emailOTP;

    @Column(name = "valid_id")
    private String validId;

    @Column(name = "is_email_valid")
    private Boolean isEmailValid;

    public User() { }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getQualificationAnswers() {
        return qualificationAnswers;
    }

    public void setQualificationAnswers(String qualificationAnswers) {
        this.qualificationAnswers = qualificationAnswers;
    }

    public Integer getQualificationPassed() {
        return qualificationPassed;
    }

    public void setQualificationPassed(Integer qualificationPassed) {
        this.qualificationPassed = qualificationPassed;
    }

    public Boolean getUserValid() {
        return isUserValid;
    }

    public void setUserValid(Boolean userValid) {
        isUserValid = userValid;
    }

    public String getEmailOTP() {
        return emailOTP;
    }

    public void setEmailOTP(String emailOTP) {
        this.emailOTP = emailOTP;
    }

    public String getValidId() {
        return validId;
    }

    public void setValidId(String validId) {
        this.validId = validId;
    }

    public Boolean getEmailValid() {
        return isEmailValid;
    }

    public void setEmailValid(Boolean emailValid) {
        isEmailValid = emailValid;
    }
}
