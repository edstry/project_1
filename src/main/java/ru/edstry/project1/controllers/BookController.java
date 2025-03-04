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

import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookDAO bookDAO;
    private final PeopleDAO peopleDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PeopleDAO peopleDAO) {
        this.bookDAO = bookDAO;
        this.peopleDAO = peopleDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("books", bookDAO.index());
        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(
            @PathVariable("id") int id,
            Model model,
            @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookDAO.getBook(id));
        Optional<Person> bookOwner = bookDAO.getBookOwner(id);
        if(bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", peopleDAO.index());
        }
        return "book/show";
    }

    @GetMapping("/new")
    public String showNew(Model model) {
        model.addAttribute("book", new Book());
        return "book/new";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute("book") @Valid Book book,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "book/new";
        } else {
            bookDAO.save(book);
            return "redirect:/book";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String updateBook(
            @PathVariable("id") int id,
            @ModelAttribute("book") @Valid Book book,
            BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "book/new";
        } else {
            bookDAO.update(id, book);
            return "redirect:/book/" + book.getId();
        }
    }

    @PatchMapping("/{id}/assign")
    public String assign(
            @PathVariable("id") int bookId,
            @ModelAttribute("person") Person selectedPerson) {
        bookDAO.assign(bookId, selectedPerson);
        return "redirect:/book/" + bookId;
    }

    @PatchMapping("/{id}/release")
    public String release(
            @PathVariable("id") int bookId) {
        bookDAO.release(bookId);
        return "redirect:/book/" + bookId;
    }
}
