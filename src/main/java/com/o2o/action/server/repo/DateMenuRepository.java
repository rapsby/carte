package com.o2o.action.server.repo;

import org.springframework.data.repository.CrudRepository;

import com.o2o.action.server.db.MealMenu;

public interface DateMenuRepository extends CrudRepository<MealMenu, Long> {

}
