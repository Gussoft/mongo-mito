package com.gussoft.mongomito.controller;

import com.gussoft.mongomito.business.impl.DishServiceImpl;
import com.gussoft.mongomito.models.Dish;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping(path = "/dishes")
public class Dish2Controller extends GenericController<Dish, String> {


    public Dish2Controller(DishServiceImpl service) {
        super(service);
    }

    @Override
    protected String getIdEntity(Dish data) {
        return data.getId();
    }
}
