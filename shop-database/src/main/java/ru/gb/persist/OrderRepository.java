package ru.gb.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.persist.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
