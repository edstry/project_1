package ru.edstry.project1.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.edstry.project1.models.Book;
import ru.edstry.project1.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBook(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id=?",
                new BeanPropertyRowMapper<>(Book.class),
                id);
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, year) VALUES (?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getYear());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title=?, author=?, year=? WHERE id=?",
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                id
        );
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    // Join'им таблицы Book и Person и получаем человека, которому принадлежит книга с указанным id
    public Optional<Person> getBookOwner(int id) {
        String sql = "SELECT person.* FROM book JOIN person ON book.person_id = person.id WHERE book.id=?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Person.class), id)
                .stream()
                .findAny();
    }

    // Присоединяем человека к книге
    public void assign(int id, Person selectedPerson) {
        jdbcTemplate.update("UPDATE book SET person_id=? WHERE book.id=?", selectedPerson.getId(), id);
    }

    // Освобождаем книгу от человека
    public void release(int id) {
        jdbcTemplate.update("UPDATE book SET person_id=null WHERE id=?", id);
    }
}
