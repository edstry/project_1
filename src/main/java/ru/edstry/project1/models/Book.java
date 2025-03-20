package ru.edstry.project1.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @NotEmpty(message = "title should not be empty")
    @Size(max = 50, message = "title should not be greater then 50 characters")
    @Column(name = "title")
    private String title;

    @Size(max = 50, message = "author should not be greater then 50 characters")
    @NotEmpty(message = "author should not be empty")
    @Column(name = "author")
    private String author;

    @Min(value = 1000, message = "Year should be greater then 1000")
    @Column(name = "year")
    private int year;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean expired;

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }
}
