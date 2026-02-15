package com.computerey.springcloud.msvc.items.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.computerey.springcloud.msvc.items.models.Item;
import com.computerey.springcloud.msvc.items.services.ItemService;


@RestController
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public List<Item> list(@RequestParam(name = "name", required = false) String name,
        @RequestHeader(name = "token-request", required = false) String token){
            IO.println(name);
            IO.println(token);
        return service.findAll();
    }

    // Devuelvo genérico porque puede devolver 404 o el item
    @GetMapping("/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<Item> itemOpt = service.findById(id) ;
        if (itemOpt.isPresent()){
            return ResponseEntity.ok(itemOpt.get());
        } 
            return ResponseEntity.status(404)
            .body(Collections.singletonMap("message", "No existe el producto en el microservice msvc-products"));
    }
    
}
