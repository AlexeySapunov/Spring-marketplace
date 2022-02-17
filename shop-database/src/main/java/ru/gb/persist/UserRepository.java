package ru.gb.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.persist.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
