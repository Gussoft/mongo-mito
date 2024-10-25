package com.gussoft.mongomito.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.gussoft.mongomito.business.ClientService;
import com.gussoft.mongomito.business.DishService;
import com.gussoft.mongomito.models.Client;
import com.gussoft.mongomito.models.Dish;
import com.gussoft.mongomito.transfer.request.ClientRequest;
import com.gussoft.mongomito.transfer.request.DishRequest;
import com.gussoft.mongomito.transfer.response.ClientResponse;
import com.gussoft.mongomito.transfer.response.DishResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cloudinary.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.file.Files;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "/client")
public class ClientController {

    private final ClientService service;
    private final ModelMapper mapper;
    private final Cloudinary cloudinary;

    @GetMapping
    public Mono<ResponseEntity<Flux<ClientResponse>>> findAll() {
        return Mono.just(ResponseEntity.ok(service.findAll().map(map -> mapper.map(map, ClientResponse.class))))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ClientResponse>> findById(@PathVariable("id") String id) {
        return service.findById(id)
                .map(data -> mapper.map(data, ClientResponse.class))
                .map(data -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(data))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ClientResponse>> save(@Valid @RequestBody ClientRequest request, final ServerHttpRequest req) {
        return service.save(entityToRequest(request))
                .map(this::responseToEntity)
                .map(data -> ResponseEntity.created(
                                URI.create(req.getURI().toString() + "/" + data.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(data))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ClientResponse>> update(@Valid @PathVariable("id") String id, @RequestBody ClientRequest request) {
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

    @PostMapping("/v3/upload/{id}")
    public Mono<ResponseEntity<ClientResponse>> uploadV3(
            @PathVariable("id") String id,
            @RequestPart("part") FilePart part) {
        return Mono.fromCallable(() -> {
            return Files.createTempFile("temp", part.filename()).toFile();
        })
                .flatMap(temp -> part.transferTo(temp)
                        .then(service.findById(id)
                                .flatMap(client -> {
                                    return Mono.fromCallable(() -> {
                                        Map<String, Object> response = cloudinary.uploader().upload(temp, ObjectUtils.asMap("resource_type", "auto"));
                                        JSONObject json = new JSONObject(response);
                                        String url = json.getString("url");

                                        // Actualizar la URL de la foto del cliente
                                        client.setPhoto(url);
                                        return service.update(id, client)
                                                .map(updatedClient -> ResponseEntity.ok().body(responseToEntity(updatedClient)));
                                    }).flatMap(mono -> mono);
                                })
                        )
                );
    }

    private ClientResponse responseToEntity(Client obj) {
        return mapper.map(obj, ClientResponse.class);
    }

    private Client entityToRequest(ClientRequest obj) {
        return mapper.map(obj, Client.class);
    }

}
