package com.gussoft.mongomito.controller;

import com.gussoft.mongomito.business.impl.ClientServiceImpl;
import com.gussoft.mongomito.models.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping(path = "/clients")
public class Client2Controller extends GenericController<Client, String> {

    public Client2Controller(ClientServiceImpl service) {
        super(service);
    }

    @Override
    protected String getIdEntity(Client data) {
        return data.getId();
    }
}
