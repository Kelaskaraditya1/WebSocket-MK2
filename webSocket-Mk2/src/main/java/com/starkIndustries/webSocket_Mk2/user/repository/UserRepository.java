package com.starkIndustries.webSocket_Mk2.user.repository;

import com.starkIndustries.webSocket_Mk2.user.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String> {

}
