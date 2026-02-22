package com.computerey.springcloud.msvc.items.controllers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.computerey.springcloud.msvc.items.models.Item;
import com.computerey.springcloud.msvc.items.models.Product;
import com.computerey.springcloud.msvc.items.services.ItemService;


@RestController
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService service;
    private final CircuitBreakerFactory cBreakerFactory;

    public ItemController(ItemService service,
        CircuitBreakerFactory cBreakerFactory
    ) {
        this.service = service;
        this.cBreakerFactory = cBreakerFactory;
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
        Optional<Item> itemOpt = cBreakerFactory.create("items").run(() -> service.findById(id) , e -> {
            IO.println(e.getMessage());
            logger.error(e.getMessage());
            
            // Este segundo camino podemos llamar una 2 API, pero aquí usaremos datos estáticos
            Product product = new Product();
            product.setCreateAt(LocalDate.now());
            product.setId(1L);
            product.setName("Cafetera (producto del camino alternativo)");
            product.setPrice(18.75);
            return Optional.of(new Item(product, 5)) ;
            
        });
        if (itemOpt.isPresent()){
            return ResponseEntity.ok(itemOpt.get());
        } 
            return ResponseEntity.status(404)
            .body(Collections.singletonMap("message", "No existe el producto en el microservice msvc-products"));

            
    }
    
}
