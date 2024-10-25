package com.gussoft.mongomito.business;

import com.gussoft.mongomito.models.Menu;
import reactor.core.publisher.Flux;

public interface MenuService extends GenericService<Menu, String> {

    Flux<Menu> getMenus(String[] roles);

}
