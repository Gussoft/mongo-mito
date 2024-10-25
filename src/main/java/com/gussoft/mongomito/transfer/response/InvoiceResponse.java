package com.gussoft.mongomito.transfer.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gussoft.mongomito.transfer.request.ClientRequest;
import com.gussoft.mongomito.transfer.request.InvoiceDetailRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceResponse {

    private String id;

    private String description;

    private ClientResponse client;

    private List<InvoiceDetailResponse> detail;

}
