package ru.gb.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.persist.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
