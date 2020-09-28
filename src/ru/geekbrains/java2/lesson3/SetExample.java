package ru.geekbrains.java2.lesson3;

import java.util.*;

public class SetExample {

    public static void main(String args[]) {
        Set<String> set = new HashSet<>();
//        Set<String> set = new LinkedHashSet<>();
//        Set<String> set = new TreeSet<>(Comparator.reverseOrder());
        set.add("Aльфa");
        set.add("Бета");
        set.add("Aльфa");
        set.add("Этa");
        set.add("Гaммa");
        set.add("Эпсилон");
        set.add("Омега");
        set.add("Гaммa");
        System.out.println(set);

        System.out.println("---------");

        Set<Person> personSet = new TreeSet<Person>(Comparator.reverseOrder());
//        Set<Person> personSet = new TreeSet<Person>(Comparator.comparing(Person::getName));
//        Set<Person> personSet = new TreeSet<Person>(Comparator.comparing(person -> person.getName()));
//        Set<Person> personSet = new TreeSet<Person>(Comparator.comparing(new Function<Person, String>() {
//            @Override
//            public String apply(Person person) {
//                return person.getName();
//            }
//        }));
        Person oleg   = new Person("Oleg","Petrov", 27);
        Person alexey = new Person("Alexey", "Petrov", 29);
        personSet.add(oleg);
        personSet.add(alexey);
//
        for (Person person : personSet) {
            System.out.println(person);
        }
    }


}
