package com.gussoft.mongomito.transfer.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gussoft.mongomito.transfer.request.DishRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvoiceDetailResponse {

    private int quantity;

    private DishResponse dish;

}
