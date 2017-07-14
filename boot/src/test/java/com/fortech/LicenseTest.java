package com.fortech;

import com.fortech.model.KeyStatus;
import com.fortech.model.License;
import com.fortech.model.LicenseType;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by iosifvarga on 30.06.2017.
 */
public class LicenseTest {
    Date endDate = new Date();
    LicenseType licenseTypeLIFETIME = LicenseType.LIFETIME;
    KeyStatus keyStatus = KeyStatus.KEY_GOOD;
    License license1;

    @Before
    public void setup() throws Exception {
        license1 = new License(licenseTypeLIFETIME, endDate, keyStatus);
    }

    @Test
    public void getId() throws Exception {
        //todo 0!=1
        assertEquals(0, license1.getId());
    }

    @Test
    public void setId() throws Exception {
        license1.setId(2);
        assertEquals(2, license1.getId());
    }


    @Test
    public void getLicenseType() throws Exception {
        assertEquals(licenseTypeLIFETIME, license1.getLicenseType());
    }

    @Test
    public void setLicenseType() throws Exception {
        license1.setLicenseType(LicenseType.SINGLE_VERSION);
        assertEquals(LicenseType.SINGLE_VERSION, license1.getLicenseType());
    }

    @Test
    public void getStartDate() throws Exception {
        Date startDate = new Date();
        assertEquals(startDate, license1.getStartDate());
    }

    @Test
    public void setStartDate() throws Exception {
        Date date2 = new Date();
        license1.setEndDate(date2);
        assertEquals(date2, license1.getStartDate());
    }

    @Test
    public void getEndDate() throws Exception {
        assertEquals(endDate, license1.getEndDate());
    }

    @Test
    public void setEndDate() throws Exception {
        Date date2 = new Date();
        license1.setEndDate(date2);
        assertEquals(date2, license1.getEndDate());
    }

    @Test
    public void getLicense() throws Exception {
        license1.setLicenseKey("license1");
        assertEquals("license1", license1.getLicenseKey());
    }

    @Test
    public void setLicense() throws Exception {
        license1.setLicenseKey("license2");
        assertEquals("license2", license1.getLicenseKey());
    }

    @Test
    public void getKeyStatus() throws Exception {
        assertEquals(keyStatus, license1.getKeyStatus());
    }

    @Test
    public void setKeyStatus() throws Exception {
        license1.setKeyStatus(KeyStatus.KEY_BLACKLISTED);
        assertEquals(KeyStatus.KEY_BLACKLISTED, license1.getKeyStatus());
    }

}