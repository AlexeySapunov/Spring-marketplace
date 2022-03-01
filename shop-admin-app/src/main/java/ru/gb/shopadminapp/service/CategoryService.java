package ru.gb.shopadminapp.service;

import org.springframework.data.domain.Page;
import ru.gb.shopadminapp.controller.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<CategoryDto> findAll();

    Page<CategoryDto> findAll(Integer page, Integer size, String sort);

    Optional<CategoryDto> findById(Long id);

    void save(CategoryDto category);

    void deleteById(Long id);
}
