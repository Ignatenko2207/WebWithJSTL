package com.mainacad.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private Integer id;
    private String itemCode;
    private String name;
    private Integer price;

    public Item(String itemCode, String name, Integer price) {
        this.itemCode = itemCode;
        this.name = name;
        this.price = price;
    }
}
