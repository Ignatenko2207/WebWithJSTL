package com.mainacad.dao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CartDTO {
    private String userLogin;
    private Integer sumItems;
    private Long creationTime;
}
