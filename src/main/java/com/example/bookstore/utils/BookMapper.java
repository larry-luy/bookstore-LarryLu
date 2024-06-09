package com.example.bookstore.utils;


import com.example.bookstore.model.dto.AuthorDTO;
import com.example.bookstore.model.dto.BookDTO;
import com.example.bookstore.model.entity.AuthorEntity;
import com.example.bookstore.model.entity.BookEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class BookMapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookMapper.class);

    public static BookDTO toDTO(BookEntity bookEntity){
        if (bookEntity == null) {
            LOGGER.info("No Entity to Map");
            return null;
        }
        LOGGER.info("Converting Entity to DTO");
        BookDTO bookDTO = new BookDTO();
        bookDTO.setIsbn(bookEntity.getIsbn());
        bookDTO.setTitle(bookEntity.getTitle());
        bookDTO.setYear(bookEntity.getPublicationYear()); // Map publicationYear to year
        bookDTO.setPrice(bookEntity.getPrice());
        bookDTO.setGenre(bookEntity.getGenre());

        List<AuthorDTO> authors = bookEntity.getAuthors().stream().map(AuthorMapper::toDTO).collect(Collectors.toList());
        bookDTO.setAuthors(authors);


        return bookDTO;
    }

    public static BookEntity toEntity(BookDTO bookDTO) {
        if (bookDTO == null) {
            LOGGER.info("No DTO to Map");
            return null;
        }
        LOGGER.info("Converting DTO to Entity");
        BookEntity bookEntity = new BookEntity();
        bookEntity.setIsbn(bookDTO.getIsbn());
        bookEntity.setTitle(bookDTO.getTitle());
        bookEntity.setPublicationYear(bookDTO.getYear()); // Map year to publicationYear
        bookEntity.setPrice(bookDTO.getPrice());
        bookEntity.setGenre(bookDTO.getGenre());

        List<AuthorEntity> authors = bookDTO.getAuthors().stream().map(AuthorMapper::toEntity).collect(Collectors.toList());
        bookEntity.setAuthors(authors);
        return bookEntity;
    }


}
