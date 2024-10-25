package com.gussoft.mongomito.repository;

import com.gussoft.mongomito.models.Invoice;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends GenericRepository<Invoice, String> {


}
