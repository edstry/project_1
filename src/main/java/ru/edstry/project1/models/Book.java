package ru.edstry.project1.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public class Book {
    private int id;
    @NotEmpty(message = "title should not be empty")
    @Size(max = 50, message = "title should not be greater then 50 characters")
    private String title;
    @Size(max = 50, message = "author should not be greater then 50 characters")
    @NotEmpty(message = "author should not be empty")
    private String author;
    @Min(value = 1000, message = "Year should be greater then 1000")
    private int year;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
