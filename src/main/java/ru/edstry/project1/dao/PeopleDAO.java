package ru.edstry.project1.dao;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.edstry.project1.models.Person;

import java.util.List;

@Component
public class PeopleDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
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


}
