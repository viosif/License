package com.fortech.controller.impl;

/**
 * Created by iosifvarga on 28.06.2017.
 */

import com.fortech.controller.LicenseInterface;
import com.fortech.services.GenerateKey;
import com.fortech.model.KeyStatus;
import com.fortech.model.License;
import com.fortech.model.LicenseType;
import com.fortech.repository.ClientRepository;
import com.fortech.repository.LicenseRepository;
import com.fortech.dto.LicenseDTO;
import com.fortech.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by iosifvarga on 07.07.2017.
 */
@CrossOrigin
@RestController    // This means that this class is a Controller
@RequestMapping(value = "/license") // This means URL's start with /demo (after Application path)
public class LicenseController implements LicenseInterface {

    private final static Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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
            log.info("getAllLicense " + license.toString());
        });
        return licenseDTOS;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public LicenseDTO createLicense(@RequestBody LicenseDTO licenseDTO, @RequestParam String email) {
        licenseDTO.setStartDate(new Date());
        licenseDTO.setKeyStatus(KeyStatus.KEY_GOOD);
        licenseDTO.setLicenseKey(GenerateKey.generateLicenseKey());
        License license = licenseDTO.toEntity();
        licenseRepository.save(license);

        Client client = clientRepository.findFirstByEmail(email);
        List<License> licensesDtos = client.getLicense();
        licensesDtos.add(license);
        client.setLicense(licensesDtos);

        clientRepository.save(client);

        log.info("createLicense " + licenseDTO.toString());

        return licenseDTO;
    }

    @RequestMapping(path = "/getLicenseByKey", method = RequestMethod.GET)
    public License getLicenseByKey(@RequestParam("licenseKey") String licenseParam) {
        License license = licenseRepository.findFirstByLicenseKey(licenseParam);
        log.info("getLicenseByKey " + license.toString());
        return license;
    }

    @RequestMapping(path = "/changeKeyStatusByLicenseKey", method = RequestMethod.GET)
    public LicenseDTO changeKeyStatusByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("keyStatus") KeyStatus keyStatus) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        license.setKeyStatus(keyStatus);
        licenseRepository.save(license);
        log.info("changeKeyStatusByLicenseKey " + license.toDto());
        return license.toDto();
    }

    @RequestMapping(path = "/changeLicenseTypeByLicenseKey", method = RequestMethod.GET)
    public LicenseDTO changeLicenseTypeByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("licenseType") LicenseType licenseType) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        license.setLicenseType(licenseType);
        licenseRepository.save(license);
        log.info("changeLicenseTypeByLicenseKey " + license.toDto());
        return license.toDto();
    }

    @RequestMapping(path = "/changeStartDateByLicenseKey", method = RequestMethod.GET)
    public LicenseDTO changeStartDateByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("startDate") String startDate) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        license.setStartDate(new Date(Long.valueOf(startDate)));
        licenseRepository.save(license);
        log.info("changeStartDateByLicenseKey " + license.toDto());
        return license.toDto();
    }

    @RequestMapping(path = "/changeEndDateByLicenseKey", method = RequestMethod.GET)
    public LicenseDTO changeEndDateByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("endDate") String endDate) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        license.setEndDate(new Date(Long.valueOf(endDate)));
        licenseRepository.save(license);
        log.info("changeEndDateByLicenseKey " + license.toDto());
        return license.toDto();
    }

    @RequestMapping(path = "/deleteLicenseByKey", method = RequestMethod.DELETE)
    public LicenseDTO deleteLicenseByKey(@RequestParam("key") String licenseKey) {
        License license = licenseRepository.findFirstByLicenseKey(licenseKey);
        licenseRepository.delete(license.getId());
        log.info("deleteLicenseByKey " + license.toDto());
        return license.toDto();
    }

}