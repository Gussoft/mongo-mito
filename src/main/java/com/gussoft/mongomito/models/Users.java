package com.gussoft.mongomito.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String username;
    private String password;
    private Boolean status;

    private List<Roles> roles;

}
