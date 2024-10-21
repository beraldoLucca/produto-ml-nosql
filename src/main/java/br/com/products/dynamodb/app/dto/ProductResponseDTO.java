package br.com.products.dynamodb.app.dto;

import br.com.products.dynamodb.infra.db.model.Produtos;

import java.math.BigDecimal;

public record ProductResponseDTO(String name, String description, BigDecimal price) {

    public static ProductResponseDTO from(Produtos produtos){
        return new ProductResponseDTO(produtos.getName(), produtos.getDescription(), produtos.getPrice());
    }
}