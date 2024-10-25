package com.gussoft.mongomito.config;

import com.gussoft.mongomito.handler.ClientHandler;
import com.gussoft.mongomito.handler.DishHandler;
import com.gussoft.mongomito.handler.InvoiceHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RoutingConfig {

    @Bean
    public RouterFunction<ServerResponse> dishRoutes(DishHandler handler) {
        return route(GET("/v2/dishes"), handler::findAll)
                .andRoute(GET("/v2/dishes/{id}"), handler::findById)
                .andRoute(POST("/v2/dishes"), handler::save)
                .andRoute(PUT("/v2/dishes/{id}"), handler::update)
                .andRoute(DELETE("/v2/dishes/{id}"), handler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> clientRoutes(ClientHandler handler) {
        return route(GET("/v2/clients"), handler::findAll)
                .andRoute(GET("/v2/clients/{id}"), handler::findById)
                .andRoute(POST("/v2/clients"), handler::save)
                .andRoute(PUT("/v2/clients/{id}"), handler::update)
                .andRoute(DELETE("/v2/clients/{id}"), handler::delete);
    }

    @Bean
    public RouterFunction<ServerResponse> invoiceRoutes(InvoiceHandler handler) {
        return route(GET("/v2/clients"), handler::findAll)
                .andRoute(GET("/v2/clients/{id}"), handler::findById)
                .andRoute(POST("/v2/clients"), handler::save)
                .andRoute(PUT("/v2/clients/{id}"), handler::update)
                .andRoute(DELETE("/v2/clients/{id}"), handler::delete);
    }
}
