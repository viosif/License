package com.fortech;

import com.fortech.model.Client;
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
    Date startDate = new Date();
    Date endDate = new Date();
    LicenseType licenseTypeLIFETIME = LicenseType.LIFETIME;
    KeyStatus keyStatus = KeyStatus.KEY_GOOD;
    License license1 ;
    Client client;

    @Before
    public void setup() throws Exception {
        license1 = new License(licenseTypeLIFETIME,startDate,endDate,keyStatus,client);
    }
    @Test
    public void getId() throws Exception {
        assertEquals(1, license1.getId());
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
        assertEquals(startDate,license1.getStartDate());
    }

    @Test
    public void setStartDate() throws Exception {
        Date date2 = new Date();
        license1.setEndDate(date2);
        assertEquals(date2, license1.getStartDate());
    }

    @Test
    public void getEndDate() throws Exception {
        assertEquals(startDate,license1.getEndDate());
    }

    @Test
    public void setEndDate() throws Exception {
        Date date2 = new Date();
        license1.setEndDate(date2);
        assertEquals(date2, license1.getEndDate());
    }

    @Test
    public void getLicense() throws Exception {
        assertEquals("license1",license1.getLicenseKey());
    }

    @Test
    public void setLicense() throws Exception {
        license1.setLicenseKey("license2");
        assertEquals("license2", license1.getLicenseKey());
    }

    @Test
    public void getKeyStatus() throws Exception {
        assertEquals(keyStatus.toString(), license1.getKeyStatus());
    }

    @Test
    public void setKeyStatus() throws Exception {
        license1.setKeyStatus(KeyStatus.KEY_BLACKLISTED);
        assertEquals(KeyStatus.KEY_BLACKLISTED.toString(), license1.getKeyStatus());
    }

}