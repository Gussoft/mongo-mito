package com.gussoft.mongomito.transfer.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequest {

    private String id;

    private String name;

    private String lastName;

    private LocalDate birthDate;
    private String photo;

    private Boolean status;

}
