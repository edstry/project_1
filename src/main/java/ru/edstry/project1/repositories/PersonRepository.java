package ru.edstry.project1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edstry.project1.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
}
