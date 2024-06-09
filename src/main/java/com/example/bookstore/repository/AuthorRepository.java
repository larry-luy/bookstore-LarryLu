package com.example.bookstore.repository;
import com.example.bookstore.model.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;


@Repository("AuthorRepository")
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long>  {

    @Query("SELECT A FROM AuthorEntity A WHERE A.name = :name AND A.birthday = :birthday")
    Optional<AuthorEntity> findByNameAndBirthday(@Param("name") String name, @Param("birthday") Date birthday);

}
