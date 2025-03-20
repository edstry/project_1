package ru.edstry.project1.util;

import jakarta.persistence.Transient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.edstry.project1.models.Person;
import ru.edstry.project1.services.PersonServices;

@Component
public class PersonValidator implements Validator {

    private final PersonServices personService;

    @Autowired
    public PersonValidator(PersonServices personService) {
        this.personService = personService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(personService.findPersonByFio(person.getFio()).isPresent()) {
            errors.rejectValue("fio", "", "Fio already exists");
        }
    }
}
