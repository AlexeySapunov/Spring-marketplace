package ru.gb.shopbackendapiapp.service;

import org.springframework.data.domain.Page;
import ru.gb.shopbackendapiapp.controller.dto.ProductDto;

import java.util.Optional;

public interface ProductService {

    Page<ProductDto> findAll(Optional<Long> categoryId, Optional<String> nameFilter,
                             Integer page, Integer size, String sort);

    Optional<ProductDto> findById(Long id);
}
