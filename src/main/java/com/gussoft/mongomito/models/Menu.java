package com.gussoft.mongomito.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Document(collection = "menus")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Menu implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    @Field
    private String icon;

    @Field
    private String name;

    @Field
    private String url;

    @Field
    private List<Roles> roles;

}
