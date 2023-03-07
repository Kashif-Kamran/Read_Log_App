package io.javabrains.book;

import io.javabrains.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService
{
    BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository)
    {
        this.bookRepository = bookRepository;
    }

    public Optional<Book> getBookById(String bookId)
    {
        return bookRepository.findById(bookId);


    }
    public void addBook(Book newBook)
    {
        this.bookRepository.save(newBook);
    }

}
