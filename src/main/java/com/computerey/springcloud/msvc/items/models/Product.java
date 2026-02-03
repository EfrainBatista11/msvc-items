package com.computerey.springcloud.msvc.items.models;

import java.time.LocalDate;

public class Product {

    private Long id;
    private String name;
    private Double price;
    private LocalDate createAt;

    public Product() {
    }
    
    public Product(Long id, String name, Double price, LocalDate createAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.createAt = createAt;
    }
    

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public LocalDate getCreateAt() {
        return createAt;
    }
    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }
}