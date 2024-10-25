package com.gussoft.mongomito.models;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "invoices")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    private String id;

    private String description;

    private Client client;

    private List<InvoiceDetail> detail;
}
