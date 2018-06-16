package com.amogh.lms.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A AadharEnrollForm.
 */
@Entity
@Table(name = "aadhar_enroll_form")
public class AadharEnrollForm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pre_enrollment_id")
    private String preEnrollmentID;

    @Column(name = "tin_number")
    private String tinNumber;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private String gender;

    @Column(name = "age")
    private String age;

    @Column(name = "dob")
    private String dob;

    @Column(name = "declared")
    private String declared;

    @Column(name = "verified")
    private String verified;

    @Column(name = "care_of")
    private String careOf;

    @Column(name = "care_of_name")
    private String careOfName;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "street")
    private String street;

    @Column(name = "landmark")
    private String landmark;

    @Column(name = "area")
    private String area;

    @Column(name = "village")
    private String village;

    @Column(name = "post_office")
    private String postOffice;

    @Column(name = "district")
    private String district;

    @Column(name = "sub_district")
    private String subDistrict;

    @Column(name = "state")
    private String state;

    @Column(name = "email")
    private String email;

    @Column(name = "modile_number")
    private String modileNumber;

    @Column(name = "pin_code")
    private String pinCode;

    @Column(name = "details_of")
    private String detailsOf;

    @Column(name = "details_of_name")
    private String detailsOfName;

    @Column(name = "aadhar_number")
    private String aadharNumber;

    @Column(name = "verfication_type")
    private String verficationType;

    @Column(name = "for_document_based")
    private String forDocumentBased;

    @Column(name = "for_introducer_based")
    private String forIntroducerBased;

    @Column(name = "for_ho_f_based")
    private String forHoFBased;

    @Column(name = "image_url")
    private String imageUrl;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreEnrollmentID() {
        return preEnrollmentID;
    }

    public AadharEnrollForm preEnrollmentID(String preEnrollmentID) {
        this.preEnrollmentID = preEnrollmentID;
        return this;
    }

    public void setPreEnrollmentID(String preEnrollmentID) {
        this.preEnrollmentID = preEnrollmentID;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public AadharEnrollForm tinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
        return this;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public AadharEnrollForm fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public AadharEnrollForm gender(String gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public AadharEnrollForm age(String age) {
        this.age = age;
        return this;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public AadharEnrollForm dob(String dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDeclared() {
        return declared;
    }

    public AadharEnrollForm declared(String declared) {
        this.declared = declared;
        return this;
    }

    public void setDeclared(String declared) {
        this.declared = declared;
    }

    public String getVerified() {
        return verified;
    }

    public AadharEnrollForm verified(String verified) {
        this.verified = verified;
        return this;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getCareOf() {
        return careOf;
    }

    public AadharEnrollForm careOf(String careOf) {
        this.careOf = careOf;
        return this;
    }

    public void setCareOf(String careOf) {
        this.careOf = careOf;
    }

    public String getCareOfName() {
        return careOfName;
    }

    public AadharEnrollForm careOfName(String careOfName) {
        this.careOfName = careOfName;
        return this;
    }

    public void setCareOfName(String careOfName) {
        this.careOfName = careOfName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public AadharEnrollForm houseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public AadharEnrollForm street(String street) {
        this.street = street;
        return this;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLandmark() {
        return landmark;
    }

    public AadharEnrollForm landmark(String landmark) {
        this.landmark = landmark;
        return this;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getArea() {
        return area;
    }

    public AadharEnrollForm area(String area) {
        this.area = area;
        return this;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getVillage() {
        return village;
    }

    public AadharEnrollForm village(String village) {
        this.village = village;
        return this;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public AadharEnrollForm postOffice(String postOffice) {
        this.postOffice = postOffice;
        return this;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getDistrict() {
        return district;
    }

    public AadharEnrollForm district(String district) {
        this.district = district;
        return this;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public AadharEnrollForm subDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
        return this;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getState() {
        return state;
    }

    public AadharEnrollForm state(String state) {
        this.state = state;
        return this;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public AadharEnrollForm email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getModileNumber() {
        return modileNumber;
    }

    public AadharEnrollForm modileNumber(String modileNumber) {
        this.modileNumber = modileNumber;
        return this;
    }

    public void setModileNumber(String modileNumber) {
        this.modileNumber = modileNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public AadharEnrollForm pinCode(String pinCode) {
        this.pinCode = pinCode;
        return this;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getDetailsOf() {
        return detailsOf;
    }

    public AadharEnrollForm detailsOf(String detailsOf) {
        this.detailsOf = detailsOf;
        return this;
    }

    public void setDetailsOf(String detailsOf) {
        this.detailsOf = detailsOf;
    }

    public String getDetailsOfName() {
        return detailsOfName;
    }

    public AadharEnrollForm detailsOfName(String detailsOfName) {
        this.detailsOfName = detailsOfName;
        return this;
    }

    public void setDetailsOfName(String detailsOfName) {
        this.detailsOfName = detailsOfName;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public AadharEnrollForm aadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
        return this;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getVerficationType() {
        return verficationType;
    }

    public AadharEnrollForm verficationType(String verficationType) {
        this.verficationType = verficationType;
        return this;
    }

    public void setVerficationType(String verficationType) {
        this.verficationType = verficationType;
    }

    public String getForDocumentBased() {
        return forDocumentBased;
    }

    public AadharEnrollForm forDocumentBased(String forDocumentBased) {
        this.forDocumentBased = forDocumentBased;
        return this;
    }

    public void setForDocumentBased(String forDocumentBased) {
        this.forDocumentBased = forDocumentBased;
    }

    public String getForIntroducerBased() {
        return forIntroducerBased;
    }

    public AadharEnrollForm forIntroducerBased(String forIntroducerBased) {
        this.forIntroducerBased = forIntroducerBased;
        return this;
    }

    public void setForIntroducerBased(String forIntroducerBased) {
        this.forIntroducerBased = forIntroducerBased;
    }

    public String getForHoFBased() {
        return forHoFBased;
    }

    public AadharEnrollForm forHoFBased(String forHoFBased) {
        this.forHoFBased = forHoFBased;
        return this;
    }

    public void setForHoFBased(String forHoFBased) {
        this.forHoFBased = forHoFBased;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AadharEnrollForm imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AadharEnrollForm aadharEnrollForm = (AadharEnrollForm) o;
        if (aadharEnrollForm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aadharEnrollForm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AadharEnrollForm{" +
            "id=" + getId() +
            ", preEnrollmentID='" + getPreEnrollmentID() + "'" +
            ", tinNumber='" + getTinNumber() + "'" +
            ", fullName='" + getFullName() + "'" +
            ", gender='" + getGender() + "'" +
            ", age='" + getAge() + "'" +
            ", dob='" + getDob() + "'" +
            ", declared='" + getDeclared() + "'" +
            ", verified='" + getVerified() + "'" +
            ", careOf='" + getCareOf() + "'" +
            ", careOfName='" + getCareOfName() + "'" +
            ", houseNumber='" + getHouseNumber() + "'" +
            ", street='" + getStreet() + "'" +
            ", landmark='" + getLandmark() + "'" +
            ", area='" + getArea() + "'" +
            ", village='" + getVillage() + "'" +
            ", postOffice='" + getPostOffice() + "'" +
            ", district='" + getDistrict() + "'" +
            ", subDistrict='" + getSubDistrict() + "'" +
            ", state='" + getState() + "'" +
            ", email='" + getEmail() + "'" +
            ", modileNumber='" + getModileNumber() + "'" +
            ", pinCode='" + getPinCode() + "'" +
            ", detailsOf='" + getDetailsOf() + "'" +
            ", detailsOfName='" + getDetailsOfName() + "'" +
            ", aadharNumber='" + getAadharNumber() + "'" +
            ", verficationType='" + getVerficationType() + "'" +
            ", forDocumentBased='" + getForDocumentBased() + "'" +
            ", forIntroducerBased='" + getForIntroducerBased() + "'" +
            ", forHoFBased='" + getForHoFBased() + "'" +
            ", imageUrl='" + getImageUrl() + "'" +
            "}";
    }
}
