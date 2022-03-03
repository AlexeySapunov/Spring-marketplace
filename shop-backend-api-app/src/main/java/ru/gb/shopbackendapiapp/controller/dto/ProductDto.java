package ru.gb.shopbackendapiapp.controller.dto;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class ProductDto {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private CategoryDto category;

    private MultipartFile[] newPicture;

    private List<Long> pictures;

    private Set<BrandDto> brands;

    public ProductDto(Long id, String name, String description, BigDecimal price, CategoryDto category, List<Long> pictures, Set<BrandDto> brands) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.pictures = pictures;
        this.brands = brands;
    }

    public ProductDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public MultipartFile[] getNewPicture() {
        return newPicture;
    }

    public void setNewPicture(MultipartFile[] newPicture) {
        this.newPicture = newPicture;
    }

    public List<Long> getPictures() {
        return pictures;
    }

    public void setPictures(List<Long> pictures) {
        this.pictures = pictures;
    }

    public Set<BrandDto> getBrands() {
        return brands;
    }

    public void setBrands(Set<BrandDto> brands) {
        this.brands = brands;
    }
}
