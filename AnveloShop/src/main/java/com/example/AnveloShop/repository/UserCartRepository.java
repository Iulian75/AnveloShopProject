package com.example.AnveloShop.repository;

import com.example.AnveloShop.domain.UserCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCartRepository extends JpaRepository<UserCart, Long> {}

