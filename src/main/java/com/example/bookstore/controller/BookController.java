package com.example.bookstore.controller;

import com.example.bookstore.model.dto.BookDTO;
import com.example.bookstore.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BookDTO book) throws Exception {
        LOGGER.info("BookController :: addBook ");
        BookDTO savedBook =  bookService.saveBook(book);

        if (savedBook.getErrorMsg() != null && !savedBook.getErrorMsg().isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving book: " + savedBook.getErrorMsg());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Book has been added");
        }

    }

    @PutMapping("/update/{isbn}")
    public ResponseEntity<?> updateBook(@PathVariable String isbn, @RequestBody BookDTO book) {
        LOGGER.info("BookController :: updateBook ");
        book.setIsbn(isbn);
        BookDTO updatedBook =  bookService.updateBook(book);

        if (updatedBook.getErrorMsg() != null && !updatedBook.getErrorMsg().isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while updating book: " + updatedBook.getErrorMsg());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Book ISBN: " +isbn+ ", has been updated");
        }

    }



    @GetMapping("/get")
    public ResponseEntity<?> findBooks(@RequestParam(required = false) String title,
                                                      @RequestParam(required = false) String authorName) {
        LOGGER.info("BookController :: findBooks ");
        List<BookDTO> books = new ArrayList<>();
        if(title !=null && authorName !=null){
            books = bookService.findBooksByTitleAndAuthor(title, authorName);

        } else if (title != null ) {
            books = bookService.findBooksByTitle(title);
        } else if (authorName != null) {
            books = bookService.findBooksByAuthor(authorName);
        }

        if(!books.isEmpty()){
            LOGGER.info("Books found");
            return ResponseEntity.ok(books);
        }else {
            LOGGER.info("No Books found");
            return ResponseEntity.badRequest().body("No books found matching the criteria.");
        }
    }

    //@Secured have prefix of ROLE_
    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/deleteById/{isbn}")
    public ResponseEntity<?> deleteBook(@PathVariable String isbn) {
        LOGGER.info("BookController :: deleteBook ");

        BookDTO bookDTO = bookService.deleteBook(isbn);
        if (bookDTO.getErrorMsg() != null && !bookDTO.getErrorMsg().isEmpty()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while deleting book: " + bookDTO.getErrorMsg());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(" Book with ISBN " + isbn + " has been deleted successfully");
        }

    }

}
