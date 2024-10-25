package com.gussoft.mongomito.business.impl;

import com.gussoft.mongomito.business.DishService;
import com.gussoft.mongomito.models.Dish;
import com.gussoft.mongomito.repository.DishRepository;
import com.gussoft.mongomito.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class DishServiceImpl extends GenericServiceImpl<Dish, String> implements DishService {

    private final DishRepository repository;

    @Autowired
    public DishServiceImpl(DishRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Mono<Dish> save(Dish request) {
        log.info(request.toString());
        return super.save(request);
    }

    @Override
    public Flux<Dish> findByName(String name) {
        return null;
    }
}
