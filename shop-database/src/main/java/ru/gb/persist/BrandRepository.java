package ru.gb.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.persist.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Long> {
}
