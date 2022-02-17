package ru.gb.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.persist.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
