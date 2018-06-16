package com.amogh.lms.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the AadharEnrollForm entity.
 */
public class AadharEnrollFormDTO implements Serializable {

    private Long id;

    private String preEnrollmentID;

    private String tinNumber;

    private String fullName;

    private String gender;

    private String age;

    private String dob;

    private String declared;

    private String verified;

    private String careOf;

    private String careOfName;

    private String houseNumber;

    private String street;

    private String landmark;

    private String area;

    private String village;

    private String postOffice;

    private String district;

    private String subDistrict;

    private String state;

    private String email;

    private String modileNumber;

    private String pinCode;

    private String detailsOf;

    private String detailsOfName;

    private String aadharNumber;

    private String verficationType;

    private String forDocumentBased;

    private String forIntroducerBased;

    private String forHoFBased;

    private String imageUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreEnrollmentID() {
        return preEnrollmentID;
    }

    public void setPreEnrollmentID(String preEnrollmentID) {
        this.preEnrollmentID = preEnrollmentID;
    }

    public String getTinNumber() {
        return tinNumber;
    }

    public void setTinNumber(String tinNumber) {
        this.tinNumber = tinNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDeclared() {
        return declared;
    }

    public void setDeclared(String declared) {
        this.declared = declared;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getCareOf() {
        return careOf;
    }

    public void setCareOf(String careOf) {
        this.careOf = careOf;
    }

    public String getCareOfName() {
        return careOfName;
    }

    public void setCareOfName(String careOfName) {
        this.careOfName = careOfName;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(String postOffice) {
        this.postOffice = postOffice;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getSubDistrict() {
        return subDistrict;
    }

    public void setSubDistrict(String subDistrict) {
        this.subDistrict = subDistrict;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getModileNumber() {
        return modileNumber;
    }

    public void setModileNumber(String modileNumber) {
        this.modileNumber = modileNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getDetailsOf() {
        return detailsOf;
    }

    public void setDetailsOf(String detailsOf) {
        this.detailsOf = detailsOf;
    }

    public String getDetailsOfName() {
        return detailsOfName;
    }

    public void setDetailsOfName(String detailsOfName) {
        this.detailsOfName = detailsOfName;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getVerficationType() {
        return verficationType;
    }

    public void setVerficationType(String verficationType) {
        this.verficationType = verficationType;
    }

    public String getForDocumentBased() {
        return forDocumentBased;
    }

    public void setForDocumentBased(String forDocumentBased) {
        this.forDocumentBased = forDocumentBased;
    }

    public String getForIntroducerBased() {
        return forIntroducerBased;
    }

    public void setForIntroducerBased(String forIntroducerBased) {
        this.forIntroducerBased = forIntroducerBased;
    }

    public String getForHoFBased() {
        return forHoFBased;
    }

    public void setForHoFBased(String forHoFBased) {
        this.forHoFBased = forHoFBased;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AadharEnrollFormDTO aadharEnrollFormDTO = (AadharEnrollFormDTO) o;
        if(aadharEnrollFormDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), aadharEnrollFormDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AadharEnrollFormDTO{" +
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
