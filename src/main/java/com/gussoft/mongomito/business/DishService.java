package com.gussoft.mongomito.business;

import com.gussoft.mongomito.models.Dish;
import reactor.core.publisher.Flux;


public interface DishService extends GenericService<Dish, String> {

    Flux<Dish> findByName(String name);

}
