package ru.gb.service;

import org.springframework.data.domain.Page;
import ru.gb.controller.dto.UserListParams;
import ru.gb.controller.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> findAll();

    Page<UserDto> findWithFilter(UserListParams userListParams);

    Optional<UserDto> findById(Long id);

    void save(UserDto userDto);

    void deleteById(Long id);
}
