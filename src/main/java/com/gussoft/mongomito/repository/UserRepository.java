package com.gussoft.mongomito.repository;

import com.gussoft.mongomito.models.Users;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends GenericRepository<Users, String> {

    Mono<Users> findOneByUsername(String username);

}
