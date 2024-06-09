package com.example.bookstore.utils;

import com.example.bookstore.model.dto.AuthorDTO;
import com.example.bookstore.model.entity.AuthorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorMapper.class);


    public static AuthorEntity toEntity(AuthorDTO authorDTO) {
        if (authorDTO == null) {
            LOGGER.info("No Entity to Map");
            return null;
        }

        LOGGER.info("Converting DTO to Entity");
        AuthorEntity author = new AuthorEntity();
        author.setName(authorDTO.getName());
        author.setBirthday(authorDTO.getBirthday());
        return author;
    }

    public static AuthorDTO toDTO(AuthorEntity authorEntity) {
        if (authorEntity == null) {
            LOGGER.info("No Entity to Map");
            return null;
        }
        LOGGER.info("Converting Entity to DTO");
        AuthorDTO authorDTO = new AuthorDTO();
        authorDTO.setName(authorEntity.getName());
        authorDTO.setBirthday(authorEntity.getBirthday());
        return authorDTO;
    }

}
