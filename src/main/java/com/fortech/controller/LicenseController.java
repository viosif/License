package com.fortech.controller;

/**
 * Created by iosifvarga on 28.06.2017.
 */

import com.fortech.DTO.LicenseDTO;
import com.fortech.model.Client;
import com.fortech.model.KeyStatus;
import com.fortech.model.License;
import com.fortech.model.LicenseType;
import com.fortech.repository.ClientRepository;
import com.fortech.repository.LicenseRepository;
import com.fortech.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public Iterable<LicenseDTO> getAllLicense() {
        List<LicenseDTO> licenseDTOS = new ArrayList<>();
        Iterable<License> licenses = licenseRepository.findAll();
        licenses.forEach(license -> {
            licenseDTOS.add(license.toDto());
        });
        return licenseDTOS;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public LicenseDTO createLicense(@RequestBody LicenseDTO licenseDTO, @RequestParam String email) {
        licenseDTO.setStartDate(new Date());
        licenseDTO.setKeyStatus(KeyStatus.KEY_GOOD);
        licenseDTO.setLicenseKey(Utils.generateLicenseKey());
        License license = licenseDTO.toEntity();
        licenseRepository.save(license);

        Client client = clientRepository.findFirstByEmail(email);
        List<License> licensesDtos = client.getLicense();
        licensesDtos.add(license);
        client.setLicense(licensesDtos);

        clientRepository.save(client);

        return licenseDTO;
    }

    @RequestMapping(path = "/getLicenseByKey", method = RequestMethod.GET)
    public License getLicenseByKey(@RequestParam("licenseKey") String licenseParam) {
        return licenseRepository.findFirstByLicenseKey(licenseParam);
    }

    @RequestMapping(path = "/changeKeyStatusByLicenseKey", method = RequestMethod.GET)
    public LicenseDTO changeKeyStatusByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("keyStatus") KeyStatus keyStatus) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        license.setKeyStatus(keyStatus);
        licenseRepository.save(license);
        return license.toDto();
    }

    @RequestMapping(path = "/changeLicenseTypeByLicenseKey", method = RequestMethod.GET)
    public LicenseDTO changeLicenseTypeByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("licenseType")LicenseType licenseType) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        license.setLicenseType(licenseType);
        licenseRepository.save(license);
        return license.toDto();
    }

    @RequestMapping(path = "/changeStartDateByLicenseKey", method = RequestMethod.GET)
    public LicenseDTO changeStartDateByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("startDate")String startDate) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        license.setStartDate(new Date(Long.valueOf(startDate)));
        licenseRepository.save(license);
        return license.toDto();
    }

    @RequestMapping(path = "/changeEndDateByLicenseKey", method = RequestMethod.GET)
    public LicenseDTO changeEndDateByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("endDate")String endDate) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        license.setEndDate(new Date(Long.valueOf(endDate)));
        licenseRepository.save(license);
        return license.toDto();
    }

    @RequestMapping(path = "/deleteLicenseByKey", method = RequestMethod.DELETE)
    public LicenseDTO deleteLicenseByKey(@RequestParam("key") String licenseKey) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        licenseRepository.delete(license.getId());
        return license.toDto();
    }

}