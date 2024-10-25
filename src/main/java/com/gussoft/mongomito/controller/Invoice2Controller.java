package com.gussoft.mongomito.controller;

import com.gussoft.mongomito.business.InvoiceService;
import com.gussoft.mongomito.business.impl.InvoiceServiceImpl;
import com.gussoft.mongomito.models.Dish;
import com.gussoft.mongomito.models.Invoice;
import com.gussoft.mongomito.pagination.PageSupport;
import com.gussoft.mongomito.transfer.request.DishRequest;
import com.gussoft.mongomito.transfer.request.InvoiceRequest;
import com.gussoft.mongomito.transfer.response.DishResponse;
import com.gussoft.mongomito.transfer.response.InvoiceResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping(path = "/invoice")
public class Invoice2Controller {

    private final InvoiceService service;
    private final ModelMapper mapper;

    public Invoice2Controller(InvoiceService service, ModelMapper mapper) {

        this.service = service;
        this.mapper = mapper;
    }


    @GetMapping("/pageable")
    public Mono<ResponseEntity<PageSupport<InvoiceResponse>>> getPage(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "2") int size){

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

    @PostMapping
    public Mono<ResponseEntity<InvoiceResponse>> save(@Valid @RequestBody InvoiceRequest request, final ServerHttpRequest req) {
        return service.save(entityToRequest(request))
                .map(this::responseToEntity)
                .map(data -> ResponseEntity.created(
                                URI.create(req.getURI().toString() + "/" + data.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(data))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/generateReport/{id}")
    public Mono<ResponseEntity<byte[]>> generateReport(@PathVariable("id") String id) {
        return service.generateReport(id)
                .map(bytes -> ResponseEntity
                        .ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=" + id + "_invoice_report.pdf")
                        .contentType(MediaType.APPLICATION_PDF) //APPLICATION_OCTET_STREAM
                        .body(bytes)
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private InvoiceResponse responseToEntity(Invoice obj) {
        return mapper.map(obj, InvoiceResponse.class);
    }

    private Invoice entityToRequest(InvoiceRequest obj) {
        return mapper.map(obj, Invoice.class);
    }

}
