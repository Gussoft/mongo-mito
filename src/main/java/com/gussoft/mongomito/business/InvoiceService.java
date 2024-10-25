package com.gussoft.mongomito.business;

import com.gussoft.mongomito.models.Invoice;
import reactor.core.publisher.Mono;

public interface InvoiceService extends GenericService<Invoice, String> {

    Mono<byte[]> generateReport(String id);

}
