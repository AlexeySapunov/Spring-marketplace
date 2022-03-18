package ru.gb.controller;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.gb.controller.dto.CategoryDto;

@Component
public class StringToCategoryDtoConverter implements Converter<String, CategoryDto> {

    @Override
    public CategoryDto convert(String id) {
        return new CategoryDto(Long.parseLong(id));
    }
}
