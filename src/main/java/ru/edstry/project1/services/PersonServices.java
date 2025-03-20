package ru.edstry.project1.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.edstry.project1.models.Book;
import ru.edstry.project1.models.Person;
import ru.edstry.project1.repositories.PersonRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonServices {
    private final PersonRepository personRepository;

    @Autowired
    public PersonServices(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        return personRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Person getPersonById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person) {
        personRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        personRepository.save(person);
    }

    public Optional<Person> findPersonByFio(String fio) {
        return personRepository.findByFio(fio);
    }

    @Transactional
    public void delete(int id) {
        personRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Book> getBookByPersonId(int personId) {
        Optional<Person> person = personRepository.findById(personId);
        if(person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            // Проверка просроченна ли книга
            person.get().getBooks().forEach(book -> {
                long diffInMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if(diffInMillies > 864000000) {
                    book.setExpired(true);
                }
            });
            return person.get().getBooks();
        }
        return Collections.emptyList();
    }
}
