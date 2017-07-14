package com.fortech.repository;

import com.fortech.model.License;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by iosifvarga on 28.06.2017.
 */
@Repository
public interface LicenseRepository extends CrudRepository<License, Long> {

    //Iterable<License> findByLicenseKey(String licenseKey);

    License findFirstByLicenseKey(String licenseKey);

    @Transactional
    Integer deleteByLicenseKey(String licenseKey);

}