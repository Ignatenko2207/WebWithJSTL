package com.mainacad.dao.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OrderDTO {
	private String itemId;
    private String itemName;
    private Integer itemPrice;
    private Integer amount;
}
