package com.gussoft.mongomito.controller;

import com.gussoft.mongomito.business.GenericService;
import java.net.URI;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
public abstract class GenericController<T, ID> {

    private final GenericService<T, ID> service;

    public GenericController(GenericService<T, ID> service) {
        this.service = service;
    }

    protected abstract ID getIdEntity(T data);
    
    @GetMapping
    public Mono<ResponseEntity<Flux<T>>> findAll() {
        return Mono.just(ResponseEntity.ok(service.findAll()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/page")
    public Mono<ResponseEntity<Page<T>>> findAllPage(
            @RequestParam(name = "name", defaultValue = "", required = false) String name,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "5", required = false) int size,
            @RequestParam(name = "sortBy", defaultValue = "id", required = false) String sortBy) {
        String[] sortArray = sortBy.contains(",")
                ? Arrays.stream(sortBy.split(",")).map(String::trim).toArray(String[]::new)
                : new String[] { sortBy.trim() };
        Sort sort = Sort.by(Sort.Direction.fromString("asc"), sortArray);
        Pageable pageable = PageRequest.of(page, size, sort);

        return service.findAllPage(pageable, name)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<T>> findById(@PathVariable ID id) {
        return service.findById(id)
                .map(data -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(data))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<T>> save(@RequestBody T request, ServerHttpRequest http) {
        return service.save(request)
                .map(data -> ResponseEntity.created(
                                URI.create(http.getURI().toString() + "/" + getIdEntity(data)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(data))
                .doOnNext(e -> log.info(e.toString()))
                .defaultIfEmpty(ResponseEntity.badRequest().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<T>> update(@PathVariable ID id, @RequestBody T request) {
        return service.update(id, request)
                .map(data -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(data))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable ID id) {
        return service.delete(id)
                .flatMap(result -> {
                    if (result) {
                        return Mono.just(ResponseEntity.noContent().build());
                    } else {
                        return Mono.just(ResponseEntity.notFound().build());
                    }
                });

    }

}
