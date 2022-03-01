package ru.gb.shopadminapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.persist.CategoryRepository;
import ru.gb.persist.ProductRepository;
import ru.gb.persist.ProductSpecification;
import ru.gb.persist.model.Category;
import ru.gb.persist.model.Picture;
import ru.gb.persist.model.Product;
import ru.gb.service.PictureService;
import ru.gb.shopadminapp.controller.NotFoundException;
import ru.gb.shopadminapp.controller.dto.BrandDto;
import ru.gb.shopadminapp.controller.dto.CategoryDto;
import ru.gb.shopadminapp.controller.dto.ProductDto;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final PictureService pictureService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              PictureService pictureService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.pictureService = pictureService;
    }

    @Override
    public Page<ProductDto> findAll(Optional<Long> categoryId, Optional<String> nameFilter,
                                    Integer page, Integer size, String sort) {

        Specification<Product> spec = Specification.where(null);
        if (nameFilter.isPresent() && !nameFilter.get().isBlank()) {
            spec = spec.and(ProductSpecification.nameLike(nameFilter.get()));
        }

        if (categoryId.isPresent() && categoryId.get() != -1) {
            spec = spec.and(ProductSpecification.byCategory(categoryId.get()));
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

        product.setName(productDto.getName());
        product.setCategory(category);
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
                product.getBrands()
                        .stream()
                        .map(brand -> new BrandDto(brand.getId(), brand.getName()))
                        .collect(Collectors.toSet())
        );
    }
}
