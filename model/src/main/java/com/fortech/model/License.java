package com.fortech.model;

/**
 * Created by iosifvarga on 28.06.2017.
 */

import com.fortech.dto.LicenseDTO;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "license")
public class License {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    @Column(name = "id")
    private long id;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "licenseType")
    private LicenseType licenseType;

    @Column(name = "startDate")
    private Date startDate = new Date();

    @NotNull
    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "licenseKey", unique = true)
    private String licenseKey;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Column(name = "keyStatus")
    private KeyStatus keyStatus;

    public License() {
    }

    public License(LicenseType licenseType, Date endDate, KeyStatus keyStatus) {
        this.licenseType = licenseType;
        this.endDate = endDate;
        this.keyStatus = keyStatus;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", licenseType=" + licenseType.toString() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", licenseKey=" + licenseKey +
                ", keyStatus=" + keyStatus.toString() +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LicenseType getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(LicenseType licenseType) {
        this.licenseType = licenseType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getLicenseKey() {
        return licenseKey;
    }

    public void setLicenseKey(String licenseKey) {
        this.licenseKey = licenseKey;
    }

    public KeyStatus getKeyStatus() {
        return keyStatus;
    }

    public void setKeyStatus(KeyStatus keyStatus) {
        this.keyStatus = keyStatus;
    }

    public LicenseDTO toDto() {
        LicenseDTO licenseDTO = new LicenseDTO();
        licenseDTO.setLicenseType(this.licenseType);
        licenseDTO.setStartDate(this.startDate);
        licenseDTO.setEndDate(this.endDate);
        licenseDTO.setLicenseKey(this.licenseKey);
        licenseDTO.setKeyStatus(this.keyStatus);
        return licenseDTO;
    }

}