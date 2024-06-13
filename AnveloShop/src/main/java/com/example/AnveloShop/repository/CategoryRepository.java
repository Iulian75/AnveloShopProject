package com.example.AnveloShop.repository;

import com.example.AnveloShop.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}

