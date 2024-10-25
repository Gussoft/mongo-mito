package com.gussoft.mongomito.business.impl;

import com.gussoft.mongomito.business.GenericService;
import com.gussoft.mongomito.pagination.PageSupport;
import com.gussoft.mongomito.repository.GenericRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public abstract class GenericServiceImpl<T, ID> implements GenericService<T, ID> {

    private final GenericRepository<T, ID> repository;

    @Override
    public Flux<T> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<Page<T>> findAllPage(Pageable page, String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .skip((long) page.getPageNumber() * page.getPageSize())
                .take(page.getPageSize())
                .collectList()
                .zipWith(repository.count())
                .map(tuple -> new PageImpl<>(tuple.getT1(), page, tuple.getT2()));
    }

    @Override
    public Mono<PageSupport<T>> getPagesH(Pageable page) {
        return repository.findAll()
                .collectList()
                .map(list -> new PageSupport<>(
                        //1,2,3,4,5,6,7,8,9,10
                        list.stream()
                                .skip(page.getPageNumber() * page.getPageSize())
                                .limit(page.getPageSize()).toList()
                        ,
                        page.getPageNumber(),
                        page.getPageSize(),
                        list.size()
                ));
    }

    @Override
    public Mono<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Mono<T> save(T request) {
        return repository.save(request);
    }

    @Override
    @Transactional
    public Mono<T> update(ID id, T request) {
        return repository.findById(id)
                .flatMap(data -> repository.save(request));
    }

    @Override
    @Transactional
    public Mono<Boolean> delete(ID id) {
        return repository.findById(id)
                .hasElement()
                .flatMap(data -> {
                    if (data) {
                        return repository.deleteById(id).thenReturn(true);
                    } else {
                        return Mono.just(false);
                    }
                });
    }
}
