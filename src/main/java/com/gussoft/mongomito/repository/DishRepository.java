package com.gussoft.mongomito.repository;

import com.gussoft.mongomito.models.Dish;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends GenericRepository<Dish, String> {

}
