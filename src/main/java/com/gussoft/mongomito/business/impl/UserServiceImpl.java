package com.gussoft.mongomito.business.impl;

import com.gussoft.mongomito.business.UserService;
import com.gussoft.mongomito.models.Roles;
import com.gussoft.mongomito.models.Users;
import com.gussoft.mongomito.repository.RoleRepository;
import com.gussoft.mongomito.repository.UserRepository;
import com.gussoft.mongomito.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserServiceImpl extends GenericServiceImpl<Users, String> implements UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(UserRepository repository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
        super(repository);
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public Mono<Users> saveHash(Users user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public Mono<User> searchByUser(String username) {
        return repository.findOneByUsername(username)
                .flatMap(users -> Flux.fromIterable(users.getRoles())
                        .flatMap(userRole -> roleRepository.findById(userRole.getId())
                                .map(Roles::getName))
                        .collectList()
                        .map(roles -> new User(users.getUsername(), users.getPassword(), users.getStatus(), roles))
                );
    }

}
