package com.Sketchr2.services;

import com.Sketchr2.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by ericweidman on 5/2/17.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUserName(String userName);
}
