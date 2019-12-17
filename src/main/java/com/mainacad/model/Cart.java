package com.mainacad.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {

    private Integer id;
    private Status status;
    private User user;
    private Long creationTime;

    public Cart(Status status, User user, Long creationTime) {
        this.status = status;
        this.user = user;
        this.creationTime = creationTime;
    }
}
