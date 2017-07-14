package com.fortech.DTO;

/**
 * Created by iosifvarga on 28.06.2017.
 */

import com.fortech.model.KeyStatus;
import com.fortech.model.License;
import com.fortech.model.LicenseType;

import java.util.Date;

public class LicenseDTO extends BaseDTO<License> {

    private LicenseType licenseType;
    private Date startDate = new Date();
    private Date endDate;
    private String licenseKey;
    private KeyStatus keyStatus;

    public LicenseDTO() {
    }

    public LicenseDTO(LicenseType licenseType, Date endDate, KeyStatus keyStatus) {
        this.licenseType = licenseType;
        this.endDate = endDate;
        this.keyStatus = keyStatus;
    }

    @Override
    public String toString() {
        return "{" +
                ", licenseType=" + licenseType.toString() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", licenseKey=" + licenseKey +
                ", keyStatus=" + keyStatus.toString() +
                '}';
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

    public License toEntity() {
        License license = new License();
        license.setLicenseType(this.licenseType);
        license.setStartDate(this.startDate);
        license.setEndDate(this.endDate);
        license.setLicenseKey(this.licenseKey);
        license.setKeyStatus(this.keyStatus);
        return license;
    }

}