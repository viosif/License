package com.fortech.controller;

import com.fortech.dto.LicenseDTO;
import com.fortech.model.KeyStatus;
import com.fortech.model.License;
import com.fortech.model.LicenseType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by iosifvarga on 14.07.2017.
 */
public interface LicenseInterface {

    @RequestMapping(path = "/list", method = RequestMethod.GET)
    public Iterable<LicenseDTO> getAllLicense();

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public LicenseDTO createLicense(@RequestBody LicenseDTO licenseDTO, @RequestParam String email);

    @RequestMapping(path = "/getLicenseByKey", method = RequestMethod.GET)
    public License getLicenseByKey(@RequestParam("licenseKey") String licenseParam);

    @RequestMapping(path = "/changeKeyStatusByLicenseKey", method = RequestMethod.GET)
    public LicenseDTO changeKeyStatusByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("keyStatus") KeyStatus keyStatus);

    @RequestMapping(path = "/changeLicenseTypeByLicenseKey", method = RequestMethod.GET)
    public LicenseDTO changeLicenseTypeByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("licenseType")LicenseType licenseType);

    @RequestMapping(path = "/changeStartDateByLicenseKey", method = RequestMethod.GET)
    public LicenseDTO changeStartDateByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("startDate")String startDate);

    @RequestMapping(path = "/changeEndDateByLicenseKey", method = RequestMethod.GET)
    public LicenseDTO changeEndDateByLicenseKey(@RequestParam("licenseKey") String licenseKey, @RequestParam("endDate")String endDate);

    @RequestMapping(path = "/deleteLicenseByKey", method = RequestMethod.DELETE)
    public LicenseDTO deleteLicenseByKey(@RequestParam("key") String licenseKey);
}
