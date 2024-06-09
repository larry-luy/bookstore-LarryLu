package com.example.bookstore.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private String isbn;
    private String title;
    private int year;
    private double price;
    private String genre;
    private List<AuthorDTO> authors;
    private String errorMsg;

}
