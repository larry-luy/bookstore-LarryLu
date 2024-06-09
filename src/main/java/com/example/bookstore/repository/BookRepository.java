package com.example.bookstore.repository;

import com.example.bookstore.model.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("BookRepository")
public interface BookRepository extends JpaRepository<BookEntity, String> {
    @Query("SELECT B FROM BookEntity B where B.title = :title")
    List<BookEntity> findByTitle(@Param("title") String title);

    @Query("SELECT b FROM BookEntity b JOIN b.authors a WHERE a.name = :authorName")
    List<BookEntity> findByAuthorsName(String authorName);

    @Query("SELECT b FROM BookEntity b JOIN b.authors a WHERE b.title = :title AND a.name = :name")
    List<BookEntity> findBooksByTitleAndAuthor(@Param("title") String title, @Param("name") String name);

    @Modifying
    @Query("DELETE from BookEntity b WHERE b.isbn = :isbn")
    void deleteById(@Param("isbn") String isbn);
}
