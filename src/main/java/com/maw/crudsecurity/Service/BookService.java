package com.maw.crudsecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maw.crudsecurity.entity.Book;
import com.maw.crudsecurity.repository.BookRepository;

@Service
public class BookService {
    @Autowired
    BookRepository bookRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public void addBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public Book findBookById(Long id) {
        return bookRepository.findById(id).get();
    }


}
