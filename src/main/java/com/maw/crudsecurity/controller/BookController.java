package com.maw.crudsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maw.crudsecurity.Service.BookService;
import com.maw.crudsecurity.entity.Book;

@Controller
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/bookList")
    public String testApi(ModelMap model) {
        model.addAttribute("bookList", bookService.findAllBooks());
        return "/book/index";
    }

    @GetMapping("/addBook")
    public String setupAddBook(ModelMap model) {
        model.addAttribute("book", new Book());
        return "/book/book-registration";
    }

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.addBook(book);
        return "redirect:/book/bookList";
    }

    @GetMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return "redirect:/book/bookList";
    }

    @GetMapping("/updateBook/{id}")
    public String setupUpdateBook(@PathVariable("id") Long id, ModelMap model) {
        Book resBook = bookService.findBookById(id);
        Book book = new Book();
        book.setId(resBook.getId());
        book.setName(resBook.getName());
        book.setAuthor(resBook.getAuthor());
        book.setPrice(resBook.getPrice());
        model.addAttribute("book", book);
        return "/book/book-update";
    }

    @PostMapping("/updateBook")
    public String updateBook(@ModelAttribute("book") Book book) {
        bookService.deleteBook(book.getId());
        bookService.addBook(book);
        return "redirect:/book/bookList";
    }

    @GetMapping("/403")
    public String showError403() {
        return "/book/403";
    }

}
