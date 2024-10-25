package com.gussoft.mongomito.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Document(collection = "dishes")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Dish {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private String name;

    @Field(name = "price")
    private BigDecimal price;

    @Field
    private Boolean status;

}
