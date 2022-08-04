package com.pdvapi.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemSaleDTO {
    private String user;
    private String date;
    private List<ProductInfoDTO> products;
}
