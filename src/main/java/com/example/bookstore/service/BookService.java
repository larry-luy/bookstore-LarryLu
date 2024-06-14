package com.example.bookstore.service;

import com.example.bookstore.model.dto.BookDTO;

import java.util.List;

public interface BookService {
    BookDTO saveBook (BookDTO book);
    BookDTO updateBook (BookDTO book);
    List<BookDTO> findBooksByTitle(String title);
    List<BookDTO> findBooksByAuthor(String authorName);
    List<BookDTO> findBooksByTitleAndAuthor(String title, String authorName);
    BookDTO deleteBook(String isbn);

}
