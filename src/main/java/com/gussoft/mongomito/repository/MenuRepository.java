package com.gussoft.mongomito.repository;

import com.gussoft.mongomito.models.Menu;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface MenuRepository extends GenericRepository<Menu, String> {

    @Query("{'roles' : { $in : [?0] }}")
    Flux<Menu> getMenus(String[] roles);

}
