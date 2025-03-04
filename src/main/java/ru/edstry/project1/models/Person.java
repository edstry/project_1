package ru.edstry.project1.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Person {
    private int id;

    @NotEmpty(message = "Fio should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    private String fio;

    @Min(value = 1900, message = "Year should be greater then 1900")
    private int year_of_birth;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }
}
