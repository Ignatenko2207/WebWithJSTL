package com.mainacad.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class ItemDTO {
    private Integer id;
    private String name;
    private Integer price;
}
