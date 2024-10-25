package com.gussoft.mongomito.repository;

import com.gussoft.mongomito.models.Client;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends GenericRepository<Client, String> {


}
