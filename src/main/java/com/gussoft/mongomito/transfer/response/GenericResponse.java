package com.gussoft.mongomito.transfer.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GenericResponse<E> {

    private String message;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private E data;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<E> response;

    public GenericResponse(String message) {
        this.message = message;
    }

    public GenericResponse(String message, E data) {
        this.message = message;
        this.data = data;
    }

    public GenericResponse(String message, List<E> response) {
        this.message = message;
        this.response = response;
    }
}
