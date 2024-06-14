package com.example.bookstore.service;

import com.example.bookstore.model.dto.AuthorDTO;
import com.example.bookstore.model.dto.BookDTO;
import com.example.bookstore.model.entity.AuthorEntity;
import com.example.bookstore.model.entity.BookEntity;
import com.example.bookstore.repository.AuthorRepository;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.utils.AuthorMapper;
import com.example.bookstore.utils.BookMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService{
    private static final Logger LOGGER = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private AuthorRepository authorRepo;


    @Override
    public BookDTO saveBook(BookDTO book){
        BookDTO savedBook = new BookDTO();
        try{
            if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
                savedBook.setErrorMsg("ISBN must be provided");
                return savedBook;
            }

            LOGGER.info("saveBook :: Check for existing books ");
            Optional<BookEntity> existingBook = bookRepo.findById(book.getIsbn());

            if (existingBook.isPresent()) {
                LOGGER.info("saveBook :: Existing Books Found");
                savedBook.setErrorMsg("Unable to save a record due to an existing book found");
            } else {
                // Save new book
                LOGGER.info("saveBook:: No existing book. proceed to save as new book");
                BookEntity newBook = BookMapper.toEntity(book);
                newBook.setAuthors(checkExistingAuthors(book.getAuthors()));
                bookRepo.save(newBook);
            }

        }catch(Exception e){
            LOGGER.error("Exception error while saving book" + e);
            savedBook.setErrorMsg("Unexpected error occurred while saving book. Please try again.");
        }
        return savedBook;
    }


    @Override
    public BookDTO updateBook(BookDTO book){
        try{
            if (book.getIsbn() == null || book.getIsbn().isEmpty()) {
                book.setErrorMsg("ISBN must be provided");
                return book;
            }

            LOGGER.info("updateBook :: Check for existing books");
            Optional<BookEntity> existingBookOpt = bookRepo.findById(book.getIsbn());

            if (existingBookOpt.isPresent()) {
                LOGGER.info("updateBook :: Existing books found");
                // Update existing book
                BookEntity existingBook = existingBookOpt.get();
                existingBook.setTitle(book.getTitle());
                existingBook.setPublicationYear(book.getYear());
                existingBook.setPrice(book.getPrice());
                existingBook.setGenre(book.getGenre());

                List<AuthorEntity> authorList =  checkExistingAuthors(book.getAuthors());
                existingBook.setAuthors(authorList);
                bookRepo.save(existingBook);

            } else {
                LOGGER.info("updateBook :: No existing book found");
                book.setErrorMsg("No record of existing book to update");
            }
        }catch(Exception e){
            LOGGER.error("updateBook :: Exception error while updating book" + e);
            book.setErrorMsg("Unexpected error occurred while updating book. Please try again.");
        }
        return book;
    }



    //Check if there are any existing authors with the same and birthday
    private List<AuthorEntity> checkExistingAuthors(List<AuthorDTO> authorDTOList) {
        LOGGER.info("======= Inside checkExistingAuthors =======");
        List<AuthorEntity> authors = new ArrayList<>();
        for (AuthorDTO authorDTO : authorDTOList) {
            Optional<AuthorEntity> existingAuthor = authorRepo.findByNameAndBirthday(authorDTO.getName(), authorDTO.getBirthday());

            if (existingAuthor.isPresent()) {
                LOGGER.info("Existing Author Present");
                authors.add(existingAuthor.get());
            } else {
                LOGGER.info("Creating new  Author");
                AuthorEntity newAuthor = AuthorMapper.toEntity(authorDTO);
                authors.add(authorRepo.save(newAuthor));
            }
        }
        return authors;
    }

    @Override
    public List<BookDTO> findBooksByTitle(String title) {
        LOGGER.info("Retrieve Books By Title: " + title);
        try{
            return bookRepo.findByTitle(title).stream().map(BookMapper::toDTO).collect(Collectors.toList());
        }catch(Exception e){
            LOGGER.error("Exception error while retrieving Books by Title " + e);
            return Collections.emptyList();
        }

    }

    @Override
    public List<BookDTO> findBooksByAuthor(String authorName) {
        LOGGER.info("Retrieve Books By Author: " + authorName);
        try{
            return bookRepo.findByAuthorsName(authorName).stream().map(BookMapper::toDTO).collect(Collectors.toList());
        }catch(Exception e){
            LOGGER.error("Exception error while retrieving Books by AuthorName " + e);
            return Collections.emptyList();
        }

    }

    @Override
    public List<BookDTO> findBooksByTitleAndAuthor(String title, String authorName) {
        LOGGER.info("Retrieve Books By Title: " +title + " and  Author: " + authorName);
        try{
            return bookRepo.findBooksByTitleAndAuthor(title, authorName).stream().map(BookMapper::toDTO).collect(Collectors.toList());
        }catch(Exception e){
            LOGGER.error("Exception error while retrieving Books by AuthorName " + e);
            return Collections.emptyList();
        }

    }


    @Transactional
    @Override
    public BookDTO deleteBook(String isbn) {
        LOGGER.info("Attempting to delete a book by isbn: " + isbn);
        BookDTO book = new BookDTO();
        try{
            //Check if the isbn exists.
            Optional<BookEntity> retrievedBook = bookRepo.findById(isbn);

            if(retrievedBook.isPresent()){
                bookRepo.deleteById(isbn);
                LOGGER.info("Book with ISBN " + isbn + " deleted successfully");
            }else{
                LOGGER.warn("Book with ISBN " + isbn + " does not exist");
                book.setErrorMsg("Book with ISBN " + isbn + " does not exist");
            }

        }catch(Exception e){
            LOGGER.error("Exception error while deleting Books by isbn " + e);
            book.setErrorMsg("Error while deleting Books with ISBN " + isbn );
        }

        return book;
    }

}
