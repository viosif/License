package com.fortech.controller;

/**
 * Created by iosifvarga on 28.06.2017.
 */

import com.fortech.model.Client;
import com.fortech.model.KeyStatus;
import com.fortech.model.License;
import com.fortech.model.LicenseType;
import com.fortech.repository.ClientRepository;
import com.fortech.repository.LicenseRepository;
import com.fortech.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController    // This means that this class is a Controller
@RequestMapping(value = "/license") // This means URL's start with /demo (after Application path)
public class LicenseController {
    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private ClientRepository clientRepository;

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Iterable<License> getAllLicense() {
        return licenseRepository.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public License createLicense(@RequestBody License license, @RequestParam String email) {
        license.setStartDate(new Date());
        license.setKeyStatus(KeyStatus.KEY_GOOD);
        license.setLicenseKey(Utils.generateLicenseKey());
        licenseRepository.save(license);

        Client client = clientRepository.findFirstByEmail(email);
        List<License> licenses = client.getLicense();
        licenses.add(license);
        client.setLicense(licenses);
        clientRepository.save(client);

        return license;
    }

    @RequestMapping(path = "/getLicenseByKey", method = RequestMethod.GET)
    public License getLicenseByKey(@RequestParam("licenseKey") String licenseParam) {
        return licenseRepository.findFirstByLicenseKey(licenseParam);
    }

    @RequestMapping(path = "/changeKeyStatusByLicenseKey", method = RequestMethod.GET)
    public License changeKeyStatusByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("keyStatus") KeyStatus keyStatus) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        license.setKeyStatus(keyStatus);
        licenseRepository.save(license);
        return license;
    }

    @RequestMapping(path = "/changeLicenseTypeByLicenseKey", method = RequestMethod.GET)
    public License changeLicenseTypeByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("licenseType")LicenseType licenseType) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        license.setLicenseType(licenseType);
        licenseRepository.save(license);
        return license;
    }

    @RequestMapping(path = "/changeStartDateByLicenseKey", method = RequestMethod.GET)
    public License changeStartDateByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("startDate")String startDate) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        license.setStartDate(new Date(Long.valueOf(startDate)));
        licenseRepository.save(license);
        return license;
    }

    @RequestMapping(path = "/changeEndDateByLicenseKey", method = RequestMethod.GET)
    public License changeEndDateByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("endDate")String endDate) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        license.setEndDate(new Date(Long.valueOf(endDate)));
        licenseRepository.save(license);
        return license;
    }

    @RequestMapping(path = "/deleteLicenseByKey", method = RequestMethod.DELETE)
    public License deleteLicenseByKey(@RequestParam("key") String licenseKey) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        licenseRepository.delete(license.getId());
        return license;
    }

}