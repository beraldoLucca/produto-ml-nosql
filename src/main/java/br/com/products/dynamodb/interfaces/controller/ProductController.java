package br.com.products.dynamodb.interfaces.controller;

import br.com.products.dynamodb.app.dto.ProductResponseDTO;
import br.com.products.dynamodb.infra.db.model.Produtos;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final DynamoDbTemplate dynamoDbTemplate;

    public ProductController(DynamoDbTemplate dynamoDbTemplate) {
        this.dynamoDbTemplate = dynamoDbTemplate;
    }

    @GetMapping("/produto/{name}")
    public ResponseEntity<ProductResponseDTO> getProductByName(@PathVariable("name") String name){

        var key = Key.builder().partitionValue(name).build();

        var condition = QueryConditional.keyEqualTo(key);

        var query = QueryEnhancedRequest.builder()
                .queryConditional(condition)
                .build();
        var produto = dynamoDbTemplate.query(query, Produtos.class);
        var produtoDto = ProductResponseDTO.from(produto.items().stream().findFirst().get());
        return ResponseEntity.ok(produtoDto);
    }

}
