package com.example.bookstore.service;

import com.example.bookstore.model.dto.BookDTO;

import java.util.List;

public interface BookService {
    public BookDTO saveBook (BookDTO book);
    public BookDTO updateBook (BookDTO book);
    public List<BookDTO> findBooksByTitle(String title);
    public List<BookDTO> findBooksByAuthor(String authorName);
    List<BookDTO> findBooksByTitleAndAuthor(String title, String authorName);
    public BookDTO deleteBook(String isbn);

}
