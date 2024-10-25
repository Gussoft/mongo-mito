package com.gussoft.mongomito.transfer.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DishRequest {

    private String id;

    @NotNull
    private String name;

    @NotNull
    @Min(value = 1)
    private BigDecimal price;

    @NotNull
    private Boolean status;

}
