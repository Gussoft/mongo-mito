package com.gussoft.mongomito;

import com.gussoft.mongomito.business.DishService;
import com.gussoft.mongomito.controller.DishController;
import com.gussoft.mongomito.models.Dish;
import com.gussoft.mongomito.transfer.request.DishRequest;
import com.gussoft.mongomito.transfer.response.DishResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@WebFluxTest(controllers = DishController.class)
public class DishControllerTest {

    @Autowired
    private WebTestClient client;

    @MockBean
    private DishService service;

    @MockBean
    private ModelMapper mapper;

    @MockBean
    private WebProperties.Resources resources;

    private Dish dish;
    private DishResponse response;
    private DishRequest request;
    private List<Dish> dishList;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        dish = new Dish();
        dish.setId("1");
        dish.setName("name");
        dish.setPrice(new BigDecimal(15.5));
        dish.setStatus(true);
        response = new DishResponse();
        response.setId("1");
        response.setName("name");
        response.setPrice(new BigDecimal(15.5));
        response.setStatus(true);
        request = new DishRequest();
        request.setId("1");
        request.setName("name");
        request.setPrice(new BigDecimal(15.5));
        request.setStatus(true);
        dishList = new ArrayList<>();
        dishList.add(dish);
    }

    //@Test
    public void findAllTest() {
        Mockito.when(mapper.map(dish, DishRequest.class)).thenReturn(request);
        Mockito.when(service.findAll()).thenReturn(Flux.fromIterable(dishList));
        Mockito.when(mapper.map(dish, DishResponse.class)).thenReturn(response);
        client.get()
                .uri("/dishes")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON);
    }
}
