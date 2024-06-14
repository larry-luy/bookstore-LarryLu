package com.example.bookstore.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "BOOK")
public class BookEntity {
    @Id
    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "PUBLICATION_YEAR")
    private int publicationYear;

    @Column(name = "PRICE")
    private double price;

    @Column(name = "GENRE")
    private String genre;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_AUTHOR",
            joinColumns = @JoinColumn(name = "BOOK_ISBN", referencedColumnName = "ISBN"),
            inverseJoinColumns = @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "ID"))
    private List<AuthorEntity> authors;

}
