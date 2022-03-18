package ru.gb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.controller.NotFoundException;
import ru.gb.controller.dto.BrandDto;
import ru.gb.controller.dto.ProductDto;
import ru.gb.persist.BrandRepository;
import ru.gb.persist.CategoryRepository;
import ru.gb.persist.ProductRepository;
import ru.gb.persist.ProductSpecification;
import ru.gb.persist.model.Brand;
import ru.gb.persist.model.Category;
import ru.gb.persist.model.Picture;
import ru.gb.persist.model.Product;
import ru.gb.controller.dto.CategoryDto;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final BrandRepository brandRepository;

    private final PictureService pictureService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              BrandRepository brandRepository, PictureService pictureService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.brandRepository = brandRepository;
        this.pictureService = pictureService;
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

    @Override
    @Transactional
    public void save(ProductDto productDto) {
        Product product = (productDto.getId() != null) ? productRepository.findById(productDto.getId())
                .orElseThrow(() -> new NotFoundException("")) : new Product();
        Category category = categoryRepository.findById(productDto.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        Brand brand = brandRepository.findById(productDto.getBrand().getId())
                        .orElseThrow(() -> new RuntimeException("Brand not found"));

        product.setName(productDto.getName());
        product.setCategory(category);
        product.setBrand(brand);
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());

        if (productDto.getNewPicture() != null) {
            for (MultipartFile newPicture: productDto.getNewPicture()) {
                try {
                    product.getPictures().add(new Picture(null,
                            newPicture.getOriginalFilename(),
                            newPicture.getContentType(),
                            pictureService.createPicture(newPicture.getBytes()),
                            product));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
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
