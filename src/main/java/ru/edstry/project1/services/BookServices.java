package ru.edstry.project1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edstry.project1.models.Book;
import ru.edstry.project1.models.Person;
import ru.edstry.project1.repositories.BookRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookServices {
    private final BookRepository bookRepository;

    @Autowired
    public BookServices(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> index(boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(Sort.by("year"));
        } else {
            return bookRepository.findAll();
        }
    }

    public Object indexWithPagination(Integer page, Integer booksPerPage, boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("year")));
        } else {
            return bookRepository.findAll(PageRequest.of(page, booksPerPage));
        }
    }

    public List<Book> searchByTitle(String title) {
        return bookRepository.findByTitleStartingWith(title);
    }

    @Transactional
    public Book getBookById(int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Person> getBookOwner(int bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        return book.map(Book::getOwner);
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook) {
        // Здесь приходит объект book из формы, этот book никак не связан с hibernate
        // hibernate еще не знает о объекте book, чтобы он обновил свои значение в таблице
        // мы должны вызвать метод save(book), после hibernate увидит, что у него совпадает
        // значение с таблицей Book из бд
        Book bookToBeUpdated = bookRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner()); // чтобы не терялась связь при обновлении
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void assign(int bookId, Person selectedPerson) {
        Optional<Book> book = bookRepository.findById(bookId);
        book.ifPresent(value -> {
            value.setOwner(selectedPerson);
            value.setTakenAt(new Date());
        });
    }

    @Transactional
    public void release(int bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        book.ifPresent(value -> {
            value.setOwner(null);
            value.setTakenAt(null);
        });
    }
}

