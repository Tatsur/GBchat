package ru.geekbrains.java2.lesson4.controller.validators;

import java.util.List;

public interface Validator {

    /**
     * Start of validation process.
     * @return List of error messages if validation is failed otherwise empty collection.
     */
    List<String> validate();

}
