package ru.gb.shopadminapp.controller.dto;

import java.io.Serializable;
import java.util.Objects;

public class BrandDto {

    private Long id;

    private String name;

    public BrandDto() {
    }

    public BrandDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandDto brandDto = (BrandDto) o;
        return name.equals(brandDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
