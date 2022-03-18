package ru.gb.controller;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.gb.controller.dto.BrandDto;

@Component
public class StringToBrandDtoConverter implements Converter<String, BrandDto> {

    @Override
    public BrandDto convert(String id) {
        return new BrandDto(Long.parseLong(id));
    }
}
