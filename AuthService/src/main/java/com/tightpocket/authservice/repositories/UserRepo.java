package com.tightpocket.authservice.repositories;

import com.tightpocket.authservice.entities.UserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends CrudRepository<UserInfo, Long> {

    public UserInfo findByUsername(String username);
}
