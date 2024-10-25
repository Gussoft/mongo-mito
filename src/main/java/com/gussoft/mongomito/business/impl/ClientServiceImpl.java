package com.gussoft.mongomito.business.impl;

import com.gussoft.mongomito.business.ClientService;
import com.gussoft.mongomito.models.Client;
import com.gussoft.mongomito.repository.ClientRepository;
import com.gussoft.mongomito.repository.GenericRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@Slf4j
public class ClientServiceImpl extends GenericServiceImpl<Client, String> implements ClientService {

    private final ClientRepository repository;

    @Autowired
    public ClientServiceImpl(ClientRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public Mono<Client> save(Client request) {
        request.setBirthDate(LocalDate.now());
        return super.save(request);
    }
}
