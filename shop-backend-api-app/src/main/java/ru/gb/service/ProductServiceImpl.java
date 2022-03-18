package ru.gb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.gb.controller.dto.BrandDto;
import ru.gb.controller.dto.CategoryDto;
import ru.gb.persist.ProductRepository;
import ru.gb.persist.ProductSpecification;
import ru.gb.persist.model.Picture;
import ru.gb.persist.model.Product;
import ru.gb.controller.dto.ProductDto;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<ProductDto> findAll(Optional<Long> brandId, Optional<Long> categoryId, Optional<String> nameFilter,
                                    Integer page, Integer size, String sort) {

        Specification<Product> spec = Specification.where(null);
        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            spec = spec.and(ProductSpecification.nameLike(nameFilter.get()));
        }

        if (categoryId.isPresent() && categoryId.get() != -1) {
            spec = spec.and(ProductSpecification.byCategory(categoryId.get()));
        }

        if (brandId.isPresent() && brandId.get() != -1) {
            spec = spec.and(ProductSpecification.byBrand(brandId.get()));
        }

        return productRepository.findAll(spec,
                        PageRequest.of(page, size, Sort.by(sort)))
                .map(ProductServiceImpl::convertToDto);
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(ProductServiceImpl::convertToDto);
    }

    private static ProductDto convertToDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                new CategoryDto(product.getCategory().getId(),
                        product.getCategory().getName()),
                product.getPictures()
                        .stream()
                        .map(Picture::getId)
                        .collect(Collectors.toList()),
                new BrandDto(product.getBrand().getId(),
                        product.getBrand().getName())
        );
    }
}
