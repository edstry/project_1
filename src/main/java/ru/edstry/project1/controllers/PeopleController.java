package ru.edstry.project1.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.edstry.project1.dao.BookDAO;
import ru.edstry.project1.dao.PeopleDAO;
import ru.edstry.project1.models.Book;
import ru.edstry.project1.models.Person;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleDAO peopleDao;
    private final BookDAO bookDao;

    @Autowired
    public PeopleController(PeopleDAO peopleDao, BookDAO bookDao) {
        this.peopleDao = peopleDao;
        this.bookDao = bookDao;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleDao.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int personId, Model model) {
        model.addAttribute("person", peopleDao.getPerson(personId));
        List<Book> list = peopleDao.getBookList(personId);
        model.addAttribute("books", list);
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleDao.getPerson(id));
        return "people/edit";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping("/create")
    public String createPerson(
            @ModelAttribute("person") @Valid Person person,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "people/new";
        } else {
            peopleDao.save(person);
            return "redirect:/people";
        }
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        peopleDao.delete(id);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") int id,
                               @ModelAttribute("person") @Valid Person person,
                               BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "people/edit";
        } else {
            peopleDao.update(id, person);
            return "redirect:/people";
        }
    }
}
