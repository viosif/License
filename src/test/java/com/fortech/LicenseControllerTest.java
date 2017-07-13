package com.fortech;

import com.fortech.controller.LicenseController;
import com.fortech.model.License;
import com.fortech.model.LicenseType;
import com.fortech.repository.LicenseRepository;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * Created by iosifvarga on 29.06.2017.
 */

public class LicenseControllerTest {


    @InjectMocks
    private LicenseController mc;

    @Mock
    private LicenseRepository licenseRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testDeleteOne() throws ParseException {
        License license = new License();
        license.setId(1);
        license.setLicenseType(LicenseType.LIFETIME);
        license.setStartDate(new Date());
        license.setEndDate(new Date());
        license.setLicenseKey("license");

        //when(licenseRepository.delete(1l).thenReturn(license);

//        License licenseReturned = mc.findOne(1L);
//
//        verify(licenseRepository).findOne(1l);
//
//        assertEquals(1l, licenseReturned.getId());
    }



    @Test
    public void testCreateLicense() {
        LicenseType licenseTypeLIFETIME = LicenseType.LIFETIME;
        License license1 = new License();
        license1.setId(1);
        license1.setLicenseType(licenseTypeLIFETIME);
        license1.setStartDate(new Date());
        license1.setEndDate(new Date());
        license1.setLicenseKey("license1");

        when(licenseRepository.save(license1)).thenReturn(license1);

        //License test = mc.createLicense("name1", "someemail1@someemail.com", licenseTypeLIFETIME, date1);

        //assertThat(test).isEqualTo(license1);

    }

    @Test
    public void testGetLicenseType() throws JSONException {
        LicenseType licenseTypeLIFETIME = LicenseType.LIFETIME;
        License license1 = new License();
        license1.setId(1);
        license1.setLicenseType(licenseTypeLIFETIME);
        license1.setStartDate(new Date());
        license1.setEndDate(new Date());
        license1.setLicenseKey("license1");

        List<License> allLicenses = new ArrayList<License>();
        allLicenses.add(license1);

        when(licenseRepository.findByLicenseKey("license1")).thenReturn(allLicenses);

        //LicenseType test1 = mc.getLicenseType("license1");

        //assertThat(test1).isEqualTo(licenseTypeLIFETIME);
    }

}
