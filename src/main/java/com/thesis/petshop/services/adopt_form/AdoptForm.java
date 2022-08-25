package com.thesis.petshop.services.adopt_form;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name ="adopt_form")
public class AdoptForm {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column (name = "id")
    private Long id;

    @Column( name = "ts")
    private String ts = LocalDateTime.now().toString();

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "valid_id_url")
    private String validIdUrl;

    @Column(name = "answer")
    private String answer;

    @Column(name = "is_form_pass")
    private Boolean isFormPass;

    @Column(name = "pet_code")
    private String petCode;

    @Column(name = "status")
    private String status;

    @Column(name = "form_score")
    private Integer formScore;

    public AdoptForm() { }

    public Integer getFormScore() {
        return formScore;
    }

    public void setFormScore(Integer formScore) {
        this.formScore = formScore;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getValidIdUrl() {
        return validIdUrl;
    }

    public void setValidIdUrl(String validIdUrl) {
        this.validIdUrl = validIdUrl;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Boolean getFormPass() {
        return isFormPass;
    }

    public void setFormPass(Boolean formPass) {
        isFormPass = formPass;
    }

    public String getPetCode() {
        return petCode;
    }

    public void setPetCode(String petCode) {
        this.petCode = petCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
