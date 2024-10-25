package com.gussoft.mongomito.controller;

import com.gussoft.mongomito.business.DishService;
import com.gussoft.mongomito.models.Dish;
import java.net.URI;

import com.gussoft.mongomito.pagination.PageSupport;
import com.gussoft.mongomito.transfer.request.DishRequest;
import com.gussoft.mongomito.transfer.response.DishResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/dish")
public class DishController {

    private final DishService service;
    private final ModelMapper mapper;
    @GetMapping
    public Mono<ResponseEntity<Flux<DishResponse>>> findAll() {
        return Mono.just(ResponseEntity.ok(service.findAll().map(map -> mapper.map(map, DishResponse.class))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<DishResponse>> findById(@PathVariable("id") String id) {
        return service.findById(id)
                .map(data -> mapper.map(data, DishResponse.class))
                .map(data -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(data))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<DishResponse>> save(@Valid @RequestBody DishRequest request, final ServerHttpRequest req) {
        return service.save(entityToRequest(request))
                .map(this::responseToEntity)
                .map(data -> ResponseEntity.created(
                        URI.create(req.getURI().toString() + "/" + data.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(data))
                        .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<DishResponse>> update(@Valid @PathVariable("id") String id, @RequestBody DishRequest request) {
        return Mono.just(request)
                .map(x -> {
                    x.setId(id);
                    return x;
                })

                .flatMap(data -> service.update(id, entityToRequest(data)))
                .map(this::responseToEntity)
                .map(data -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(data))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Boolean>> delete(@PathVariable("id") String id) {
        return service.delete(id)
                .map(data -> ResponseEntity.noContent().build());
    }

    // USO DE HATEOAS
    @GetMapping("/hateoas/{id}")
    public Mono<EntityModel<DishResponse>> findByIdH(@PathVariable("id") String id) {
        Mono<Link> mono = linkTo(methodOn(DishController.class).findById(id)).withRel("dish-info").toMono();
        return service.findById(id)
                .map(this::responseToEntity)
                .zipWith(mono, EntityModel::of); // (data, link) -> EntityModel.of(data, link));
    }

    @GetMapping("/pageable")
    public Mono<ResponseEntity<PageSupport<DishResponse>>> getPage(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size){

        return service.getPagesH(PageRequest.of(page, size))
                .map(pageSupport -> new PageSupport<>(
                        pageSupport.getContent().stream().map(this::responseToEntity).toList(),
                        pageSupport.getPageNumber(),
                        pageSupport.getPageSize(),
                        pageSupport.getTotalElements()
                ))
                .map(e -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(e)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    private DishResponse responseToEntity(Dish obj) {
        return mapper.map(obj, DishResponse.class);
    }

    private Dish entityToRequest(DishRequest obj) {
        return mapper.map(obj, Dish.class);
    }

}
