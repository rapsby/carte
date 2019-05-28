package com.o2o.action.server.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.o2o.action.server.db.MealMenu;
import com.o2o.action.server.db.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	UserEntity findByUserName(String userName);
}
