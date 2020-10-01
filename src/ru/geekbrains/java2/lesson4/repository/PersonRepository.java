package ru.geekbrains.java2.lesson4.repository;

import ru.geekbrains.java2.lesson4.model.Person;

import java.util.List;

public interface PersonRepository {

    List<Person> getAllData();
}
