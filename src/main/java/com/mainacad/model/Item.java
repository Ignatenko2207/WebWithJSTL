package com.mainacad.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    private Integer id;
    private String name;
    private String code;
    private Integer price;
    private Integer availability;

    public Item(String name, String code, Integer price, Integer availability) {
        this.name = name;
        this.code = code;
        this.price = price;
        this.availability = availability;
    }
}
