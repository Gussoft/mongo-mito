package com.gussoft.mongomito.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "client")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @EqualsAndHashCode.Include
    private String id;

    private String name;

    private String lastName;

    private LocalDate birthDate;

    private String photo;

    private Boolean status;

}
