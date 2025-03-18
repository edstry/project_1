package ru.edstry.project1.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    @NotEmpty(message = "Fio should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "fio")
    private String fio;

    @Min(value = 1900, message = "Year should be greater then 1900")
    @Column(name = "year_of_birth")
    private int year_of_birth;

    public Person() {}

    public Person(String fio, int year_of_birth) {
        this.fio = fio;
        this.year_of_birth = year_of_birth;
    }
}
