package com.gussoft.mongomito.transfer.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceRequest {

    private String id;

    private String description;

    private ClientRequest client;

    private List<InvoiceDetailRequest> detail;

}
