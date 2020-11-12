package ru.geekbrains.java2.lesson2;

public class DivideByZeroException extends /*ArithmeticException*/ RuntimeException {

    public DivideByZeroException() {
        super("Произошло деление на ноль!");
    }
}
