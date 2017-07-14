package com.fortech.repository;

import com.fortech.model.Client;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by iosifvarga on 07.07.2017.
 */
@Repository
public interface ClientRepository extends PagingAndSortingRepository<Client, Long> {

    Iterable<Client> findByEmail(String email);

    Client findFirstByEmail(String email);

    @Transactional
    Integer  deleteByEmail(String email);



}
