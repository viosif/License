package com.fortech.repository;

import com.fortech.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by iosifvarga on 07.07.2017.
 */
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
