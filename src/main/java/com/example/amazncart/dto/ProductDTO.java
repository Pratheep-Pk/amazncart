package com.example.amazncart.dto;

import com.example.amazncart.entity.Product;
import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String category;
    private Double price;
    private String currency;
    private String origin;
    private Double rating;
    private Integer inventory;
    private DiscountDTO discount;

    public static ProductDTO fromEntity(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setCurrency(product.getCurrency());
        dto.setOrigin(product.getOrigin());
        dto.setRating(product.getRating());
        dto.setInventory(product.getInventory());
        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setCategory(dto.getCategory());
        product.setPrice(dto.getPrice());
        product.setCurrency(dto.getCurrency());
        product.setOrigin(dto.getOrigin());
        product.setRating(dto.getRating());
        product.setInventory(dto.getInventory());
        return product;
    }
}
