package com.mainacad.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CartSumDTO {
    private String userLogin;
    private Integer sumItems;
    private Long creationTime;
}
