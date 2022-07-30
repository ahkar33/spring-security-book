package com.maw.crudsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maw.crudsecurity.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    
}
