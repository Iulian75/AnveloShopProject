package com.example.AnveloShop.api;

import com.example.AnveloShop.domain.UserCart;
import com.example.AnveloShop.dto.UserCartDto;
import com.example.AnveloShop.repository.ProductRepository;
import com.example.AnveloShop.repository.UserCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carts")
    public class UserCartController {

        @Autowired
        private UserCartRepository userCartRepository;
        @Autowired
        private ProductRepository productRepository;

        @PostMapping
        public UserCartDto createUserCart(@RequestBody UserCartDto userCartDto) {
            UserCart cart = new UserCart();
            cart.setCustomerName(userCartDto.getCustomerName());
            cart.setProducts(userCartDto.getProductIds().stream()
                    .map(productRepository::findById)
                    .map(product -> product.orElseThrow())
                    .collect(Collectors.toList()));
            cart = userCartRepository.save(cart);
            userCartDto.setId(cart.getId());
            return userCartDto;
        }

        @GetMapping
        public List<UserCartDto> getAllCarts() {
            return userCartRepository.findAll().stream().map(cart -> {
                UserCartDto dto = new UserCartDto();
                dto.setId(cart.getId());
                dto.setCustomerName(cart.getCustomerName());
                dto.setProductIds(cart.getProducts().stream().map(product -> product.getId()).collect(Collectors.toList()));
                return dto;
            }).collect(Collectors.toList());
        }

        @PutMapping("/{id}")
        public UserCartDto updateUserCart(@PathVariable Long id, @RequestBody UserCartDto userCartDto) {
            UserCart cart = userCartRepository.findById(id).orElseThrow();
            cart.setCustomerName(userCartDto.getCustomerName());
            cart.setProducts(userCartDto.getProductIds().stream()
                    .map(productRepository::findById)
                    .map(product -> product.orElseThrow())
                    .collect(Collectors.toList()));
            cart = userCartRepository.save(cart);
            userCartDto.setId(cart.getId());
            return userCartDto;
        }

        @DeleteMapping("/{id}")
        public void deleteUserCart(@PathVariable Long id) {
            userCartRepository.deleteById(id);
        }
    }

