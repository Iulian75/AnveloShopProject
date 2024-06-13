package com.example.AnveloShop.api;

import com.example.AnveloShop.domain.Product;
import com.example.AnveloShop.dto.ProductDto;
import com.example.AnveloShop.repository.CategoryRepository;
import com.example.AnveloShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        Product product = new Product();
        product.setBrand(productDto.getBrand());
        product.setPrice(productDto.getPrice());
        product.setWidth(productDto.getWidth());
        product.setHeight(productDto.getHeight());
        product.setRim(productDto.getRim());
        product.setCategory(categoryRepository.findById(productDto.getCategoryId()).orElseThrow());
        product = productRepository.save(product);
        productDto.setId(product.getId());
        return productDto;
    }

    @GetMapping
    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(product -> {
            ProductDto dto = new ProductDto();
            dto.setId(product.getId());
            dto.setBrand(product.getBrand());
            dto.setPrice(product.getPrice());
            dto.setWidth(product.getWidth());
            dto.setHeight(product.getHeight());
            dto.setRim(product.getRim());
            dto.setCategoryId(product.getCategory().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ProductDto updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setBrand(productDto.getBrand());
        product.setPrice(productDto.getPrice());
        product.setWidth(productDto.getWidth());
        product.setHeight(productDto.getHeight());
        product.setRim(productDto.getRim());
        product.setCategory(categoryRepository.findById(productDto.getCategoryId()).orElseThrow());
        product = productRepository.save(product);
        productDto.setId(product.getId());
        return productDto;
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    @GetMapping("/search")
    public List<ProductDto> findProductsByCriteria(@RequestParam(value = "brand", required = false) String brand,
                                                   @RequestParam(value = "minPrice", required = false) Double minPrice,
                                                   @RequestParam(value = "maxPrice", required = false) Double maxPrice,
                                                   @RequestParam(value = "categoryId", required = false) Long categoryId) {
        List<Product> products = productRepository.findProductsByCriteria(brand, minPrice, maxPrice, categoryId);
        return products.stream().map(product -> {
            ProductDto dto = new ProductDto();
            dto.setId(product.getId());
            dto.setBrand(product.getBrand());
            dto.setPrice(product.getPrice());
            dto.setWidth(product.getWidth());
            dto.setHeight(product.getHeight());
            dto.setRim(product.getRim());
            dto.setCategoryId(product.getCategory().getId());
            return dto;
        }).collect(Collectors.toList());
    }
}


