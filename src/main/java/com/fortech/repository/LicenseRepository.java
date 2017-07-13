package com.fortech.repository;

import com.fortech.model.License;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by iosifvarga on 28.06.2017.
 */

public interface LicenseRepository extends CrudRepository<License, Long> {

    //Iterable<License> findByLicenseKey(String licenseKey);

    License findFirstByLicenseKey(String licenseKey);

}