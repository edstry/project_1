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
import ru.edstry.project1.services.BookServices;
import ru.edstry.project1.services.PersonServices;

import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {

    private final BookServices bookServices;
    private final PersonServices personServices;

    @Autowired
    public BookController(BookServices bookServices, PersonServices personServices) {
        this.bookServices = bookServices;
        this.personServices = personServices;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "book/search";
    }

    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query) {
        model.addAttribute("books", bookServices.searchByTitle(query));
        return "books/search";
    }

    @GetMapping()
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) boolean sortByYear) {
        if(page == null || booksPerPage == null) {
            model.addAttribute("books", bookServices.index(sortByYear));
        } else {
            model.addAttribute("books", bookServices.indexWithPagination(page, booksPerPage, sortByYear));
        }

        return "book/index";
    }

    @GetMapping("/{id}")
    public String show(
            @PathVariable("id") int id,
            Model model,
            @ModelAttribute("person") Person person) {
        model.addAttribute("book", bookServices.getBookById(id));
        Optional<Person> bookOwner = bookServices.getBookOwner(id);
        if(bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", personServices.index());
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
            bookServices.save(book);
            return "redirect:/book";
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookServices.delete(id);
        return "redirect:/book";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookServices.getBookById(id));
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
            bookServices.update(id, book);
            return "redirect:/book/" + book.getId();
        }
    }

    @PatchMapping("/{id}/assign")
    public String assign(
            @PathVariable("id") int bookId,
            @ModelAttribute("person") Person selectedPerson) {
        bookServices.assign(bookId, selectedPerson);
        return "redirect:/book/" + bookId;
    }

    @PatchMapping("/{id}/release")
    public String release(
            @PathVariable("id") int bookId) {
        bookServices.release(bookId);
        return "redirect:/book/" + bookId;
    }
}
