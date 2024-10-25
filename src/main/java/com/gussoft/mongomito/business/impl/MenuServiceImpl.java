package com.gussoft.mongomito.business.impl;

import com.gussoft.mongomito.business.MenuService;
import com.gussoft.mongomito.models.Menu;
import com.gussoft.mongomito.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
public class MenuServiceImpl extends GenericServiceImpl<Menu, String> implements MenuService {

    private final MenuRepository repository;

    @Autowired
    public MenuServiceImpl(MenuRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Flux<Menu> getMenus(String[] roles) {
        return repository.getMenus(roles);
    }
}
