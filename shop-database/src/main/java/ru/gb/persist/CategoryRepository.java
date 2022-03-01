package ru.gb.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.gb.persist.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {
}
