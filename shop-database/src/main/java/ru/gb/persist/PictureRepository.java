package ru.gb.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.persist.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
