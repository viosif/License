package com.fortech.repository;

import com.fortech.model.Client;
import com.fortech.model.License;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by iosifvarga on 28.06.2017.
 */

public interface LicenseRepository extends CrudRepository<License, Long> {

    Iterable<License> findByLicenseKey(String licenseKey);

    License findFirstByLicenseKey(String licenseKey);

    Iterable<License> findByClient(Client client);

    @Query(value = "select * from license left join client on license.client_id=client.id where client.email = ?#{[0]}", nativeQuery = true)
    List<License> findAllEmail(@Param("email") String email);

    @Transactional
    Integer deleteByClient(Client client);

}