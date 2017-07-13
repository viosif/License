package com.fortech.repository;

import com.fortech.model.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by iosifvarga on 07.07.2017.
 */
public interface ClientRepository extends CrudRepository<Client, Long> {

    Iterable<Client> findByEmail(String email);

    Client findFirstByEmail(String email);

    @Transactional
    Integer  deleteByEmail(String email);



}
