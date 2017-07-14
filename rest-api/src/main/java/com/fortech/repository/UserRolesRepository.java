package com.fortech.repository;

import com.fortech.model.UserRoles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by iosifvarga on 07.07.2017.
 */
@Repository
public interface UserRolesRepository extends CrudRepository<UserRoles, Long> {

}
