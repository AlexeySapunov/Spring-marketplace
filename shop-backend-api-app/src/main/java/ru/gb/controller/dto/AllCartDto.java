package ru.gb.controller.dto;

import ru.gb.service.dto.LineItem;

import java.math.BigDecimal;
import java.util.List;

public class AllCartDto {

    private List<LineItem> lineItems;

    private BigDecimal subTotal;

    public AllCartDto() {
    }

    public AllCartDto(List<LineItem> lineItems, BigDecimal subTotal) {
        this.lineItems = lineItems;
        this.subTotal = subTotal;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
}
