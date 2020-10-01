package ru.geekbrains.java2.lesson3;

import java.util.Objects;

public class Person /*implements Comparable<Person> */{

    private String name;
    private String surname;
    private int age;

    public Person(String name, int age) {
        this(name, null, age);
    }

    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

/*    @Override
    public int compareTo(Person anotherPerson) {
//        return this.name.compareTo(anotherPerson.getName());

        int compareBySurname = this.surname.compareTo(anotherPerson.getSurname());
        return compareBySurname != 0 ? compareBySurname : this.getName().compareTo(anotherPerson.getName());
    }*/
}
