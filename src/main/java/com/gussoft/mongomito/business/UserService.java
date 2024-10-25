package com.gussoft.mongomito.business;

import com.gussoft.mongomito.models.Users;
import com.gussoft.mongomito.security.User;
import reactor.core.publisher.Mono;

public interface UserService extends GenericService<Users, String> {

    Mono<Users> saveHash(Users user);

    Mono<User> searchByUser(String username);

}
