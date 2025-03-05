package ru.edstry.project1.dao;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.edstry.project1.models.Book;
import ru.edstry.project1.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PeopleDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person ORDER BY id", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPerson(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id = ?",
                new BeanPropertyRowMapper<>(Person.class),
                id);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(fio, year_of_birth) VALUES (?, ?)",
                person.getFio(),
                person.getYear_of_birth());
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET fio=?, year_of_birth=? WHERE id =?",
                person.getFio(),
                person.getYear_of_birth(),
                id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    public List<Book> getBookList(int personId) {
        String sql = "SELECT book.* FROM book JOIN person ON book.person_id = person.id WHERE person.id =?;";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Book.class), personId);
    }

}
