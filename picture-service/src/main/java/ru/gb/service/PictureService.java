package ru.gb.service;

import java.util.Optional;

public interface PictureService {

    Optional<String> getPictureContentType(long id);

    Optional<byte[]> getPictureDataById(long id);

    String createPicture(byte[] pictureData);

    void deleteById(Long id);
}
