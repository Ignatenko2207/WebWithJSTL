package com.mainacad.dao.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class OrderDTO {
	private Integer orderId;
	private Integer itemId;
    private String itemName;
    private Integer itemPrice;
    private Integer amount;
}
