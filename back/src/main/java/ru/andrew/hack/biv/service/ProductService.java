package ru.andrew.hack.biv.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.andrew.hack.biv.dto.ProductDto;
import ru.andrew.hack.biv.model.Product;
import ru.andrew.hack.biv.repos.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDto getProductById(String id) {
        var result = productRepository.findById(id).orElse(null);
        if (result == null) {
            return null;
        }
        return ProductDto.builder()
                .id(result.getId())
                .name(result.getName())
                .description(result.getDescription())
                .build();
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(i -> ProductDto.builder()
                .id(i.getId())
                .name(i.getName())
                .description(i.getDescription())
                .build()
        ).collect(Collectors.toList());
    }

    public String createProduct(ProductDto product) {
        var productToSave = Product.builder()
                .name(product.getName())
                .description(product.getDescription())
                .build();
        productRepository.save(productToSave);
        return productToSave.getId();
    }

    public String updateProduct(String id, ProductDto product) {
        var productToUpdate = productRepository.findById(id);
        productToUpdate.ifPresent(i -> {
            i.setName(product.getName());
            i.setDescription(product.getDescription());
        });
        return id;
    }

    public String deleteProduct(String id) {
        productRepository.deleteById(id);
        return id;
    }
}
